package com.wonderful.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wonderful.bean.dto.MasterCardPurchaseDetailsDTO;
import com.wonderful.bean.dto.MasterCardPurchaseDetailsSummaryDTO;
import com.wonderful.bean.entity.*;
import com.wonderful.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MasterCardPurchaseDetailsSummaryServiceImpl implements MasterCardPurchaseDetailsSummaryService {

    @Autowired
    private MasterCardPurchaseDetailsService masterCardPurchaseDetailsService;
    @Autowired
    private MasterAttacherCardRelationshipService masterAttacherCardRelationshipService;
    @Autowired
    private AttacherCardSaleDetailsService attacherCardSaleDetailsService;
    @Autowired
    private AttacherCardPointDetailsService attacherCardPointDetailsService;
    @Autowired
    private MasterCardOpenBillDetailsService masterCardOpenBillDetailsService;

    @Override
    public IPage<MasterCardPurchaseDetailsSummaryDTO> pageSummary(MasterCardPurchaseDetailsSummaryDTO masterCardPurchaseDetailsSummaryDTO) {

        //主卡进货明细根据主卡号充值金额、积分求和
        MasterCardPurchaseDetailsDTO masterCardPurchaseDetailsDTO = BeanUtil.toBean(masterCardPurchaseDetailsSummaryDTO, MasterCardPurchaseDetailsDTO.class);
        IPage<MasterCardPurchaseDetails> purchaseDetailsPage = masterCardPurchaseDetailsService.pageForSummary(masterCardPurchaseDetailsDTO);
        if(purchaseDetailsPage.getTotal()==0l){
            return null;
        }
        //根据主卡号获取副卡信息
        List<MasterCardPurchaseDetails> records = purchaseDetailsPage.getRecords();
        List<String> masterCardNumList = records.stream().map(o -> o.getMasterCardNum()).collect(Collectors.toList());
        List<MasterAttacherCardRelationship> masterAttacherCardRelationshipList = masterAttacherCardRelationshipService.getByMasterCardNumList(masterCardNumList);
        //查询主卡号下面副卡销售数据
        List<String> attacherCardNumList = masterAttacherCardRelationshipList.stream().map(o -> o.getAttacherCardNum()).collect(Collectors.toList());
        List<AttacherCardSaleDetails> attacherCardSaleDetailsList = attacherCardSaleDetailsService.getByAttacherCardNumList(attacherCardNumList);
        //查询主卡号下面副卡积分销售数据
        List<AttacherCardPointDetails> attacherCardPointDetails = attacherCardPointDetailsService.getByAttacherCardNumList(attacherCardNumList);
        //查询主卡开票数据
        List<MasterCardOpenBillDetails> masterCardOpenBillDetails = masterCardOpenBillDetailsService.getByMasterCardNumList(masterCardNumList);
        //处理返回数据
        List<MasterCardPurchaseDetailsSummaryDTO> handleRecords = records.stream().map(o -> {
            String masterCardNum = o.getMasterCardNum();
            List<String> collect = masterAttacherCardRelationshipList.stream().filter(macrInfo -> macrInfo.getMasterCardNum().equals(masterCardNum)).map(MasterAttacherCardRelationship::getAttacherCardNum).collect(Collectors.toList());
            //副卡销售额求和
            BigDecimal distributionAmountSum = attacherCardSaleDetailsList.stream()
                    .filter(acseInfo -> collect.contains(acseInfo.getCardNum()))
                    .map(AttacherCardSaleDetails::getDistributionAmount)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            //副卡销售积分求和
            BigDecimal rechargePointSum = attacherCardPointDetails.stream()
                    .filter(acpdInfo -> collect.contains(acpdInfo.getCardNum()))
                    .map(AttacherCardPointDetails::getRechargePoint)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            //主卡开票金额
            BigDecimal openBillAmountSum = masterCardOpenBillDetails.stream()
                    .filter(mcobd -> mcobd.getMasterCardNum().equals(masterCardNum))
                    .map(MasterCardOpenBillDetails::getOpenBillAmount)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            MasterCardPurchaseDetailsSummaryDTO build = MasterCardPurchaseDetailsSummaryDTO.builder()
                    .openCardCompanyName(o.getOpenCardCompanyName())
                    .masterCardNum(masterCardNum)
                    .rechargeAmountSum(o.getRechargeAmount())
                    .saleAmount(distributionAmountSum)
                    .masterSurplusAmount(o.getRechargeAmount().subtract(distributionAmountSum))
                    .getPoint(o.getPoints())
                    .salePoint(rechargePointSum)
                    .openBillAmount(openBillAmountSum)
                    .noOpenBillAmount(o.getRechargeAmount().subtract(openBillAmountSum)).build();
            return build;
        }).collect(Collectors.toList());

        //构建返回
        Page<MasterCardPurchaseDetailsSummaryDTO> page = new Page<MasterCardPurchaseDetailsSummaryDTO>();
        page.setCurrent(purchaseDetailsPage.getCurrent());
        page.setSize(purchaseDetailsPage.getSize());
        page.setTotal(purchaseDetailsPage.getTotal());
        page.setRecords(handleRecords);
        return page;
    }
}
