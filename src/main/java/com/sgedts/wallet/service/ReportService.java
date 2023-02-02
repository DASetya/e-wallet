package com.sgedts.wallet.service;

import com.sgedts.wallet.dto.GetReportDTO;
import com.sgedts.wallet.model.Transaction;
import com.sgedts.wallet.model.User;
import com.sgedts.wallet.repository.TransactionRepository;
import com.sgedts.wallet.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReportService {

    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    UserRepository userRepository;

    public List<GetReportDTO> getReport(LocalDate date) {
        String balanceChangeDate = date.format(DateTimeFormatter.ofPattern("dd MMM yyyy"));
        List<User> list = userRepository.findAll();
        List<GetReportDTO> getReportDTOList = new ArrayList<>();

        list.forEach(user -> {
            GetReportDTO getReportDTO = new GetReportDTO();
            List<Transaction> transactions = user.getTransactions().stream().filter(transaction -> transaction.getDate().equals(date)).toList();
            if (!transactions.isEmpty()) {
                Long firstTransaction = transactions.get(0).getBalanceBefore();
                Long lastTransaction = transactions.get(transactions.size() - 1).getBalanceAfter();
                if (firstTransaction == 0) {
                    String changeInPercentage = "-";
                    getReportDTO.setUsername(user.getUsername());
                    getReportDTO.setChangeInPercentage(changeInPercentage);
                } else {
                    DecimalFormat decimalFormat = new DecimalFormat("###.##");
                    double changeInPercentage = 1.0 * (lastTransaction - firstTransaction) / firstTransaction * 100;
                    getReportDTO.setUsername(user.getUsername());
                    getReportDTO.setChangeInPercentage(decimalFormat.format(changeInPercentage)+"%");

                }
            } else {
                getReportDTO.setUsername(user.getUsername());
                getReportDTO.setChangeInPercentage("0%");
            }
            getReportDTO.setDate(balanceChangeDate);
            getReportDTOList.add(getReportDTO);
        });
        return getReportDTOList;
    }
}
