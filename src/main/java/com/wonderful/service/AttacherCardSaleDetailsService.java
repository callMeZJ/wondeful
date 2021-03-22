package com.wonderful.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wonderful.bean.dto.AttacherCardSaleDetailsDTO;
import com.wonderful.bean.dto.AttacherCardSaleDetailsSummaryDTO;
import com.wonderful.bean.dto.MasterCardPurchaseDetailsDTO;
import com.wonderful.bean.entity.AttacherCardSaleDetails;
import com.wonderful.bean.entity.MasterCardPurchaseDetails;

import java.util.List;

public interface AttacherCardSaleDetailsService extends IService<AttacherCardSaleDetails> {

    IPage<AttacherCardSaleDetails> page(AttacherCardSaleDetailsDTO attacherCardSaleDetailsDTO);

    IPage<AttacherCardSaleDetailsSummaryDTO> pageForSummary(AttacherCardSaleDetailsDTO attacherCardSaleDetailsDTO);

    List<AttacherCardSaleDetails> getByAttacherCardNumList(List<String> attacherCardNumList);

    List<AttacherCardSaleDetails> getIsNotSynchronise();
}
