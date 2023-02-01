package com.sgedts.wallet.dto;

import lombok.Data;

@Data
public class GetBalanceDTO {
    private Long balance;
    private Long transactionLimit;
}
