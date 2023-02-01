package com.sgedts.wallet.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatusCode;

import java.util.List;

@AllArgsConstructor
@Data
public class GetReportResponseDTO{
    private List<GetReportDTO> reportBalanceChangeInPercentage;
}
