package com.wonderful.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wonderful.bean.dto.PayServiceChargeDTO;
import com.wonderful.bean.entity.PayServiceCharge;
import com.wonderful.service.PayServiceChargeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/payServiceCharge")
public class PayServiceChargeController {

    @Autowired
    private PayServiceChargeService payServiceChargeService;

    @PostMapping("/page")
    public Map<String, Object> page(PayServiceChargeDTO payServiceChargeDTO){

        Map<String, Object> map = new HashMap<>();

        IPage<PayServiceCharge> page = payServiceChargeService.page(payServiceChargeDTO);
        map.put("total",page.getTotal());
        map.put("rows",page.getRecords());

        return map;

    }

    @PostMapping("/save")
    public boolean save(PayServiceChargeDTO payServiceChargeDTO){
        PayServiceCharge payServiceCharge = BeanUtil.toBean(payServiceChargeDTO, PayServiceCharge.class);
        payServiceCharge.setCreateTime(LocalDateTime.now());
        payServiceCharge.setUpdateTime(LocalDateTime.now());

        boolean save = payServiceChargeService.save(payServiceCharge);

        return save;

    }

    @PostMapping("/update")
    public boolean update(PayServiceChargeDTO payServiceChargeDTO){
        PayServiceCharge payServiceCharge = BeanUtil.toBean(payServiceChargeDTO, PayServiceCharge.class);
        payServiceCharge.setUpdateTime(LocalDateTime.now());

        boolean save = payServiceChargeService.updateById(payServiceCharge);

        return save;

    }

    @GetMapping("/delete")
    public boolean delete(@RequestParam(value = "id") int id){

        boolean save = payServiceChargeService.removeById(id);

        return save;

    }

    @GetMapping("/find")
    public PayServiceCharge find(@RequestParam(value = "id") int id){

        PayServiceCharge payServiceCharge = payServiceChargeService.getById(id);

        return payServiceCharge;

    }
}
