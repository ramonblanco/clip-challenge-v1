package com.example.clip.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReportResponse {

    private Long userId;
    private String userName;
    private BigDecimal allPaymentsSumAmount;
    private Integer newPaymentsQuantity;
    private BigDecimal newPaymentsSumAmount;
}
