package com.wonderful.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wonderful.bean.dto.MasterCardPurchaseDetailsDTO;
import com.wonderful.bean.entity.MasterCardPurchaseDetails;
import com.wonderful.service.MasterCardPurchaseDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/masterCardPurchaseDetails")
public class MasterCardPurchaseDetailsController {

    @Autowired
    private MasterCardPurchaseDetailsService masterCardPurchaseDetailsService;

    @PostMapping("/page")
    public Map<String, Object> page(MasterCardPurchaseDetailsDTO masterCardPurchaseDetailsDTO){

        Map<String, Object> map = new HashMap<>();

        IPage<MasterCardPurchaseDetails> page = masterCardPurchaseDetailsService.page(masterCardPurchaseDetailsDTO);
        map.put("total",page.getTotal());
        map.put("rows",page.getRecords());

        return map;

    }

    @PostMapping("/save")
    public boolean save(MasterCardPurchaseDetailsDTO masterCardPurchaseDetailsDTO){
        MasterCardPurchaseDetails masterCardPurchaseDetails = BeanUtil.toBean(masterCardPurchaseDetailsDTO, MasterCardPurchaseDetails.class);
        masterCardPurchaseDetails.setCreateTime(LocalDateTime.now());
        masterCardPurchaseDetails.setUpdateTime(LocalDateTime.now());

        boolean save = masterCardPurchaseDetailsService.save(masterCardPurchaseDetails);

        return save;

    }
}
