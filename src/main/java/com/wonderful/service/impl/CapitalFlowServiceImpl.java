package com.wonderful.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wonderful.bean.dto.CapitalFlowDTO;
import com.wonderful.bean.entity.CapitalFlow;
import com.wonderful.dao.CapitalFlowMapper;
import com.wonderful.service.CapitalFlowService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class CapitalFlowServiceImpl extends ServiceImpl<CapitalFlowMapper, CapitalFlow> implements CapitalFlowService {

    @Override
    public IPage<CapitalFlow> page(CapitalFlowDTO capitalFlowDTO) {

        LambdaQueryWrapper<CapitalFlow> wrapper = new LambdaQueryWrapper<CapitalFlow>();
        wrapper.orderByDesc(CapitalFlow::getCreateTime);
        wrapper.like(!StringUtils.isEmpty(capitalFlowDTO.getPayee()),CapitalFlow::getPayee,capitalFlowDTO.getPayee());
        wrapper.like(!StringUtils.isEmpty(capitalFlowDTO.getPayer()),CapitalFlow::getPayer,capitalFlowDTO.getPayer());

        Page page = new Page();
        page.setCurrent(capitalFlowDTO.getPage());
        page.setSize(capitalFlowDTO.getRows());

        IPage<CapitalFlow> p = this.baseMapper.selectPage(page, wrapper);

        return p;
    }
}
