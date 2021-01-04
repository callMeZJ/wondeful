package com.wonderful.bean.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wonderful.common.CustomLocalDateTimeDeserializer;
import com.wonderful.common.CustomLocalDateTimeSerializer;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Getter
@Setter
public class AttacherCardSaleDetailsImportDTO {

    @ExcelProperty("卡号")
    private String cardNum;

    @ExcelProperty("持卡人")
    private String cardholder;

    @ExcelProperty("交易类型")
    private String transactionType;

    @ExcelProperty("交易时间")
    private String transactionTime;

    @ExcelProperty("交易地点")
    private String transactionPlace;

    @ExcelProperty("分配金额")
    private BigDecimal distributionAmount;

    @ExcelProperty("分配积分")
    private BigDecimal distributionPoint;
}
