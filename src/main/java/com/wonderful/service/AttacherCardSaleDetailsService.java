package com.wonderful.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wonderful.bean.dto.AttacherCardSaleDetailsDTO;
import com.wonderful.bean.entity.AttacherCardSaleDetails;

public interface AttacherCardSaleDetailsService extends IService<AttacherCardSaleDetails> {

    IPage<AttacherCardSaleDetails> page(AttacherCardSaleDetailsDTO attacherCardSaleDetailsDTO);
}
