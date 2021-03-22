package com.wonderful.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wonderful.bean.dto.AttacherCardSaleDetailsSummaryDTO;

public interface AttacherCardSaleDetailsSummaryService {

    IPage<AttacherCardSaleDetailsSummaryDTO> pageSummary(AttacherCardSaleDetailsSummaryDTO attacherCardSaleDetailsSummaryDTO);

}
