package com.wonderful.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wonderful.bean.dto.CapitalFlowDTO;
import com.wonderful.bean.dto.MasterCardPurchaseDetailsDTO;
import com.wonderful.bean.entity.CapitalFlow;
import com.wonderful.bean.entity.MasterCardPurchaseDetails;

public interface CapitalFlowService extends IService<CapitalFlow> {

    IPage<CapitalFlow> page(CapitalFlowDTO capitalFlowDTO);
}
