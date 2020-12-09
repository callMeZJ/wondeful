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
import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttacherCardPointDetailsDTO {

    @TableId(type = IdType.AUTO)
    private BigInteger id;

    private Date date;

    private String cardNum;

    private String cardBuyer;

    private BigDecimal rechargePoint;

    private Integer discount;

    private BigDecimal realPayAmount;

    private String payWay;

    private Date date1;

    private BigDecimal serviceCharge;

    private String oils;

    private String remarks;

    private LocalDateTime updateTime;

    private LocalDateTime createTime;

    private Integer page;

    private Integer rows;
}
