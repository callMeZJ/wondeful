package com.wonderful.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wonderful.bean.entity.MasterCardPurchaseDetails;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MasterCardPurchaseDetailsMapper extends BaseMapper<MasterCardPurchaseDetails> {

    IPage<MasterCardPurchaseDetails> pageForSummary(Page page, @Param("openCardCompanyName")String openCardCompanyName, @Param("masterCardNum")String masterCardNum);
}
