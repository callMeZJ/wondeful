package com.wonderful.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wonderful.bean.dto.MasterCardOpenBillDetailsDTO;
import com.wonderful.bean.dto.MasterCardPurchaseDetailsDTO;
import com.wonderful.bean.entity.MasterCardOpenBillDetails;
import com.wonderful.bean.entity.MasterCardPurchaseDetails;
import com.wonderful.dao.MasterCardOpenBillDetailsMapper;
import com.wonderful.dao.MasterCardPurchaseDetailsMapper;
import com.wonderful.service.MasterCardOpenBillDetailsService;
import com.wonderful.service.MasterCardPurchaseDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class MasterCardOpenBillDetailsServiceImpl extends ServiceImpl<MasterCardOpenBillDetailsMapper, MasterCardOpenBillDetails> implements MasterCardOpenBillDetailsService {

    @Override
    public IPage<MasterCardOpenBillDetails> page(MasterCardOpenBillDetailsDTO masterCardOpenBillDetailsDTO) {

        LambdaQueryWrapper<MasterCardOpenBillDetails> wrapper = new LambdaQueryWrapper<MasterCardOpenBillDetails>();
        wrapper.orderByDesc(MasterCardOpenBillDetails::getCreateTime);
        wrapper.like(!StringUtils.isEmpty(masterCardOpenBillDetailsDTO.getOpenBillCompany()),MasterCardOpenBillDetails::getOpenBillCompany,masterCardOpenBillDetailsDTO.getOpenBillCompany());
        wrapper.like(!StringUtils.isEmpty(masterCardOpenBillDetailsDTO.getMasterCardNum()),MasterCardOpenBillDetails::getMasterCardNum,masterCardOpenBillDetailsDTO.getMasterCardNum());

        Page page = new Page();
        page.setCurrent(masterCardOpenBillDetailsDTO.getPage());
        page.setSize(masterCardOpenBillDetailsDTO.getRows());

        IPage<MasterCardOpenBillDetails> p = this.baseMapper.selectPage(page, wrapper);

        return p;
    }

    @Override
    public List<MasterCardOpenBillDetails> getByMasterCardNumList(List<String> masterCardNumList) {

        LambdaQueryWrapper<MasterCardOpenBillDetails> wrapper = new LambdaQueryWrapper<MasterCardOpenBillDetails>();
        wrapper.in(MasterCardOpenBillDetails::getMasterCardNum,masterCardNumList);
        List<MasterCardOpenBillDetails> masterCardOpenBillDetails = this.baseMapper.selectList(wrapper);

        return masterCardOpenBillDetails;

    }
}
