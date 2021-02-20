package com.wonderful.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wonderful.bean.dto.MasterCardPurchaseDetailsDTO;
import com.wonderful.bean.entity.MasterCardPurchaseDetails;
import com.wonderful.dao.MasterCardPurchaseDetailsMapper;
import com.wonderful.service.MasterCardPurchaseDetailsService;
import com.wonderful.service.MasterCardPurchaseDetailsSummaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class MasterCardPurchaseDetailsServiceImpl extends ServiceImpl<MasterCardPurchaseDetailsMapper, MasterCardPurchaseDetails> implements MasterCardPurchaseDetailsService {

    @Autowired
    private MasterCardPurchaseDetailsMapper masterCardPurchaseDetailsMapper;


    @Override
    public IPage<MasterCardPurchaseDetails> page(MasterCardPurchaseDetailsDTO masterCardPurchaseDetailsDTO) {

        LambdaQueryWrapper<MasterCardPurchaseDetails> wrapper = new LambdaQueryWrapper<MasterCardPurchaseDetails>();
        wrapper.orderByDesc(MasterCardPurchaseDetails::getCreateTime);
        wrapper.like(!StringUtils.isEmpty(masterCardPurchaseDetailsDTO.getOpenCardCompanyName()),MasterCardPurchaseDetails::getOpenCardCompanyName,masterCardPurchaseDetailsDTO.getOpenCardCompanyName());
        wrapper.like(!StringUtils.isEmpty(masterCardPurchaseDetailsDTO.getMasterCardNum()),MasterCardPurchaseDetails::getMasterCardNum,masterCardPurchaseDetailsDTO.getMasterCardNum());

        Page page = new Page();
        page.setCurrent(masterCardPurchaseDetailsDTO.getPage());
        page.setSize(masterCardPurchaseDetailsDTO.getRows());

        IPage<MasterCardPurchaseDetails> p = this.baseMapper.selectPage(page, wrapper);

        return p;
    }

    @Override
    public IPage<MasterCardPurchaseDetails> pageForSummary(MasterCardPurchaseDetailsDTO masterCardPurchaseDetailsDTO) {

        /*QueryWrapper<MasterCardPurchaseDetails> wrapper = new QueryWrapper<MasterCardPurchaseDetails>();
        wrapper.orderByDesc("create_time");
        wrapper.like(!StringUtils.isEmpty(masterCardPurchaseDetailsDTO.getOpenCardCompanyName()),"open_card_company_name",masterCardPurchaseDetailsDTO.getOpenCardCompanyName());
        wrapper.like(!StringUtils.isEmpty(masterCardPurchaseDetailsDTO.getMasterCardNum()),"master_card_num",masterCardPurchaseDetailsDTO.getMasterCardNum());
        wrapper.select("SELECT ANY_VALUE(open_card_company_name) AS open_card_company_name,ANY_VALUE(master_card_num) AS master_card_num,SUM(a.recharge_amount) AS recharge_amount ,SUM(a.points) AS points FROM master_card_purchase_details a GROUP BY a.master_card_num");
*/
        Page page = new Page();
        page.setCurrent(masterCardPurchaseDetailsDTO.getPage());
        page.setSize(masterCardPurchaseDetailsDTO.getRows());

        //IPage<MasterCardPurchaseDetails> p = this.baseMapper.selectPage(page, wrapper);
        IPage<MasterCardPurchaseDetails> p = masterCardPurchaseDetailsMapper.pageForSummary(page, masterCardPurchaseDetailsDTO.getOpenCardCompanyName(), masterCardPurchaseDetailsDTO.getMasterCardNum());

        return p;
    }
}
