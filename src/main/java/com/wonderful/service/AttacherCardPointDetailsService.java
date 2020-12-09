package com.wonderful.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wonderful.bean.dto.AttacherCardPointDetailsDTO;
import com.wonderful.bean.dto.MasterCardPurchaseDetailsDTO;
import com.wonderful.bean.entity.AttacherCardPointDetails;
import com.wonderful.bean.entity.MasterCardPurchaseDetails;

public interface AttacherCardPointDetailsService extends IService<AttacherCardPointDetails> {

    IPage<AttacherCardPointDetails> page(AttacherCardPointDetailsDTO attacherCardPointDetailsDTO);
}
