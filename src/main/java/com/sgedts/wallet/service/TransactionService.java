package com.sgedts.wallet.service;

import com.sgedts.wallet.constant.Constant;
import com.sgedts.wallet.dto.TopupDTO;
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
        User user = userService.getInfo(topupDTO.getUsername());
        if (user == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username not found");
        }
        else if (!user.getPassword().equals(topupDTO.getPassword())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password Salah");
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
            userRepository.save(user);
        }
    }
}
