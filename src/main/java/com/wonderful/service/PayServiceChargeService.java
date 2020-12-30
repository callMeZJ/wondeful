package com.wonderful.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wonderful.bean.dto.PayServiceChargeDTO;
import com.wonderful.bean.entity.PayServiceCharge;

import java.math.BigDecimal;
import java.util.List;

public interface PayServiceChargeService extends IService<PayServiceCharge> {

    IPage<PayServiceCharge> page(PayServiceChargeDTO payServiceChargeDTO);

    PayServiceCharge getServiceFeeByWay(String way);

    List<PayServiceCharge> getPayServie();
}
