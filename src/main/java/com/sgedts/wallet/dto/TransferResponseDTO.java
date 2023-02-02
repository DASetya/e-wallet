package com.sgedts.wallet.dto;

import com.sgedts.wallet.model.Status;
import lombok.Data;

@Data
public class TransferResponseDTO {
    private Long trxId;
    private String originUsername;
    private String destinationUsername;
    private String amount;
    private Status status;
}
