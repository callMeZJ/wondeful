package com.wonderful.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wonderful.bean.dto.AttacherCardPointDetailsDTO;
import com.wonderful.bean.entity.AttacherCardPointDetails;
import com.wonderful.service.AttacherCardPointDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/attacherCardPointDetails")
public class AttacherCardPointDetailsController {

    @Autowired
    private AttacherCardPointDetailsService attacherCardPointDetailsService;

    @PostMapping("/page")
    public Map<String, Object> page(AttacherCardPointDetailsDTO attacherCardPointDetailsDTO){

        Map<String, Object> map = new HashMap<>();

        IPage<AttacherCardPointDetails> page = attacherCardPointDetailsService.page(attacherCardPointDetailsDTO);
        map.put("total",page.getTotal());
        map.put("rows",page.getRecords());

        return map;

    }

    @PostMapping("/save")
    public boolean save(AttacherCardPointDetailsDTO attacherCardPointDetailsDTO){
        AttacherCardPointDetails attacherCardPointDetails = BeanUtil.toBean(attacherCardPointDetailsDTO, AttacherCardPointDetails.class);
        attacherCardPointDetails.setCreateTime(LocalDateTime.now());
        attacherCardPointDetails.setUpdateTime(LocalDateTime.now());

        boolean save = attacherCardPointDetailsService.save(attacherCardPointDetails);

        return save;

    }

    @PostMapping("/update")
    public boolean update(AttacherCardPointDetailsDTO attacherCardPointDetailsDTO){
        AttacherCardPointDetails attacherCardPointDetails = BeanUtil.toBean(attacherCardPointDetailsDTO, AttacherCardPointDetails.class);
        attacherCardPointDetails.setUpdateTime(LocalDateTime.now());

        boolean save = attacherCardPointDetailsService.updateById(attacherCardPointDetails);

        return save;

    }

    @GetMapping("/delete")
    public boolean delete(@RequestParam(value = "id") int id){

        boolean save = attacherCardPointDetailsService.removeById(id);

        return save;

    }

    @GetMapping("/find")
    public AttacherCardPointDetails find(@RequestParam(value = "id") int id){

        AttacherCardPointDetails attacherCardPointDetails = attacherCardPointDetailsService.getById(id);

        return attacherCardPointDetails;

    }
}
