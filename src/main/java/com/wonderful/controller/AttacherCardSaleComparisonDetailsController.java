package com.wonderful.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wonderful.bean.dto.AttacherCardSaleComparisonDetailsDTO;
import com.wonderful.bean.dto.AttacherCardSaleDetailsDTO;
import com.wonderful.bean.entity.AttacherCardSaleComparisonDetails;
import com.wonderful.bean.entity.AttacherCardSaleDetails;
import com.wonderful.service.AttacherCardSaleComparisonDetailsService;
import com.wonderful.service.AttacherCardSaleDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/attacherCardSaleComparisonDetails")
public class AttacherCardSaleComparisonDetailsController {

    @Autowired
    private AttacherCardSaleComparisonDetailsService attacherCardSaleComparisonDetailsService;

    @PostMapping("/page")
    public Map<String, Object> page(AttacherCardSaleComparisonDetailsDTO attacherCardSaleComparisonDetailsDTO){

        Map<String, Object> map = new HashMap<>();

        IPage<AttacherCardSaleComparisonDetails> page = attacherCardSaleComparisonDetailsService.page(attacherCardSaleComparisonDetailsDTO);
        map.put("total",page.getTotal());
        map.put("rows",page.getRecords());

        return map;

    }

    @PostMapping("/save")
    public boolean save(AttacherCardSaleComparisonDetailsDTO attacherCardSaleComparisonDetailsDTO){
        AttacherCardSaleComparisonDetails attacherCardSaleComparisonDetails = BeanUtil.toBean(attacherCardSaleComparisonDetailsDTO, AttacherCardSaleComparisonDetails.class);
        attacherCardSaleComparisonDetails.setCreateTime(LocalDateTime.now());
        attacherCardSaleComparisonDetails.setUpdateTime(LocalDateTime.now());

        boolean save = attacherCardSaleComparisonDetailsService.save(attacherCardSaleComparisonDetails);

        return save;

    }

    @PostMapping("/update")
    public boolean update(AttacherCardSaleComparisonDetailsDTO attacherCardSaleComparisonDetailsDTO){
        AttacherCardSaleComparisonDetails attacherCardSaleComparisonDetails = BeanUtil.toBean(attacherCardSaleComparisonDetailsDTO, AttacherCardSaleComparisonDetails.class);
        attacherCardSaleComparisonDetails.setUpdateTime(LocalDateTime.now());

        boolean save = attacherCardSaleComparisonDetailsService.updateById(attacherCardSaleComparisonDetails);

        return save;

    }

    @GetMapping("/delete")
    public boolean delete(@RequestParam(value = "id") int id){

        boolean save = attacherCardSaleComparisonDetailsService.removeById(id);

        return save;

    }

    @GetMapping("/find")
    public AttacherCardSaleComparisonDetails find(@RequestParam(value = "id") int id){

        AttacherCardSaleComparisonDetails attacherCardSaleComparisonDetails = attacherCardSaleComparisonDetailsService.getById(id);

        return attacherCardSaleComparisonDetails;

    }
}
