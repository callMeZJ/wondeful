package com.wonderful.bean.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
public class AttacherCardOfficialAccountSaleDetailsDTO {

    @TableId(type = IdType.AUTO)
    private BigInteger id;

    private String orderNum;

    private String customerNum;

    private String realName;

    private String customerAlias;

    private BigDecimal rechargeAmount;

    private BigDecimal payAmount;

    private String rechargePlatform;

    private String oilCardNum;

    private String status;

    private String isComparison;

    private Timestamp generationTime;

    private String remarks;

    private LocalDateTime updateTime;

    private LocalDateTime createTime;

    private Integer page;

    private Integer rows;
}
