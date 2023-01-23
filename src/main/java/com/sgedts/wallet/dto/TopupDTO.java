package com.sgedts.wallet.dto;

import lombok.Data;

@Data
public class TopupDTO {
    private String username;
    private String password;
    private Long amount;
}
