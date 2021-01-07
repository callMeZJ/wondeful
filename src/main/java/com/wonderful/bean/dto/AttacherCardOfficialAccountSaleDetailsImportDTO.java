package com.wonderful.bean.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.*;

import java.math.BigDecimal;

@Data
@Getter
@Setter
public class AttacherCardOfficialAccountSaleDetailsImportDTO {

    @ExcelProperty("订单号")
    private String orderNum;

    @ExcelProperty("客户编号")
    private String customerNum;

    @ExcelProperty("真实姓名")
    private String realName;

    @ExcelProperty("用户昵称")
    private String customerAlias;

    @ExcelProperty("充值金额")
    private BigDecimal rechargeAmount;

    @ExcelProperty("付款金额")
    private BigDecimal payAmount;

    @ExcelProperty("充值平台")
    private String rechargePlatform;

    @ExcelProperty("油卡号码")
    private String oilCardNum;

    @ExcelProperty("状态")
    private String status;

    @ExcelProperty("生成时间")
    private String generationTime;

    @ExcelProperty("备注")
    private String remarks;

}
