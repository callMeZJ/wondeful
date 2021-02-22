package com.wonderful.bean.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.*;

@Data
@Getter
@Setter
public class CustomerInfoExportDTO extends BaseRowModel {

    @ExcelProperty("用户昵称")
    private String customerAlias;

    @ExcelProperty("用户编码")
    private String customerNum;

    @ExcelProperty("用户姓名")
    private String customerName;

    @ExcelProperty("联系电话")
    private String telNum;

    @ExcelProperty("性别")
    private String sex;

    @ExcelProperty("身份证号")
    private String idCard;

    @ExcelProperty("地区")
    private String area;

    @ExcelProperty(value = "注册时间", format = "yyyy-MM-dd HH:mm:ss")
    private String registerTime;

    @ExcelProperty("车辆长度")
    private String carSize;

    @ExcelProperty("车辆载荷人数")
    private String carLoadSize;

    @ExcelProperty("车牌号")
    private String carNum;

    @ExcelProperty("车辆使用用途")
    private String carUse;
}
