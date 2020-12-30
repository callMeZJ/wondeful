package com.wonderful.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wonderful.bean.dto.AttacherCardOfficialAccountSaleDetailsDTO;
import com.wonderful.bean.dto.MasterCardPurchaseDetailsDTO;
import com.wonderful.bean.entity.AttacherCardOfficialAccountSaleDetails;
import com.wonderful.bean.entity.MasterCardPurchaseDetails;
import com.wonderful.service.AttacherCardOfficialAccountSaleDetailsService;
import com.wonderful.service.MasterCardPurchaseDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/attacherCardOfficialAccountSaleDetails")
public class AttacherCardOfficialAccountSaleDetailsController {

    @Autowired
    private AttacherCardOfficialAccountSaleDetailsService attacherCardOfficialAccountSaleDetailsService;

    @PostMapping("/page")
    public Map<String, Object> page(AttacherCardOfficialAccountSaleDetailsDTO attacherCardOfficialAccountSaleDetailsDTO){

        Map<String, Object> map = new HashMap<>();

        IPage<AttacherCardOfficialAccountSaleDetails> page = attacherCardOfficialAccountSaleDetailsService.page(attacherCardOfficialAccountSaleDetailsDTO);
        map.put("total",page.getTotal());
        map.put("rows",page.getRecords());

        return map;

    }

    @PostMapping("/save")
    public boolean save(AttacherCardOfficialAccountSaleDetailsDTO attacherCardOfficialAccountSaleDetailsDTO){
        AttacherCardOfficialAccountSaleDetails attacherCardOfficialAccountSaleDetails = BeanUtil.toBean(attacherCardOfficialAccountSaleDetailsDTO, AttacherCardOfficialAccountSaleDetails.class);
        attacherCardOfficialAccountSaleDetails.setCreateTime(LocalDateTime.now());
        attacherCardOfficialAccountSaleDetails.setUpdateTime(LocalDateTime.now());
        attacherCardOfficialAccountSaleDetails.setIsComparison("no");

        boolean save = attacherCardOfficialAccountSaleDetailsService.save(attacherCardOfficialAccountSaleDetails);

        return save;

    }

    @PostMapping("/update")
    public boolean update(AttacherCardOfficialAccountSaleDetailsDTO attacherCardOfficialAccountSaleDetailsDTO){
        AttacherCardOfficialAccountSaleDetails attacherCardOfficialAccountSaleDetails = BeanUtil.toBean(attacherCardOfficialAccountSaleDetailsDTO, AttacherCardOfficialAccountSaleDetails.class);
        attacherCardOfficialAccountSaleDetails.setUpdateTime(LocalDateTime.now());

        boolean save = attacherCardOfficialAccountSaleDetailsService.updateById(attacherCardOfficialAccountSaleDetails);

        return save;

    }

    @GetMapping("/delete")
    public boolean delete(@RequestParam(value = "id") int id){

        boolean save = attacherCardOfficialAccountSaleDetailsService.removeById(id);

        return save;

    }

    @GetMapping("/find")
    public AttacherCardOfficialAccountSaleDetails find(@RequestParam(value = "id") int id){

        AttacherCardOfficialAccountSaleDetails attacherCardOfficialAccountSaleDetails = attacherCardOfficialAccountSaleDetailsService.getById(id);

        return attacherCardOfficialAccountSaleDetails;

    }
}
