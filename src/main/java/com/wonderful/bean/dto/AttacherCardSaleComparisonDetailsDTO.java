package com.wonderful.bean.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wonderful.common.CustomLocalDateDeserializer;
import com.wonderful.common.CustomLocalDateSerializer;
import com.wonderful.common.CustomLocalDateTimeDeserializer;
import com.wonderful.common.CustomLocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttacherCardSaleComparisonDetailsDTO {

    @TableId(type = IdType.AUTO)
    private BigInteger id;

    private Date date;

    private String cardNum;

    private String cardBuyer;

    private String masterCardNum;

    private BigDecimal rechargeAmount;

    private Integer discount;

    private BigDecimal realPayAmount;

    private String payWay;

    private Timestamp payTime;

    private BigDecimal serviceCharge;

    private String oils;

    private String remarks;

    private LocalDateTime updateTime;

    private LocalDateTime createTime;

    private Integer page;

    private Integer rows;
}
