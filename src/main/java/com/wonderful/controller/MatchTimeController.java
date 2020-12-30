package com.wonderful.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wonderful.bean.dto.MatchTimeDTO;
import com.wonderful.bean.dto.PayServiceChargeDTO;
import com.wonderful.bean.entity.MatchTime;
import com.wonderful.bean.entity.PayServiceCharge;
import com.wonderful.service.MatchTimeService;
import com.wonderful.service.PayServiceChargeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/matchTime")
public class MatchTimeController {

    @Autowired
    private MatchTimeService matchTimeService;

    @PostMapping("/page")
    public Map<String, Object> page(MatchTimeDTO matchTimeDTO){

        Map<String, Object> map = new HashMap<>();

        IPage<MatchTime> page = matchTimeService.page(matchTimeDTO);
        map.put("total",page.getTotal());
        map.put("rows",page.getRecords());

        return map;

    }

    @PostMapping("/save")
    public boolean save(MatchTimeDTO matchTimeDTO){
        MatchTime matchTime = BeanUtil.toBean(matchTimeDTO, MatchTime.class);
        matchTime.setCreateTime(LocalDateTime.now());
        matchTime.setUpdateTime(LocalDateTime.now());

        boolean save = matchTimeService.save(matchTime);

        return save;

    }

    @PostMapping("/update")
    public boolean update(MatchTimeDTO matchTimeDTO){
        MatchTime matchTime = BeanUtil.toBean(matchTimeDTO, MatchTime.class);
        matchTime.setUpdateTime(LocalDateTime.now());

        boolean save = matchTimeService.updateById(matchTime);

        return save;

    }

    @GetMapping("/delete")
    public boolean delete(@RequestParam(value = "id") int id){

        boolean save = matchTimeService.removeById(id);

        return save;

    }

    @GetMapping("/find")
    public MatchTime find(@RequestParam(value = "id") int id){

        MatchTime matchTime = matchTimeService.getById(id);

        return matchTime;

    }

    @GetMapping("/findOnlyOne")
    public MatchTime findOnlyOne(){

        MatchTime matchTime = matchTimeService.findOnlyOne();

        return matchTime;

    }

}
