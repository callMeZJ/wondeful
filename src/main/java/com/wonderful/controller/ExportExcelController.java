package com.wonderful.controller;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wonderful.bean.dto.*;
import com.wonderful.bean.entity.AttacherCardPointDetails;
import com.wonderful.bean.entity.AttacherCardSaleComparisonDetails;
import com.wonderful.bean.entity.CustomerInfo;
import com.wonderful.bean.entity.MasterCardPurchaseDetails;
import com.wonderful.service.AttacherCardPointDetailsService;
import com.wonderful.service.AttacherCardSaleComparisonDetailsService;
import com.wonderful.service.CustomerInfoService;
import com.wonderful.service.MasterCardPurchaseDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/exportExcel")
public class ExportExcelController {

    private AtomicInteger exportCount = new AtomicInteger(0);

    @Autowired
    private MasterCardPurchaseDetailsService masterCardPurchaseDetailsService;
    @Autowired
    private AttacherCardPointDetailsService attacherCardPointDetailsService;
    @Autowired
    private AttacherCardSaleComparisonDetailsService attacherCardSaleComparisonDetailsService;
    @Autowired
    private CustomerInfoService customerInfoService;

    @GetMapping(value = "/masterCardPurchaseDetailsExport")
    public String masterCardPurchaseDetailsExport(@RequestParam(value = "encrypted") String encrypted, HttpServletResponse response) throws UnsupportedEncodingException {
        //AtomicInteger原子操作类，导出流输出完之前不允许多次调用点击
        if (exportCount.incrementAndGet() > 1) {
            return "导出失败，重复导出";
        }
        Sheet sheet = new Sheet(1, 0, MasterCardPurchaseDetailsExportDTO.class);
        //设置自适应宽度
        sheet.setAutoWidth(Boolean.TRUE);
        String fileName = String.format("进货明细%s", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
        //通知浏览器以附件的形式下载处理
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-disposition", "attachment;filename=" + new String(fileName.getBytes("utf-8"),"iso-8859-1") + ".xlsx");
        response.setContentType("multipart/form-data");
        try {
            //获取要导出的数据
            //将空格替换为+，
            String str = encrypted.replaceAll(" +","+");
            //回车换行去掉
            String decrypted = new String(Base64.getDecoder().decode(str.replace("\r\n", "")),"utf-8");
            MasterCardPurchaseDetailsDTO masterCardPurchaseDetailsDTO = JSON.parseObject(decrypted, MasterCardPurchaseDetailsDTO.class);
            IPage<MasterCardPurchaseDetails> page = masterCardPurchaseDetailsService.page(masterCardPurchaseDetailsDTO);
            List<MasterCardPurchaseDetails> records = page.getRecords();
            if (records.size() == 0) {
                return "暂无数据导出";
            }
            List<MasterCardPurchaseDetailsExportDTO> content = records.stream().map(o -> {
                MasterCardPurchaseDetailsExportDTO masterCardPurchaseDetailsExportDTO = BeanUtil.toBean(o, MasterCardPurchaseDetailsExportDTO.class);
                return masterCardPurchaseDetailsExportDTO;
            }).collect(Collectors.toList());

            //导出
            ServletOutputStream out = response.getOutputStream();
            ExcelWriter writer = new ExcelWriter(out, ExcelTypeEnum.XLSX, true);
            writer.write(content, sheet);
            writer.finish();
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
            return "导出失败";
        } finally {
            //导出流结束，计数归0
            exportCount.set(0);
        }
        return "导出成功";
    }

    @GetMapping(value = "/attacherCardPointDetailsExport")
    public String attacherCardPointDetailsExport(@RequestParam(value = "encrypted") String encrypted, HttpServletResponse response) throws UnsupportedEncodingException {
        //AtomicInteger原子操作类，导出流输出完之前不允许多次调用点击
        if (exportCount.incrementAndGet() > 1) {
            return "导出失败，重复导出";
        }
        Sheet sheet = new Sheet(1, 0, AttacherCardPointDetailsExportDTO.class);
        //设置自适应宽度
        sheet.setAutoWidth(Boolean.TRUE);
        String fileName = String.format("石化销售积分明细%s", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
        //通知浏览器以附件的形式下载处理
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-disposition", "attachment;filename=" + new String(fileName.getBytes("utf-8"),"iso-8859-1") + ".xlsx");
        response.setContentType("multipart/form-data");
        try {
            //获取要导出的数据
            //将空格替换为+，
            String str = encrypted.replaceAll(" +","+");
            //回车换行去掉
            String decrypted = new String(Base64.getDecoder().decode(str.replace("\r\n", "")),"utf-8");
            AttacherCardPointDetailsDTO attacherCardPointDetailsDTO = JSON.parseObject(decrypted, AttacherCardPointDetailsDTO.class);
            IPage<AttacherCardPointDetails> page = attacherCardPointDetailsService.page(attacherCardPointDetailsDTO);
            List<AttacherCardPointDetails> records = page.getRecords();
            if (records.size() == 0) {
                return "暂无数据导出";
            }
            List<AttacherCardPointDetailsExportDTO> content = records.stream().map(o -> {
                AttacherCardPointDetailsExportDTO attacherCardPointDetailsExportDTO = BeanUtil.toBean(o, AttacherCardPointDetailsExportDTO.class);
                return attacherCardPointDetailsExportDTO;
            }).collect(Collectors.toList());

            //导出
            ServletOutputStream out = response.getOutputStream();
            ExcelWriter writer = new ExcelWriter(out, ExcelTypeEnum.XLSX, true);
            writer.write(content, sheet);
            writer.finish();
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
            return "导出失败";
        } finally {
            //导出流结束，计数归0
            exportCount.set(0);
        }
        return "导出成功";
    }

    @GetMapping(value = "/attacherCardSaleComparisonDetailsExport")
    public String attacherCardSaleComparisonDetailsExport(@RequestParam(value = "encrypted") String encrypted, HttpServletResponse response) throws UnsupportedEncodingException {
        //AtomicInteger原子操作类，导出流输出完之前不允许多次调用点击
        if (exportCount.incrementAndGet() > 1) {
            return "导出失败，重复导出";
        }
        Sheet sheet = new Sheet(1, 0, AttacherCardSaleComparisonDetailsExportDTO.class);
        //设置自适应宽度
        sheet.setAutoWidth(Boolean.TRUE);
        String fileName = String.format("石化-公众号明细对比%s", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
        //通知浏览器以附件的形式下载处理
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-disposition", "attachment;filename=" + new String(fileName.getBytes("utf-8"),"iso-8859-1") + ".xlsx");
        response.setContentType("multipart/form-data");
        try {
            //获取要导出的数据
            //将空格替换为+，
            String str = encrypted.replaceAll(" +","+");
            //回车换行去掉
            String decrypted = new String(Base64.getDecoder().decode(str.replace("\r\n", "")),"utf-8");
            AttacherCardSaleComparisonDetailsDTO attacherCardSaleComparisonDetailsDTO = JSON.parseObject(decrypted, AttacherCardSaleComparisonDetailsDTO.class);
            IPage<AttacherCardSaleComparisonDetails> page = attacherCardSaleComparisonDetailsService.page(attacherCardSaleComparisonDetailsDTO);
            List<AttacherCardSaleComparisonDetails> records = page.getRecords();
            if (records.size() == 0) {
                return "暂无数据导出";
            }
            List<AttacherCardSaleComparisonDetailsExportDTO> content = records.stream().map(o -> {
                AttacherCardSaleComparisonDetailsExportDTO attacherCardSaleComparisonDetailsExportDTO = BeanUtil.toBean(o, AttacherCardSaleComparisonDetailsExportDTO.class);
                return attacherCardSaleComparisonDetailsExportDTO;
            }).collect(Collectors.toList());

            //导出
            ServletOutputStream out = response.getOutputStream();
            ExcelWriter writer = new ExcelWriter(out, ExcelTypeEnum.XLSX, true);
            writer.write(content, sheet);
            writer.finish();
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
            return "导出失败";
        } finally {
            //导出流结束，计数归0
            exportCount.set(0);
        }
        return "导出成功";
    }

    @GetMapping(value = "/customerInfoExport")
    public String customerInfoExport(@RequestParam(value = "encrypted") String encrypted, HttpServletResponse response) throws UnsupportedEncodingException {
        //AtomicInteger原子操作类，导出流输出完之前不允许多次调用点击
        if (exportCount.incrementAndGet() > 1) {
            return "导出失败，重复导出";
        }
        Sheet sheet = new Sheet(1, 0, CustomerInfoExportDTO.class);
        //设置自适应宽度
        sheet.setAutoWidth(Boolean.TRUE);
        String fileName = String.format("客户信息%s", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
        //通知浏览器以附件的形式下载处理
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-disposition", "attachment;filename=" + new String(fileName.getBytes("utf-8"),"iso-8859-1") + ".xlsx");
        response.setContentType("multipart/form-data");
        try {
            //获取要导出的数据
            //将空格替换为+，
            String str = encrypted.replaceAll(" +","+");
            //回车换行去掉
            String decrypted = new String(Base64.getDecoder().decode(str.replace("\r\n", "")),"utf-8");
            CustomerInfoDTO customerInfoDTO = JSON.parseObject(decrypted, CustomerInfoDTO.class);
            IPage<CustomerInfo> page = customerInfoService.page(customerInfoDTO);
            List<CustomerInfo> records = page.getRecords();
            if (records.size() == 0) {
                return "暂无数据导出";
            }
            List<CustomerInfoExportDTO> content = records.stream().map(o -> {
                CustomerInfoExportDTO customerInfoExportDTO = BeanUtil.toBean(o, CustomerInfoExportDTO.class);
                return customerInfoExportDTO;
            }).collect(Collectors.toList());

            //导出
            ServletOutputStream out = response.getOutputStream();
            ExcelWriter writer = new ExcelWriter(out, ExcelTypeEnum.XLSX, true);
            writer.write(content, sheet);
            writer.finish();
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
            return "导出失败";
        } finally {
            //导出流结束，计数归0
            exportCount.set(0);
        }
        return "导出成功";
    }
}
