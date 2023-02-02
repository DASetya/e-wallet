package com.sgedts.wallet.dto;

import lombok.Data;

@Data
public class GetBalanceDTO {
    private String balance;
    private Long transactionLimit;
}
