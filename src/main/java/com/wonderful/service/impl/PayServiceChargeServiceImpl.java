package com.wonderful.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wonderful.bean.dto.MasterCardPurchaseDetailsDTO;
import com.wonderful.bean.dto.PayServiceChargeDTO;
import com.wonderful.bean.entity.MasterCardPurchaseDetails;
import com.wonderful.bean.entity.PayServiceCharge;
import com.wonderful.dao.MasterCardPurchaseDetailsMapper;
import com.wonderful.dao.PayServiceChargeMapper;
import com.wonderful.service.MasterCardPurchaseDetailsService;
import com.wonderful.service.PayServiceChargeService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.List;

@Service
public class PayServiceChargeServiceImpl extends ServiceImpl<PayServiceChargeMapper, PayServiceCharge> implements PayServiceChargeService {

    @Override
    public IPage<PayServiceCharge> page(PayServiceChargeDTO payServiceChargeDTO) {

        LambdaQueryWrapper<PayServiceCharge> wrapper = new LambdaQueryWrapper<PayServiceCharge>();
        wrapper.orderByDesc(PayServiceCharge::getCreateTime);

        Page page = new Page();
        page.setCurrent(payServiceChargeDTO.getPage());
        page.setSize(payServiceChargeDTO.getRows());

        IPage<PayServiceCharge> p = this.baseMapper.selectPage(page, wrapper);

        return p;
    }

    @Override
    public PayServiceCharge getServiceFeeByWay(String way) {

        LambdaQueryWrapper<PayServiceCharge> wrapper = new LambdaQueryWrapper<PayServiceCharge>();
        wrapper.eq(PayServiceCharge::getWay,way);

        PayServiceCharge payServiceCharge = this.baseMapper.selectOne(wrapper);
        return payServiceCharge;
    }

    @Override
    public List<PayServiceCharge> getPayServie() {

        LambdaQueryWrapper<PayServiceCharge> wrapper = new LambdaQueryWrapper<PayServiceCharge>();
        return this.baseMapper.selectList(wrapper);
    }
}
