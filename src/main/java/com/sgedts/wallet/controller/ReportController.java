package com.sgedts.wallet.controller;

import com.sgedts.wallet.dto.GetReportDTO;
import com.sgedts.wallet.dto.GetReportResponseDTO;
import com.sgedts.wallet.model.Transaction;
import com.sgedts.wallet.repository.TransactionRepository;
import com.sgedts.wallet.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/report")
public class ReportController {

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    ReportService reportService;

    @GetMapping("/getreport/{date}")
    public ResponseEntity<Object> getReport(@PathVariable LocalDate date){
//        GetReportResponseDTO getReportResponseDTO = new GetReportResponseDTO();
        return ResponseEntity.ok().body(reportService.getReport(date));
    }
}
