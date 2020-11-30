package com.wonderful.bean.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MasterCardOpenBillDetailsDTO {

    @TableId(type = IdType.AUTO)
    private BigInteger id;

    private String openBillCompany;

    private String masterCardNum;

    private Timestamp openBillTime;

    private BigDecimal openBillAmount;

    private BigDecimal taxPoint;

    private Timestamp returnFundTime;

    private LocalDateTime updateTime;

    private LocalDateTime createTime;

    private Integer page;

    private Integer rows;
}
