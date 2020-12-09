package com.wonderful.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wonderful.bean.dto.CustomerInfoDTO;
import com.wonderful.bean.entity.CustomerInfo;

public interface CustomerInfoService extends IService<CustomerInfo> {

    IPage<CustomerInfo> page(CustomerInfoDTO customerInfoDTO);
}
