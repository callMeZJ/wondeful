package com.wonderful.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wonderful.bean.dto.MatchTimeDTO;
import com.wonderful.bean.dto.PayServiceChargeDTO;
import com.wonderful.bean.entity.MatchTime;
import com.wonderful.bean.entity.PayServiceCharge;
import com.wonderful.dao.MatchTimeMapper;
import com.wonderful.dao.PayServiceChargeMapper;
import com.wonderful.service.MatchTimeService;
import com.wonderful.service.PayServiceChargeService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

@Service
public class MatchTimeServiceImpl extends ServiceImpl<MatchTimeMapper, MatchTime> implements MatchTimeService {

    @Override
    public IPage<MatchTime> page(MatchTimeDTO matchTimeDTO) {

        LambdaQueryWrapper<MatchTime> wrapper = new LambdaQueryWrapper<MatchTime>();
        wrapper.orderByDesc(MatchTime::getCreateTime);

        Page page = new Page();
        page.setCurrent(matchTimeDTO.getPage());
        page.setSize(matchTimeDTO.getRows());

        IPage<MatchTime> p = this.baseMapper.selectPage(page, wrapper);

        return p;
    }

    @Override
    public MatchTime findOnlyOne() {

        LambdaQueryWrapper<MatchTime> wrapper = new LambdaQueryWrapper<MatchTime>();
        List<MatchTime> matchTimes = this.baseMapper.selectList(wrapper);

        if(CollectionUtils.isEmpty(matchTimes)){
            return null;
        }else{
            return matchTimes.get(0);
        }

    }

}
