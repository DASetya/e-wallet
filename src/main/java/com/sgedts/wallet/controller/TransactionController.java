package com.sgedts.wallet.controller;

import com.sgedts.wallet.dto.TopupDTO;
import com.sgedts.wallet.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionController {
    @Autowired
    TransactionService transactionService;

    @PostMapping("/transaction/topup")
    public void topup(@RequestBody TopupDTO topupDTO){
        transactionService.topup(topupDTO);
    }
}
