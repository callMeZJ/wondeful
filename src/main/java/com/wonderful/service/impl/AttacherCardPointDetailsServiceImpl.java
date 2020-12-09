package com.wonderful.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wonderful.bean.dto.AttacherCardPointDetailsDTO;
import com.wonderful.bean.entity.AttacherCardPointDetails;
import com.wonderful.dao.AttacherCardPointDetailsMapper;
import com.wonderful.service.AttacherCardPointDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class AttacherCardPointDetailsServiceImpl extends ServiceImpl<AttacherCardPointDetailsMapper, AttacherCardPointDetails> implements AttacherCardPointDetailsService {

    @Override
    public IPage<AttacherCardPointDetails> page(AttacherCardPointDetailsDTO attacherCardPointDetailsDTO) {

        LambdaQueryWrapper<AttacherCardPointDetails> wrapper = new LambdaQueryWrapper<AttacherCardPointDetails>();
        wrapper.orderByDesc(AttacherCardPointDetails::getCreateTime);
        wrapper.like(!StringUtils.isEmpty(attacherCardPointDetailsDTO.getCardNum()),AttacherCardPointDetails::getCardNum,attacherCardPointDetailsDTO.getCardNum());
        wrapper.like(!StringUtils.isEmpty(attacherCardPointDetailsDTO.getCardBuyer()),AttacherCardPointDetails::getCardBuyer,attacherCardPointDetailsDTO.getCardBuyer());

        Page page = new Page();
        page.setCurrent(attacherCardPointDetailsDTO.getPage());
        page.setSize(attacherCardPointDetailsDTO.getRows());

        IPage<AttacherCardPointDetails> p = this.baseMapper.selectPage(page, wrapper);

        return p;
    }
}
