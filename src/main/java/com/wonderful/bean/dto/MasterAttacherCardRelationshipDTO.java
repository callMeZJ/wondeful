package com.wonderful.bean.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.sql.Timestamp;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MasterAttacherCardRelationshipDTO {

    @TableId(type = IdType.AUTO)
    private BigInteger id;

    private String masterCardNum;

    private String attacherCardNum;

    private Date openCardTime;

    private String status;

    private String oils;

    private String customerCode;

    private BigDecimal discount;

    private String cardBuyer;

    private Timestamp updateTime;

    private Timestamp createTime;

    private Integer page;

    private Integer rows;
}
