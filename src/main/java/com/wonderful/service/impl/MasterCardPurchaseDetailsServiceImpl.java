package com.wonderful.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wonderful.bean.dto.MasterCardPurchaseDetailsDTO;
import com.wonderful.bean.entity.MasterCardPurchaseDetails;
import com.wonderful.dao.MasterCardPurchaseDetailsMapper;
import com.wonderful.service.MasterCardPurchaseDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class MasterCardPurchaseDetailsServiceImpl extends ServiceImpl<MasterCardPurchaseDetailsMapper, MasterCardPurchaseDetails> implements MasterCardPurchaseDetailsService {

    @Override
    public IPage<MasterCardPurchaseDetails> page(MasterCardPurchaseDetailsDTO masterCardPurchaseDetailsDTO) {

        LambdaQueryWrapper<MasterCardPurchaseDetails> wrapper = new LambdaQueryWrapper<MasterCardPurchaseDetails>();
        wrapper.orderByDesc(MasterCardPurchaseDetails::getCreateTime);
        wrapper.like(!StringUtils.isEmpty(masterCardPurchaseDetailsDTO.getOpenCardCompanyName()),MasterCardPurchaseDetails::getOpenCardCompanyName,masterCardPurchaseDetailsDTO.getOpenCardCompanyName());

        Page page = new Page();
        page.setCurrent(masterCardPurchaseDetailsDTO.getPage());
        page.setSize(masterCardPurchaseDetailsDTO.getRows());

        IPage<MasterCardPurchaseDetails> p = this.baseMapper.selectPage(page, wrapper);

        return p;
    }
}
