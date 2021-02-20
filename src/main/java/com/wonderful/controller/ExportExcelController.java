package com.wonderful.controller;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wonderful.bean.dto.MasterCardPurchaseDetailsDTO;
import com.wonderful.bean.dto.MasterCardPurchaseDetailsExportDTO;
import com.wonderful.bean.entity.MasterCardPurchaseDetails;
import com.wonderful.service.MasterCardPurchaseDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
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

    @GetMapping(value = "/masterCardPurchaseDetailsExport/{encrypted}")
    public String masterCardPurchaseDetailsExport(@PathVariable String encrypted, HttpServletResponse response) throws UnsupportedEncodingException {
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
            String decrypted = new String(Base64.getUrlDecoder().decode(encrypted.getBytes()));
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
}
