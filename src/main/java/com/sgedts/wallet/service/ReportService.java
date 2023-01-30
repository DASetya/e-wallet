package com.sgedts.wallet.service;

import com.sgedts.wallet.dto.GetReportDTO;
import com.sgedts.wallet.dto.GetReportResponseDTO;
import com.sgedts.wallet.model.Transaction;
import com.sgedts.wallet.model.User;
import com.sgedts.wallet.repository.TransactionRepository;
import com.sgedts.wallet.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReportService {
    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    UserRepository userRepository;

    public List<GetReportDTO> getReport(LocalDate date){
        String balanceChangeDate = date.format(DateTimeFormatter.ofPattern("dd MMM yyyy"));
//        List<Transaction> transactions = transactionRepository.findByDate(date);
        Iterable<User> list = userRepository.findAll();
        List<GetReportDTO> getReportDTOList = new ArrayList<>();
        NumberFormat numberFormat = NumberFormat.getPercentInstance();
        numberFormat.setMaximumFractionDigits(2);

        list.forEach(user -> {
            GetReportDTO getReportDTO = new GetReportDTO();
            List<Transaction> transactions = user.getTransactions().stream().filter(transaction -> transaction.getDate().equals(date)).toList();
            if (!transactions.isEmpty()){
                Long firstTransaction = transactions.get(0).getBalanceBefore();
                Long lastTransaction = transactions.get(transactions.size()-1).getBalanceAfter();
                if (firstTransaction==0){
                    String changeInPercentage = "-";
                    getReportDTO.setUsername(user.getUsername());
                    getReportDTO.setChangeInPercentage(changeInPercentage);
                }
                else {
                    double changeInPercentage = 1.0 * (lastTransaction-firstTransaction) / firstTransaction * 100;
                    getReportDTO.setUsername(user.getUsername());
                    getReportDTO.setChangeInPercentage(numberFormat.format(changeInPercentage)+"%");
                }
            }else {
                getReportDTO.setUsername(user.getUsername());
                getReportDTO.setChangeInPercentage("0%");
            }
            getReportDTO.setDate(balanceChangeDate);
            getReportDTOList.add(getReportDTO);
        });

        return getReportDTOList;

//        Map<String, List<Transaction>> map = transactions.stream()
//                .collect(Collectors.groupingBy(Transaction::getUsername));
//
//        List<GetReportDTO> reportDTOS = new ArrayList<>();
//
//        NumberFormat numberFormat = NumberFormat.getPercentInstance();
//        numberFormat.setMaximumFractionDigits(2);
//
//        map.forEach((username, report) -> {
//            System.out.println(username);
//            report.forEach(t-> System.out.println("\t" + t.getType() + " " + t.getDate()));
//            System.out.println();
//            Long firstTransaction = report.get(0).getBalanceBefore();
//            Long lastTransaction = report.get(report.size()-1).getBalanceAfter();
//            double changeInPercentage = 1.0 * (lastTransaction-firstTransaction) / firstTransaction;
//
//            reportDTOS.add(
//                    new GetReportDTO(username, numberFormat.format(changeInPercentage), balanceChangeDate)
//            );
//        });
//        return new GetReportResponseDTO(reportDTOS);
    }
}
