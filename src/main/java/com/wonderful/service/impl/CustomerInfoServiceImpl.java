package com.wonderful.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wonderful.bean.dto.CustomerInfoDTO;
import com.wonderful.bean.entity.CustomerInfo;
import com.wonderful.dao.CustomerInfoMapper;
import com.wonderful.service.CustomerInfoService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class CustomerInfoServiceImpl extends ServiceImpl<CustomerInfoMapper, CustomerInfo> implements CustomerInfoService {

    @Override
    public IPage<CustomerInfo> page(CustomerInfoDTO customerInfoDTO) {

        LambdaQueryWrapper<CustomerInfo> wrapper = new LambdaQueryWrapper<CustomerInfo>();
        wrapper.orderByDesc(CustomerInfo::getCreateTime);
        wrapper.like(!StringUtils.isEmpty(customerInfoDTO.getCustomerName()),CustomerInfo::getCustomerName,customerInfoDTO.getCustomerName());
        wrapper.like(!StringUtils.isEmpty(customerInfoDTO.getTelNum()),CustomerInfo::getTelNum,customerInfoDTO.getTelNum());

        Page page = new Page();
        page.setCurrent(customerInfoDTO.getPage());
        page.setSize(customerInfoDTO.getRows());

        IPage<CustomerInfo> p = this.baseMapper.selectPage(page, wrapper);

        return p;
    }

    @Override
    public List<CustomerInfo> getByCustomerNum(List<String> customerNums) {
        LambdaQueryWrapper<CustomerInfo> wrapper = new LambdaQueryWrapper<CustomerInfo>();
        wrapper.in(CustomerInfo::getCustomerNum,customerNums);
        List<CustomerInfo> customerInfos = this.baseMapper.selectList(wrapper);
        return customerInfos;
    }
}
