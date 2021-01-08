package com.wonderful.controller;

import com.alibaba.excel.EasyExcel;
import com.wonderful.bean.dto.AttacherCardOfficialAccountSaleDetailsImportDTO;
import com.wonderful.bean.dto.AttacherCardSaleDetailsImportDTO;
import com.wonderful.bean.dto.CustomerInfoImportDTO;
import com.wonderful.bean.dto.MasterAttacherCardRelationshipImportDTO;
import com.wonderful.bean.entity.AttacherCardSaleDetails;
import com.wonderful.service.AttacherCardOfficialAccountSaleDetailsService;
import com.wonderful.service.AttacherCardSaleDetailsService;
import com.wonderful.service.CustomerInfoService;
import com.wonderful.service.MasterAttacherCardRelationshipService;
import com.wonderful.service.excel.in.AttacherCardOfficialAccountSaleDetailsListener;
import com.wonderful.service.excel.in.AttacherCardSaleDetailsListener;
import com.wonderful.service.excel.in.CustomerInfoListener;
import com.wonderful.service.excel.in.MasterAttacherCardRelationshipListener;
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
    @Autowired
    private AttacherCardOfficialAccountSaleDetailsService attacherCardOfficialAccountSaleDetailsService;
    @Autowired
    private MasterAttacherCardRelationshipService masterAttacherCardRelationshipService;
    @Autowired
    private CustomerInfoService customerInfoService;

    /**
     * 读取 excel
     * @return
     */
    @PostMapping("/attacherCardSaleDetailsUpload")
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

    @PostMapping("/attacherCardOfficialAccountSaleDetailsUpload")
    public String upload4AttacherCardOfficialAccountSaleDetails(MultipartFile file) throws IOException {

        EasyExcel.read(file.getInputStream(), AttacherCardOfficialAccountSaleDetailsImportDTO.class, new AttacherCardOfficialAccountSaleDetailsListener(attacherCardOfficialAccountSaleDetailsService))
                .sheet()
                .doRead();

        return "导入成功";
    }

    @PostMapping("/masterAttacherCardRelationshipUpload")
    public String upload4MasterAttacherCardRelationship(MultipartFile file) throws IOException {

        EasyExcel.read(file.getInputStream(), MasterAttacherCardRelationshipImportDTO.class, new MasterAttacherCardRelationshipListener(masterAttacherCardRelationshipService))
                .sheet()
                .doRead();

        return "导入成功";
    }

    @PostMapping("/customerInfoUpload")
    public String upload4CustomerInfo(MultipartFile file) throws IOException {

        EasyExcel.read(file.getInputStream(), CustomerInfoImportDTO.class, new CustomerInfoListener(customerInfoService))
                .sheet()
                .doRead();

        return "导入成功";
    }
}
