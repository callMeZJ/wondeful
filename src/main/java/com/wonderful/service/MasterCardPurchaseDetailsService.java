package com.wonderful.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wonderful.bean.dto.MasterCardPurchaseDetailsDTO;
import com.wonderful.bean.entity.MasterCardPurchaseDetails;

public interface MasterCardPurchaseDetailsService extends IService<MasterCardPurchaseDetails> {

    IPage<MasterCardPurchaseDetails> page(MasterCardPurchaseDetailsDTO masterCardPurchaseDetailsDTO);

    IPage<MasterCardPurchaseDetails> pageForSummary(MasterCardPurchaseDetailsDTO masterCardPurchaseDetailsDTO);
}
