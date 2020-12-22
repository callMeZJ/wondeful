package com.wonderful.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wonderful.bean.dto.AttacherCardSaleDetailsDTO;
import com.wonderful.bean.dto.MasterCardPurchaseDetailsDTO;
import com.wonderful.bean.entity.AttacherCardSaleDetails;
import com.wonderful.bean.entity.MasterCardPurchaseDetails;
import com.wonderful.dao.AttacherCardSaleDetailsMapper;
import com.wonderful.dao.MasterCardPurchaseDetailsMapper;
import com.wonderful.service.AttacherCardSaleDetailsService;
import com.wonderful.service.MasterCardPurchaseDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class AttacherCardSaleDetailsServiceImpl extends ServiceImpl<AttacherCardSaleDetailsMapper, AttacherCardSaleDetails> implements AttacherCardSaleDetailsService {

    @Override
    public IPage<AttacherCardSaleDetails> page(AttacherCardSaleDetailsDTO attacherCardSaleDetailsDTO) {

        LambdaQueryWrapper<AttacherCardSaleDetails> wrapper = new LambdaQueryWrapper<AttacherCardSaleDetails>();
        wrapper.orderByDesc(AttacherCardSaleDetails::getCreateTime);
        wrapper.like(!StringUtils.isEmpty(attacherCardSaleDetailsDTO.getCardNum()),AttacherCardSaleDetails::getCardNum,attacherCardSaleDetailsDTO.getCardNum());
        wrapper.like(!StringUtils.isEmpty(attacherCardSaleDetailsDTO.getCardholder()),AttacherCardSaleDetails::getCardholder,attacherCardSaleDetailsDTO.getCardholder());

        Page page = new Page();
        page.setCurrent(attacherCardSaleDetailsDTO.getPage());
        page.setSize(attacherCardSaleDetailsDTO.getRows());

        IPage<AttacherCardSaleDetails> p = this.baseMapper.selectPage(page, wrapper);

        return p;
    }

    @Override
    public List<AttacherCardSaleDetails> getByAttacherCardNumList(List<String> attacherCardNumList) {

        LambdaQueryWrapper<AttacherCardSaleDetails> wrapper = new LambdaQueryWrapper<AttacherCardSaleDetails>();
        wrapper.in(AttacherCardSaleDetails::getCardNum,attacherCardNumList);

        List<AttacherCardSaleDetails> attacherCardSaleDetails = this.baseMapper.selectList(wrapper);

        return attacherCardSaleDetails;

    }
}
