package com.wonderful.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wonderful.bean.dto.AttacherCardSaleComparisonDetailsDTO;
import com.wonderful.bean.dto.AttacherCardSaleDetailsDTO;
import com.wonderful.bean.entity.AttacherCardSaleComparisonDetails;
import com.wonderful.bean.entity.AttacherCardSaleDetails;

import java.util.List;

public interface AttacherCardSaleComparisonDetailsService extends IService<AttacherCardSaleComparisonDetails> {

    IPage<AttacherCardSaleComparisonDetails> page(AttacherCardSaleComparisonDetailsDTO attacherCardSaleComparisonDetailsDTO);

}
