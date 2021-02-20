package com.wonderful.bean.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
@Getter
@Setter
public class MasterCardPurchaseDetailsExportDTO extends BaseRowModel {

    @ExcelProperty("开卡公司")
    private String openCardCompanyName;

    @ExcelProperty("主卡卡号")
    private String masterCardNum;

    @ExcelProperty(value = "转账时间", format = "yyyy-MM-dd HH:mm:ss")
    private String transferTime;

    @ExcelProperty(value = "上账时间", format = "yyyy-MM-dd HH:mm:ss")
    private String accountingTime;

    @ExcelProperty("充值面额")
    private BigDecimal rechargeAmount;

    @ExcelProperty("转账金额")
    private BigDecimal transferAmount;

    @ExcelProperty("折扣")
    private Integer discount;

    @ExcelProperty("积分")
    private BigDecimal points;

    @ExcelProperty("备注")
    private String remarks;

}
