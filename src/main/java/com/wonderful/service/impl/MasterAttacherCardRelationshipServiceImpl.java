package com.wonderful.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wonderful.bean.dto.MasterAttacherCardRelationshipDTO;
import com.wonderful.bean.dto.MasterCardOpenBillDetailsDTO;
import com.wonderful.bean.entity.AttacherCardSaleDetails;
import com.wonderful.bean.entity.MasterAttacherCardRelationship;
import com.wonderful.bean.entity.MasterCardOpenBillDetails;
import com.wonderful.dao.MasterAttacherCardRelationshipMapper;
import com.wonderful.dao.MasterCardOpenBillDetailsMapper;
import com.wonderful.service.MasterAttacherCardRelationshipService;
import com.wonderful.service.MasterCardOpenBillDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class MasterAttacherCardRelationshipServiceImpl extends ServiceImpl<MasterAttacherCardRelationshipMapper, MasterAttacherCardRelationship> implements MasterAttacherCardRelationshipService {

    @Override
    public IPage<MasterAttacherCardRelationship> page(MasterAttacherCardRelationshipDTO masterAttacherCardRelationshipDTO) {

        LambdaQueryWrapper<MasterAttacherCardRelationship> wrapper = new LambdaQueryWrapper<MasterAttacherCardRelationship>();
        wrapper.orderByDesc(MasterAttacherCardRelationship::getCreateTime);
        wrapper.like(!StringUtils.isEmpty(masterAttacherCardRelationshipDTO.getMasterCardNum()),MasterAttacherCardRelationship::getMasterCardNum,masterAttacherCardRelationshipDTO.getMasterCardNum());
        wrapper.like(!StringUtils.isEmpty(masterAttacherCardRelationshipDTO.getAttacherCardNum()),MasterAttacherCardRelationship::getAttacherCardNum,masterAttacherCardRelationshipDTO.getAttacherCardNum());

        Page page = new Page();
        page.setCurrent(masterAttacherCardRelationshipDTO.getPage());
        page.setSize(masterAttacherCardRelationshipDTO.getRows());

        IPage<MasterAttacherCardRelationship> p = this.baseMapper.selectPage(page, wrapper);

        return p;
    }

    @Override
    public List<MasterAttacherCardRelationship> getByMasterCardNumList(List<String> masterCardNumList) {

        LambdaQueryWrapper<MasterAttacherCardRelationship> wrapper = new LambdaQueryWrapper<MasterAttacherCardRelationship>();
        wrapper.in(MasterAttacherCardRelationship::getMasterCardNum,masterCardNumList);
        List<MasterAttacherCardRelationship> masterAttacherCardRelationships = this.baseMapper.selectList(wrapper);

        return masterAttacherCardRelationships;
    }
}
