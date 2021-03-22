package com.wonderful.bean.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wonderful.common.CustomLocalDateTimeDeserializer;
import com.wonderful.common.CustomLocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;

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

    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime cardBuyTime;

    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime changeCardTime;

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

    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime firstRechargeTime;

    private Integer rechargeTimes;

    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime lastRechargeTime;

    private Integer page;

    private Integer rows;
}
