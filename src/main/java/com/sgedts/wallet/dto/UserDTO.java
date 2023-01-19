package com.sgedts.wallet.dto;

import jakarta.persistence.Column;
import lombok.Builder;

@Builder
public class UserDTO {
    private Long id;
    private String username;
    private String password;
    private String ktp;
    private Boolean isBan;
    private Long balance;
    private Long transactionLimit;
}
