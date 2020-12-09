package com.wonderful.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wonderful.bean.dto.CapitalFlowDTO;
import com.wonderful.bean.dto.MasterCardPurchaseDetailsDTO;
import com.wonderful.bean.entity.CapitalFlow;
import com.wonderful.bean.entity.MasterCardPurchaseDetails;
import com.wonderful.service.CapitalFlowService;
import com.wonderful.service.MasterCardPurchaseDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/capitalFlow")
public class CapitalFlowController {

    @Autowired
    private CapitalFlowService capitalFlowService;

    @PostMapping("/page")
    public Map<String, Object> page(CapitalFlowDTO capitalFlowDTO){

        Map<String, Object> map = new HashMap<>();

        IPage<CapitalFlow> page = capitalFlowService.page(capitalFlowDTO);
        map.put("total",page.getTotal());
        map.put("rows",page.getRecords());

        return map;

    }

    @PostMapping("/save")
    public boolean save(CapitalFlowDTO capitalFlowDTO){
        CapitalFlow capitalFlow = BeanUtil.toBean(capitalFlowDTO, CapitalFlow.class);
        capitalFlow.setCreateTime(LocalDateTime.now());
        capitalFlow.setUpdateTime(LocalDateTime.now());

        boolean save = capitalFlowService.save(capitalFlow);

        return save;

    }

    @PostMapping("/update")
    public boolean update(CapitalFlowDTO capitalFlowDTO){
        CapitalFlow capitalFlow = BeanUtil.toBean(capitalFlowDTO, CapitalFlow.class);
        capitalFlow.setUpdateTime(LocalDateTime.now());

        boolean save = capitalFlowService.updateById(capitalFlow);

        return save;

    }

    @GetMapping("/delete")
    public boolean delete(@RequestParam(value = "id") int id){

        boolean save = capitalFlowService.removeById(id);

        return save;

    }

    @GetMapping("/find")
    public CapitalFlow find(@RequestParam(value = "id") int id){

        CapitalFlow capitalFlow = capitalFlowService.getById(id);

        return capitalFlow;

    }
}
