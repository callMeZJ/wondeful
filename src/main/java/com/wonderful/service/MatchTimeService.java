package com.wonderful.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wonderful.bean.dto.MatchTimeDTO;
import com.wonderful.bean.dto.PayServiceChargeDTO;
import com.wonderful.bean.entity.MatchTime;
import com.wonderful.bean.entity.PayServiceCharge;

import java.util.List;

public interface MatchTimeService extends IService<MatchTime> {

    IPage<MatchTime> page(MatchTimeDTO matchTimeDTO);

    MatchTime findOnlyOne();
}
