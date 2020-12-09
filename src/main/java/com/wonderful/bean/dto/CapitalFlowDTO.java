package com.wonderful.bean.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CapitalFlowDTO {

    @TableId(type = IdType.AUTO)
    private BigInteger id;

    private BigDecimal income;

    private BigDecimal expenditure;

    private BigDecimal surplusAmount;

    private String payee;

    private String payer;

    private String useTo;

    private String remarks;

    private LocalDateTime updateTime;

    private LocalDateTime createTime;

    private Integer page;

    private Integer rows;
}
