package com.sgedts.wallet.controller;

import com.sgedts.wallet.dto.TopupDTO;
import com.sgedts.wallet.dto.TransferDTO;
import com.sgedts.wallet.dto.TransferResponseDTO;
import com.sgedts.wallet.model.Transaction;
import com.sgedts.wallet.service.TransactionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionController {
    @Autowired
    TransactionService transactionService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/transaction/topup")
    public void topup(@RequestBody TopupDTO topupDTO){
        transactionService.topup(topupDTO);
    }

    @PostMapping("/transaction/create")
    public TransferResponseDTO transfer(@RequestBody TransferDTO transferDTO){
        return transactionService.transfer(transferDTO);
//        Transaction transaction = new Transaction();
//        TransferResponseDTO transferResponseDTO = modelMapper.map(transaction, TransferResponseDTO.class);
//        transferResponseDTO.setTrxId(transaction.getId());
//        return ResponseEntity.ok().body(transferResponseDTO);
    }
}
