package com.sgedts.wallet.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetReportDTO {
    private String username;
    private String changeInPercentage;
    private String date;
}
