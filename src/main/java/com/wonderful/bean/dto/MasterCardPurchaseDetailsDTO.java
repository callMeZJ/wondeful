package com.wonderful.bean.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wonderful.common.CustomLocalDateTimeDeserializer;
import com.wonderful.common.CustomLocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MasterCardPurchaseDetailsDTO {

    @TableId(type = IdType.AUTO)
    private BigInteger id;

    private String openCardCompanyName;

    private String masterCardNum;

    //@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    //@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private Timestamp transferTime;

    //@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    //@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private Timestamp accountingTime;

    private BigDecimal rechargeAmount;

    private BigDecimal transferAmount;

    private Integer discount;

    private String remarks;

    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime updateTime;

    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime createTime;

    private Integer page;

    private Integer rows;
}
