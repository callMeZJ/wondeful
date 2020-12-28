package com.wonderful.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wonderful.bean.dto.AttacherCardSaleComparisonDetailsDTO;
import com.wonderful.bean.dto.AttacherCardSaleDetailsDTO;
import com.wonderful.bean.entity.AttacherCardSaleComparisonDetails;
import com.wonderful.bean.entity.AttacherCardSaleDetails;
import com.wonderful.dao.AttacherCardSaleComparisonDetailsMapper;
import com.wonderful.dao.AttacherCardSaleDetailsMapper;
import com.wonderful.service.AttacherCardSaleComparisonDetailsService;
import com.wonderful.service.AttacherCardSaleDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class AttacherCardSaleComparisonDetailsServiceImpl extends ServiceImpl<AttacherCardSaleComparisonDetailsMapper, AttacherCardSaleComparisonDetails> implements AttacherCardSaleComparisonDetailsService {

    @Override
    public IPage<AttacherCardSaleComparisonDetails> page(AttacherCardSaleComparisonDetailsDTO attacherCardSaleComparisonDetailsDTO) {

        LambdaQueryWrapper<AttacherCardSaleComparisonDetails> wrapper = new LambdaQueryWrapper<AttacherCardSaleComparisonDetails>();
        wrapper.orderByDesc(AttacherCardSaleComparisonDetails::getCreateTime);
        wrapper.like(!StringUtils.isEmpty(attacherCardSaleComparisonDetailsDTO.getCardNum()),AttacherCardSaleComparisonDetails::getCardNum,attacherCardSaleComparisonDetailsDTO.getCardNum());
        wrapper.like(!StringUtils.isEmpty(attacherCardSaleComparisonDetailsDTO.getMasterCardNum()),AttacherCardSaleComparisonDetails::getMasterCardNum,attacherCardSaleComparisonDetailsDTO.getMasterCardNum());

        Page page = new Page();
        page.setCurrent(attacherCardSaleComparisonDetailsDTO.getPage());
        page.setSize(attacherCardSaleComparisonDetailsDTO.getRows());

        IPage<AttacherCardSaleComparisonDetails> p = this.baseMapper.selectPage(page, wrapper);

        return p;
    }

}
