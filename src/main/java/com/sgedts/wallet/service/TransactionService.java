package com.sgedts.wallet.service;

import com.sgedts.wallet.constant.Constant;
import com.sgedts.wallet.dto.TopupDTO;
import com.sgedts.wallet.dto.TransferDTO;
import com.sgedts.wallet.dto.TransferResponseDTO;
import com.sgedts.wallet.model.Status;
import com.sgedts.wallet.model.Transaction;
import com.sgedts.wallet.model.Type;
import com.sgedts.wallet.model.User;
import com.sgedts.wallet.repository.TransactionRepository;
import com.sgedts.wallet.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.text.NumberFormat;
import java.time.LocalDate;

@Service
@Transactional(dontRollbackOn = ResponseStatusException.class)
public class TransactionService {
    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;
    LocalDate now = LocalDate.now();
    public void topup(TopupDTO topupDTO) throws Exception {
        Transaction transaction = new Transaction();
        User user = userService.getInfo(topupDTO.getUsername());
        if (user == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username not found");
        }
        var wrongCounter = user.getWrongCounter();
        if (!user.getPassword().equals(topupDTO.getPassword())){
            if (wrongCounter==3){
                user.setIsBan(Boolean.TRUE);
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Akun terblokir");
            }
            user.setWrongCounter(wrongCounter+1);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password salah");
        }
        else if (topupDTO.getAmount() < Constant.MIN_TRANSACTION_AMOUNT){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Minimum topup 10000");
        }
        else if (topupDTO.getAmount() > Constant.MAX_TOPUP) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Max Topup 10000000");
        }
        else if(user.getBalance() + topupDTO.getAmount() > Constant.MAX_BALANCE){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Max Balance 10000000");
        }
        user.setWrongCounter(0);
        transaction.setUsername(topupDTO.getUsername());
        transaction.setAmount(topupDTO.getAmount());
        transaction.setStatus(Status.SETTLED);
        transaction.setUser(user);
        transaction.setDate(now);
        transaction.setType(Type.TOPUP);
        transaction.setBalanceBefore(user.getBalance());
        transaction.setBalanceAfter(user.getBalance() + topupDTO.getAmount());
        user.setBalance(user.getBalance() + topupDTO.getAmount());
        transactionRepository.save(transaction);
    }
    public TransferResponseDTO transfer(TransferDTO transferDTO){
        User sender = userRepository.findByUsername(transferDTO.getUsername());
        User recipient = userRepository.findByUsername(transferDTO.getDestinationUsername());
        Transaction senderTransaction = new Transaction();
        Transaction recipientTransaction = new Transaction();
        Long tax = (long) (transferDTO.getAmount() * Constant.TRANSACTION_TAX);

        if (sender.getUsername()==null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username not found");
        }
        Integer wrongCounter = sender.getWrongCounter();
        if (!sender.getPassword().equals(transferDTO.getPassword())){
            if (wrongCounter==3){
                sender.setIsBan(Boolean.TRUE);
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Akun terblokir");
            }
            sender.setWrongCounter(wrongCounter+1);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password salah");
        }
        else if (sender.getIsBan()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Akun terblokir");
        }
        else if(sender.getBalance() - transferDTO.getAmount() - tax < Constant.MIN_BALANCE){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Saldo tidak mencukupi untuk transaksi");
        }
        else if(recipient.getUsername() == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Akun penerima tidak ada");
        }
        else if(sender.getUsername().equals(recipient.getUsername())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Akun penerima tidak bisa sama dengan akun pengirim");
        }
        else if(transferDTO.getAmount() > sender.getTransactionLimit()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Transaksi melebihi limit");
        }
        else if(transferDTO.getAmount() < Constant.MIN_TRANSACTION_AMOUNT){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Transaksi minimal 10000");
        }
        else if(recipient.getBalance() + transferDTO.getAmount() > Constant.MAX_BALANCE){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Balance Amount Exceeded");
        }
        sender.setWrongCounter(0);
        senderTransaction.setUsername(sender.getUsername());
        senderTransaction.setUser(sender);
        senderTransaction.setAmount(transferDTO.getAmount());
        senderTransaction.setStatus(Status.SETTLED);
        senderTransaction.setDate(now);
        senderTransaction.setType(Type.SENDER);
        senderTransaction.setBalanceBefore(sender.getBalance());
        senderTransaction.setBalanceAfter(sender.getBalance() - transferDTO.getAmount() - tax);
        sender.setBalance(sender.getBalance() - transferDTO.getAmount() - tax);

        recipientTransaction.setUsername(recipient.getUsername());
        recipientTransaction.setUser(recipient);
        recipientTransaction.setAmount(transferDTO.getAmount());
        recipientTransaction.setStatus(Status.SETTLED);
        recipientTransaction.setDate(now);
        recipientTransaction.setType(Type.RECIPIENT);
        recipientTransaction.setBalanceBefore(recipient.getBalance());
        recipientTransaction.setBalanceAfter(recipient.getBalance() + transferDTO.getAmount());
        recipient.setBalance(recipient.getBalance() + transferDTO.getAmount());
        transactionRepository.save(senderTransaction);
        transactionRepository.save(recipientTransaction);

        TransferResponseDTO transferResponseDTO = new TransferResponseDTO();
        NumberFormat numberFormat = NumberFormat.getNumberInstance();
        transferResponseDTO.setTrxId(senderTransaction.getId());
        transferResponseDTO.setOriginUsername(sender.getUsername());
        transferResponseDTO.setDestinationUsername(recipient.getUsername());
        transferResponseDTO.setAmount(numberFormat.format(senderTransaction.getAmount()));
        transferResponseDTO.setStatus(Status.SETTLED);
        return transferResponseDTO;
    }
}
