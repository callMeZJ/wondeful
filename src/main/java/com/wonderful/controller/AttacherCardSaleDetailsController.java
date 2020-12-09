package com.wonderful.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wonderful.bean.dto.AttacherCardSaleDetailsDTO;
import com.wonderful.bean.dto.MasterCardPurchaseDetailsDTO;
import com.wonderful.bean.entity.AttacherCardSaleDetails;
import com.wonderful.bean.entity.MasterCardPurchaseDetails;
import com.wonderful.service.AttacherCardSaleDetailsService;
import com.wonderful.service.MasterCardPurchaseDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/attacherCardSaleDetails")
public class AttacherCardSaleDetailsController {

    @Autowired
    private AttacherCardSaleDetailsService attacherCardSaleDetailsService;

    @PostMapping("/page")
    public Map<String, Object> page(AttacherCardSaleDetailsDTO attacherCardSaleDetailsDTO){

        Map<String, Object> map = new HashMap<>();

        IPage<AttacherCardSaleDetails> page = attacherCardSaleDetailsService.page(attacherCardSaleDetailsDTO);
        map.put("total",page.getTotal());
        map.put("rows",page.getRecords());

        return map;

    }

    @PostMapping("/save")
    public boolean save(AttacherCardSaleDetailsDTO attacherCardSaleDetailsDTO){
        AttacherCardSaleDetails attacherCardSaleDetails = BeanUtil.toBean(attacherCardSaleDetailsDTO, AttacherCardSaleDetails.class);
        attacherCardSaleDetails.setCreateTime(LocalDateTime.now());
        attacherCardSaleDetails.setUpdateTime(LocalDateTime.now());

        boolean save = attacherCardSaleDetailsService.save(attacherCardSaleDetails);

        return save;

    }

    @PostMapping("/update")
    public boolean update(AttacherCardSaleDetailsDTO attacherCardSaleDetailsDTO){
        AttacherCardSaleDetails attacherCardSaleDetails = BeanUtil.toBean(attacherCardSaleDetailsDTO, AttacherCardSaleDetails.class);
        attacherCardSaleDetails.setUpdateTime(LocalDateTime.now());

        boolean save = attacherCardSaleDetailsService.updateById(attacherCardSaleDetails);

        return save;

    }

    @GetMapping("/delete")
    public boolean delete(@RequestParam(value = "id") int id){

        boolean save = attacherCardSaleDetailsService.removeById(id);

        return save;

    }

    @GetMapping("/find")
    public AttacherCardSaleDetails find(@RequestParam(value = "id") int id){

        AttacherCardSaleDetails attacherCardSaleDetails = attacherCardSaleDetailsService.getById(id);

        return attacherCardSaleDetails;

    }
}
