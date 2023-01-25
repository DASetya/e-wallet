package com.sgedts.wallet.service;

import com.sgedts.wallet.constant.Constant;
import com.sgedts.wallet.dto.TopupDTO;
import com.sgedts.wallet.model.Status;
import com.sgedts.wallet.model.Transaction;
import com.sgedts.wallet.model.User;
import com.sgedts.wallet.repository.TransactionRepository;
import com.sgedts.wallet.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class TransactionService {

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Transactional
    public void topup(TopupDTO topupDTO){
        Transaction transaction = new Transaction();
        User user = userService.getInfo(topupDTO.getUsername());
        if (user == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username not found");
        }
        else if (!user.getPassword().equals(topupDTO.getPassword())){
            int wrongCounter = 0;
            while (wrongCounter!=3){
                wrongCounter+=1;
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password Salah");
            }
            if (wrongCounter==3){
                user.setIsBan(Boolean.TRUE);
                userRepository.save(user);
            }
        }
        else if (topupDTO.getAmount() <= Constant.MIN_TRANSACTION_AMOUNT){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Minimum topup 10000");
        }
        else if (topupDTO.getAmount() > Constant.MAX_TOPUP) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Max Topup 10000000");
        }
        else if(user.getBalance() + topupDTO.getAmount() > Constant.MAX_BALANCE){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Max Balance 10000000");
        }
        else {
            user.setBalance(user.getBalance() + topupDTO.getAmount());
            transaction.setOriginUsername(topupDTO.getUsername());
            transaction.setAmount(topupDTO.getAmount());
//            transaction.setId(user.getId());
            transaction.setStatus(Status.SETTLED);
            userRepository.save(user);
            transactionRepository.save(transaction);
        }
    }

    @Transactional
    public void transfer(Transaction transaction){
        User sender = userRepository.findByUsername(transaction.getOriginUsername());
        User receiver = userRepository.findByUsername(transaction.getDestinationUsername());
        transaction.setOriginUsername(transaction.getOriginUsername());
        transaction.setDestinationUsername(transaction.getDestinationUsername());
        transaction.setAmount(transaction.getAmount());
        transaction.setStatus(Status.SETTLED);
        sender.setBalance(sender.getBalance()-transaction.getAmount());
        receiver.setBalance(receiver.getBalance()+transaction.getAmount());
        userRepository.save(sender);
        userRepository.save(receiver);
        transactionRepository.save(transaction);
    }
}
