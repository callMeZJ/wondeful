package com.wonderful.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wonderful.bean.dto.MasterCardPurchaseDetailsDTO;
import com.wonderful.bean.dto.MasterCardPurchaseDetailsSummaryDTO;
import com.wonderful.bean.entity.MasterCardPurchaseDetails;
import com.wonderful.service.MasterCardPurchaseDetailsService;
import com.wonderful.service.MasterCardPurchaseDetailsSummaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/masterCardPurchaseDetailsSummary")
public class MasterCardPurchaseDetailsSummaryController {

    @Autowired
    private MasterCardPurchaseDetailsSummaryService masterCardPurchaseDetailsSummaryService;

    @PostMapping("/page")
    public Map<String, Object> page(MasterCardPurchaseDetailsSummaryDTO masterCardPurchaseDetailsSummaryDTO){

        Map<String, Object> map = new HashMap<>();

        IPage<MasterCardPurchaseDetailsSummaryDTO> page = masterCardPurchaseDetailsSummaryService.pageSummary(masterCardPurchaseDetailsSummaryDTO);

        if(Objects.isNull(page)){
            return null;
        }

        map.put("total",page.getTotal());
        map.put("rows",page.getRecords());

        return map;

    }
}
