package com.wonderful.bean.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.*;

import java.math.BigDecimal;
import java.sql.Date;

@Data
@Getter
@Setter
public class AttacherCardSaleComparisonDetailsExportDTO extends BaseRowModel {

    @ExcelProperty(value = "日期", format = "yyyy-MM-dd")
    private String date;

    @ExcelProperty("卡号")
    private String cardNum;

    @ExcelProperty("购卡人")
    private String cardBuyer;

    @ExcelProperty("主卡")
    private String masterCardNum;

    @ExcelProperty("充值面额")
    private BigDecimal rechargeAmount;

    @ExcelProperty("折扣")
    private Integer discount;

    @ExcelProperty("实付金额")
    private BigDecimal realPayAmount;

    @ExcelProperty("支付方式")
    private String payWay;

    @ExcelProperty(value = "付款时间", format = "yyyy-MM-dd HH:mm:ss")
    private String payTime;

    @ExcelProperty("扣除手续费")
    private BigDecimal serviceCharge;

    @ExcelProperty("油品")
    private String oils;

    @ExcelProperty("备注")
    private String remarks;

}
