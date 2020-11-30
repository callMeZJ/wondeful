package com.wonderful.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wonderful.bean.dto.MasterCardOpenBillDetailsDTO;
import com.wonderful.bean.dto.MasterCardPurchaseDetailsDTO;
import com.wonderful.bean.entity.MasterCardOpenBillDetails;
import com.wonderful.bean.entity.MasterCardPurchaseDetails;


public interface MasterCardOpenBillDetailsService extends IService<MasterCardOpenBillDetails> {

    IPage<MasterCardOpenBillDetails> page(MasterCardOpenBillDetailsDTO masterCardOpenBillDetailsDTO);
}
