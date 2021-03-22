package com.wonderful.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wonderful.bean.dto.AttacherCardSaleDetailsSummaryDTO;
import com.wonderful.service.AttacherCardSaleDetailsSummaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/attacherCardSaleDetailsSummary")
public class AttacherCardSaleDetailsSummaryController {

    @Autowired
    private AttacherCardSaleDetailsSummaryService attacherCardSaleDetailsSummaryService;

    @PostMapping("/page")
    public Map<String, Object> page(@RequestBody  AttacherCardSaleDetailsSummaryDTO attacherCardSaleDetailsSummaryDTO){

        Map<String, Object> map = new HashMap<>();

        IPage<AttacherCardSaleDetailsSummaryDTO> page = attacherCardSaleDetailsSummaryService.pageSummary(attacherCardSaleDetailsSummaryDTO);

        if(Objects.isNull(page)){
            map.put("total",0);
            map.put("rows", Arrays.asList());
            return map;
        }

        map.put("total",page.getTotal());
        map.put("rows",page.getRecords());

        return map;

    }
}
