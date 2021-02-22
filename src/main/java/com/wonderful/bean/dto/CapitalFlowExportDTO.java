package com.wonderful.bean.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.*;

import java.math.BigDecimal;

@Data
@Getter
@Setter
public class CapitalFlowExportDTO extends BaseRowModel {

    @ExcelProperty("收入")
    private BigDecimal income;

    @ExcelProperty("支出")
    private BigDecimal expenditure;

    @ExcelProperty("余额")
    private BigDecimal surplusAmount;

    @ExcelProperty("收款方")
    private String payee;

    @ExcelProperty("付款方")
    private String payer;

    @ExcelProperty("用途")
    private String useTo;

    @ExcelProperty("备注")
    private String remarks;
}
