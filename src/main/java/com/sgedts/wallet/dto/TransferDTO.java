package com.sgedts.wallet.dto;

import lombok.Data;

@Data
public class TransferDTO {
    private String username;
    private String password;
    private String destinationUsername;
    private Long amount;
}
