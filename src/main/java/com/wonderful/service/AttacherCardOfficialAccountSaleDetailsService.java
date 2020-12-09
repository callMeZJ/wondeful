package com.wonderful.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wonderful.bean.dto.AttacherCardOfficialAccountSaleDetailsDTO;
import com.wonderful.bean.entity.AttacherCardOfficialAccountSaleDetails;

public interface AttacherCardOfficialAccountSaleDetailsService extends IService<AttacherCardOfficialAccountSaleDetails> {

    IPage<AttacherCardOfficialAccountSaleDetails> page(AttacherCardOfficialAccountSaleDetailsDTO attacherCardOfficialAccountSaleDetailsDTO);
}
