package com.wonderful.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wonderful.bean.dto.MasterCardOpenBillDetailsDTO;
import com.wonderful.bean.dto.MasterCardPurchaseDetailsDTO;
import com.wonderful.bean.entity.MasterCardOpenBillDetails;
import com.wonderful.bean.entity.MasterCardPurchaseDetails;
import com.wonderful.service.MasterCardOpenBillDetailsService;
import com.wonderful.service.MasterCardPurchaseDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/masterCardOpenBillDetails")
public class MasterCardOpenBillDetailsController {

    @Autowired
    private MasterCardOpenBillDetailsService masterCardOpenBillDetailsService;

    @PostMapping("/page")
    public Map<String, Object> page(MasterCardOpenBillDetailsDTO masterCardOpenBillDetailsDTO){

        Map<String, Object> map = new HashMap<>();

        IPage<MasterCardOpenBillDetails> page = masterCardOpenBillDetailsService.page(masterCardOpenBillDetailsDTO);
        map.put("total",page.getTotal());
        map.put("rows",page.getRecords());

        return map;

    }

    @PostMapping("/save")
    public boolean save(MasterCardOpenBillDetailsDTO masterCardOpenBillDetailsDTO){
        MasterCardOpenBillDetails masterCardOpenBillDetails = BeanUtil.toBean(masterCardOpenBillDetailsDTO, MasterCardOpenBillDetails.class);
        masterCardOpenBillDetails.setCreateTime(LocalDateTime.now());
        masterCardOpenBillDetails.setUpdateTime(LocalDateTime.now());

        boolean save = masterCardOpenBillDetailsService.save(masterCardOpenBillDetails);

        return save;

    }

    @PostMapping("/update")
    public boolean update(MasterCardOpenBillDetailsDTO masterCardOpenBillDetailsDTO){
        MasterCardOpenBillDetails masterCardOpenBillDetails = BeanUtil.toBean(masterCardOpenBillDetailsDTO, MasterCardOpenBillDetails.class);
        masterCardOpenBillDetails.setUpdateTime(LocalDateTime.now());

        boolean save = masterCardOpenBillDetailsService.updateById(masterCardOpenBillDetails);

        return save;

    }

    @GetMapping("/delete")
    public boolean delete(@RequestParam(value = "id") int id){

        boolean save = masterCardOpenBillDetailsService.removeById(id);

        return save;

    }

    @GetMapping("/find")
    public MasterCardOpenBillDetails find(@RequestParam(value = "id") int id){

        MasterCardOpenBillDetails masterCardOpenBillDetails = masterCardOpenBillDetailsService.getById(id);

        return masterCardOpenBillDetails;

    }
}
