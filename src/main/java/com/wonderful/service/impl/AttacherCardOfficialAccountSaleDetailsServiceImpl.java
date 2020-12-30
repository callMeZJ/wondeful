package com.wonderful.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wonderful.bean.dto.AttacherCardOfficialAccountSaleDetailsDTO;
import com.wonderful.bean.dto.MasterCardPurchaseDetailsDTO;
import com.wonderful.bean.entity.AttacherCardOfficialAccountSaleDetails;
import com.wonderful.bean.entity.AttacherCardSaleDetails;
import com.wonderful.bean.entity.MasterCardPurchaseDetails;
import com.wonderful.dao.AttacherCardOfficialAccountSaleDetailsMapper;
import com.wonderful.dao.MasterCardPurchaseDetailsMapper;
import com.wonderful.service.AttacherCardOfficialAccountSaleDetailsService;
import com.wonderful.service.MasterCardPurchaseDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class AttacherCardOfficialAccountSaleDetailsServiceImpl extends ServiceImpl<AttacherCardOfficialAccountSaleDetailsMapper, AttacherCardOfficialAccountSaleDetails> implements AttacherCardOfficialAccountSaleDetailsService {

    @Override
    public IPage<AttacherCardOfficialAccountSaleDetails> page(AttacherCardOfficialAccountSaleDetailsDTO attacherCardOfficialAccountSaleDetailsDTO) {

        LambdaQueryWrapper<AttacherCardOfficialAccountSaleDetails> wrapper = new LambdaQueryWrapper<AttacherCardOfficialAccountSaleDetails>();
        wrapper.orderByDesc(AttacherCardOfficialAccountSaleDetails::getCreateTime);
        wrapper.like(!StringUtils.isEmpty(attacherCardOfficialAccountSaleDetailsDTO.getRealName()),AttacherCardOfficialAccountSaleDetails::getRealName,attacherCardOfficialAccountSaleDetailsDTO.getRealName());
        wrapper.like(!StringUtils.isEmpty(attacherCardOfficialAccountSaleDetailsDTO.getOilCardNum()),AttacherCardOfficialAccountSaleDetails::getOilCardNum,attacherCardOfficialAccountSaleDetailsDTO.getOilCardNum());

        Page page = new Page();
        page.setCurrent(attacherCardOfficialAccountSaleDetailsDTO.getPage());
        page.setSize(attacherCardOfficialAccountSaleDetailsDTO.getRows());

        IPage<AttacherCardOfficialAccountSaleDetails> p = this.baseMapper.selectPage(page, wrapper);

        return p;
    }

    @Override
    public List<AttacherCardOfficialAccountSaleDetails> getIsNotComparison() {
        LambdaQueryWrapper<AttacherCardOfficialAccountSaleDetails> wrapper = new LambdaQueryWrapper<AttacherCardOfficialAccountSaleDetails>();
        wrapper.eq(AttacherCardOfficialAccountSaleDetails::getIsComparison,"no");

        List<AttacherCardOfficialAccountSaleDetails> attacherCardOfficialAccountSaleDetails = this.baseMapper.selectList(wrapper);

        return attacherCardOfficialAccountSaleDetails;
    }
}
