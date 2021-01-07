package com.wonderful.bean.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;

@Data
@Getter
@Setter
public class MasterAttacherCardRelationshipImportDTO {

    @ExcelProperty("主卡卡号")
    private String masterCardNum;

    @ExcelProperty("副卡卡号")
    private String attacherCardNum;

    @ExcelProperty("开卡时间")
    private String openCardTime;

    @ExcelProperty("状态")
    private String status;

    @ExcelProperty("油品")
    private String oils;

    @ExcelProperty("客户编码")
    private String customerCode;

    @ExcelProperty("折扣")
    private BigDecimal discount;

    @ExcelProperty("购卡人")
    private String cardBuyer;

}
