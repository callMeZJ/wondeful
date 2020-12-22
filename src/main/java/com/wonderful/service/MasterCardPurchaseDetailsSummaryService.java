package com.wonderful.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wonderful.bean.dto.MasterCardPurchaseDetailsSummaryDTO;

public interface MasterCardPurchaseDetailsSummaryService  {

    IPage<MasterCardPurchaseDetailsSummaryDTO> pageSummary(MasterCardPurchaseDetailsSummaryDTO masterCardPurchaseDetailsSummaryDTO);
}
