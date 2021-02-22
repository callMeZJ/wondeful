package com.wonderful.bean.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.time.LocalDateTime;

@Data
@Getter
@Setter
public class AttacherCardPointDetailsExportDTO extends BaseRowModel {

    @ExcelProperty(value = "日期", format = "yyyy-MM-dd")
    private String date;

    @ExcelProperty("卡号")
    private String cardNum;

    @ExcelProperty("购卡人")
    private String cardBuyer;

    @ExcelProperty("充值积分")
    private BigDecimal rechargePoint;

    @ExcelProperty("折扣")
    private Integer discount;

    @ExcelProperty("实付金额")
    private BigDecimal realPayAmount;

    @ExcelProperty("付款方式")
    private String payWay;

    @ExcelProperty("日期1")
    private String date1;

    @ExcelProperty("扣除手续费")
    private BigDecimal serviceCharge;

    @ExcelProperty("油品")
    private String oils;

    @ExcelProperty("备注")
    private String remarks;
}
