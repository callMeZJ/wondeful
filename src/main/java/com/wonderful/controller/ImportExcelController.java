package com.wonderful.controller;

import com.alibaba.excel.EasyExcel;
import com.wonderful.bean.dto.AttacherCardSaleDetailsImportDTO;
import com.wonderful.bean.entity.AttacherCardSaleDetails;
import com.wonderful.service.AttacherCardSaleDetailsService;
import com.wonderful.service.excel.in.AttacherCardSaleDetailsListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/importExcel")
public class ImportExcelController {

    @Autowired
    private AttacherCardSaleDetailsService attacherCardSaleDetailsService;

    /**
     * 读取 excel
     * @return
     */
    @PostMapping("upload")
    public String upload(MultipartFile file) throws IOException {

        //写法1
        //sheet里面可以传参 根据sheet下标读取或者根据名字读取 不传默认读取第一个
        EasyExcel.read(file.getInputStream(), AttacherCardSaleDetailsImportDTO.class, new AttacherCardSaleDetailsListener(attacherCardSaleDetailsService))
                .sheet()
                .doRead();

        //写法2
        //ExcelReader excelReader = EasyExcel.read(file.getInputStream(), Student.class, new StudentListener(studentDao)).build();
        //ReadSheet readSheet = EasyExcel.readSheet(0).build();
        //excelReader.read(readSheet);
        //这里千万别忘记关闭，读的时候会创建临时文件，到时磁盘会崩的
        //excelReader.finish();
        return "导入成功";
    }
}
