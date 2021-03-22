package com.wonderful.bean.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttacherCardSaleDetailsSummaryDTO {

    private String masterCardNum;

    private String cardNum;

    private String department;

    private String cardPublisher;

    private String cardBuyer;

    private Timestamp cardBuyTime;

    private Timestamp changeCardTime;

    private BigDecimal discount;

    private String telNum;

    private String vx;

    private String carNum;

    private String attribute;

    private String oils;

    private String status;

    private String remarks;

    private BigDecimal rechargeAmount;

    private BigDecimal rechargePoint;

    private Timestamp firstRechargeTime;

    private Integer rechargeTimes;

    private Timestamp lastRechargeTime;

    private Integer page;

    private Integer rows;
}
