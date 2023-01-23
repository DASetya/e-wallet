package com.sgedts.wallet.service;

import com.sgedts.wallet.dto.TopupDTO;
import com.sgedts.wallet.model.Transaction;
import com.sgedts.wallet.model.User;
import com.sgedts.wallet.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    UserService userService;

//    @Transactional
//    public Transaction topup(TopupDTO topupDTO){
//        User user = transactionRepository.findByUsername(topupDTO.getUsername());
//        Transaction topup =
//    }
}
