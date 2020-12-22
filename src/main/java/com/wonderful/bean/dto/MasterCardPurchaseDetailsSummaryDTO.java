package com.wonderful.bean.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MasterCardPurchaseDetailsSummaryDTO {

    private String openCardCompanyName;

    private String masterCardNum;

    private BigDecimal rechargeAmountSum;

    private BigDecimal saleAmount;

    private BigDecimal masterSurplusAmount;

    private BigDecimal getPoint;

    private BigDecimal salePoint;

    private BigDecimal openBillAmount;

    private BigDecimal noOpenBillAmount;

    private Integer page;

    private Integer rows;
}
