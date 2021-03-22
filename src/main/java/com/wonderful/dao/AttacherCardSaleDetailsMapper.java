package com.wonderful.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wonderful.bean.dto.AttacherCardSaleDetailsSummaryDTO;
import com.wonderful.bean.entity.AttacherCardSaleDetails;
import com.wonderful.bean.entity.MasterCardPurchaseDetails;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AttacherCardSaleDetailsMapper extends BaseMapper<AttacherCardSaleDetails> {

    IPage<AttacherCardSaleDetailsSummaryDTO> pageForSummary(Page page, @Param("cardNum")String cardNum);
}
