package com.wonderful.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wonderful.bean.dto.MasterAttacherCardRelationshipDTO;
import com.wonderful.bean.dto.MasterCardPurchaseDetailsDTO;
import com.wonderful.bean.entity.MasterAttacherCardRelationship;
import com.wonderful.bean.entity.MasterCardPurchaseDetails;
import com.wonderful.service.MasterAttacherCardRelationshipService;
import com.wonderful.service.MasterCardPurchaseDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/masterAttacherCardRelationship")
public class MasterAttacherCardRelationshipController {

    @Autowired
    private MasterAttacherCardRelationshipService masterAttacherCardRelationshipService;

    @PostMapping("/page")
    public Map<String, Object> page(MasterAttacherCardRelationshipDTO masterAttacherCardRelationshipDTO){

        //检测副卡是否已经消费过
        masterAttacherCardRelationshipService.updateIsUse();

        Map<String, Object> map = new HashMap<>();

        IPage<MasterAttacherCardRelationship> page = masterAttacherCardRelationshipService.page(masterAttacherCardRelationshipDTO);
        map.put("total",page.getTotal());
        map.put("rows",page.getRecords());

        return map;

    }

    @PostMapping("/save")
    public boolean save(MasterAttacherCardRelationshipDTO masterAttacherCardRelationshipDTO){
        MasterAttacherCardRelationship masterAttacherCardRelationship = BeanUtil.toBean(masterAttacherCardRelationshipDTO, MasterAttacherCardRelationship.class);
        masterAttacherCardRelationship.setCreateTime(LocalDateTime.now());
        masterAttacherCardRelationship.setUpdateTime(LocalDateTime.now());

        boolean save = masterAttacherCardRelationshipService.save(masterAttacherCardRelationship);

        return save;

    }

    @PostMapping("/update")
    public boolean update(MasterAttacherCardRelationshipDTO masterAttacherCardRelationshipDTO){
        MasterAttacherCardRelationship masterAttacherCardRelationship = BeanUtil.toBean(masterAttacherCardRelationshipDTO, MasterAttacherCardRelationship.class);
        masterAttacherCardRelationship.setUpdateTime(LocalDateTime.now());

        boolean save = masterAttacherCardRelationshipService.updateById(masterAttacherCardRelationship);

        return save;

    }

    @GetMapping("/delete")
    public boolean delete(@RequestParam(value = "id") int id){

        boolean save = masterAttacherCardRelationshipService.removeById(id);

        return save;

    }

    @GetMapping("/find")
    public MasterAttacherCardRelationship find(@RequestParam(value = "id") int id){

        MasterAttacherCardRelationship masterAttacherCardRelationship = masterAttacherCardRelationshipService.getById(id);

        return masterAttacherCardRelationship;

    }

    @GetMapping("/updateIsUse")
    public void updateIsUse(){

        masterAttacherCardRelationshipService.updateIsUse();

    }
}
