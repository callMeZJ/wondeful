package com.wonderful.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wonderful.bean.dto.PayServiceChargeDTO;
import com.wonderful.bean.entity.PayServiceCharge;

public interface PayServiceChargeService extends IService<PayServiceCharge> {

    IPage<PayServiceCharge> page(PayServiceChargeDTO payServiceChargeDTO);
}
