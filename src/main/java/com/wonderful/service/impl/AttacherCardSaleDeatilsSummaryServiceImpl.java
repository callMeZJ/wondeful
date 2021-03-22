package com.wonderful.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wonderful.bean.dto.AttacherCardSaleDetailsDTO;
import com.wonderful.bean.dto.AttacherCardSaleDetailsSummaryDTO;
import com.wonderful.bean.dto.MasterCardPurchaseDetailsDTO;
import com.wonderful.bean.dto.MasterCardPurchaseDetailsSummaryDTO;
import com.wonderful.bean.entity.*;
import com.wonderful.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AttacherCardSaleDeatilsSummaryServiceImpl implements AttacherCardSaleDetailsSummaryService {

    @Autowired
    private AttacherCardSaleDetailsService attacherCardSaleDetailsService;
    @Autowired
    private MasterAttacherCardRelationshipService masterAttacherCardRelationshipService;
    @Autowired
    private AttacherCardPointDetailsService attacherCardPointDetailsService;
    @Autowired
    private AttacherCardSaleComparisonDetailsService attacherCardSaleComparisonDetailsService;
    @Autowired
    private AttacherCardOfficialAccountSaleDetailsService attacherCardOfficialAccountSaleDetailsService;
    @Autowired
    private CustomerInfoService customerInfoService;


    @Override
    public IPage<AttacherCardSaleDetailsSummaryDTO> pageSummary(AttacherCardSaleDetailsSummaryDTO attacherCardSaleDetailsSummaryDTO) {

        //根据副卡号 石化分配金额累加 石化充值次数累加等
        AttacherCardSaleDetailsDTO attacherCardSaleDetailsDTO = BeanUtil.toBean(attacherCardSaleDetailsSummaryDTO, AttacherCardSaleDetailsDTO.class);
        IPage<AttacherCardSaleDetailsSummaryDTO> page = attacherCardSaleDetailsService.pageForSummary(attacherCardSaleDetailsDTO);
        if(page.getTotal()==0l){
            return null;
        }
        //根据副卡号获取主副卡信息
        List<AttacherCardSaleDetailsSummaryDTO> records = page.getRecords();
        List<String> attacherCardNumList = records.stream().map(o -> o.getCardNum()).collect(Collectors.toList());
        attacherCardNumList.add("nothing");
        List<MasterAttacherCardRelationship> masterAttacherCardRelationshipList = masterAttacherCardRelationshipService.getByAttacherCardNumList(attacherCardNumList);
        if(CollectionUtils.isEmpty(masterAttacherCardRelationshipList)){
            return null;
        }
        //查询副卡积分销售数据
        List<AttacherCardPointDetails> attacherCardPointDetails = attacherCardPointDetailsService.getByAttacherCardNumList(attacherCardNumList);
        Map<String, Long> map = attacherCardPointDetails.stream().collect(Collectors.groupingBy(obj -> obj.getCardNum(), Collectors.counting()));

        List<AttacherCardSaleDetailsSummaryDTO> data = records.stream().map(o -> {
            String cardNum = o.getCardNum();//副卡号
            List<MasterAttacherCardRelationship> masterAttacherCardRelationships = masterAttacherCardRelationshipList.stream().filter(o1 -> o1.getAttacherCardNum().equals(cardNum)).collect(Collectors.toList());
            if(!CollectionUtils.isEmpty(masterAttacherCardRelationships)){
                MasterAttacherCardRelationship masterAttacherCardRelationship = masterAttacherCardRelationships.get(0);
                String masterCardNum = masterAttacherCardRelationship.getMasterCardNum();//主卡号
                String cardBuyer = masterAttacherCardRelationship.getCardBuyer();//购卡人
                BigDecimal discount = masterAttacherCardRelationship.getDiscount();//折扣
                String oils = masterAttacherCardRelationship.getOils();//油品
                String status = masterAttacherCardRelationship.getStatus();//状态
                o.setMasterCardNum(masterCardNum);
                o.setCardNum(cardNum);
                o.setCardBuyer(cardBuyer);
                o.setOils(oils);
                o.setStatus(status);
                o.setDiscount(discount);
            }

            //副卡销售积分求和
            BigDecimal rechargePointSum = attacherCardPointDetails.stream()//充值积分
                    .filter(o2 -> cardNum.equals(o2.getCardNum()))
                    .map(AttacherCardPointDetails::getRechargePoint)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            //求积分销售的记录条数
            Long pointSum = 0l;
            for (Map.Entry<String, Long> m : map.entrySet()) {
                if (m.getKey().equals(cardNum)) {
                    pointSum = m.getValue();
                }
            }
            int i = o.getRechargeTimes() + pointSum.intValue();//总的销售记录条数

            o.setRechargePoint(rechargePointSum);
            o.setRechargeTimes(i);
            return o;

        }).collect(Collectors.toList());

        //根据副卡号去比对结果中查询公众号匹配上的,通过匹配上的公众号id去关联查询用户信息
        List<AttacherCardSaleComparisonDetails> attacherCardSaleComparisonDetails = attacherCardSaleComparisonDetailsService.getByAttacherCardNumList(attacherCardNumList);
        List<String> customerNums = attacherCardSaleComparisonDetails.stream()
                .filter(o -> "yes".equals(o.getIsComparison()))
                .map(s -> s.getCustomerNum()).collect(Collectors.toList());
        customerNums.add("nothing");
        List<CustomerInfo> customerInfos = customerInfoService.getByCustomerNum(customerNums);
        List<AttacherCardSaleDetailsSummaryDTO> returnData = null;
        if(!CollectionUtils.isEmpty(customerInfos)){
            returnData = data.stream().map(o -> {
                String cardNum = o.getCardNum();
                List<AttacherCardSaleComparisonDetails> collect = attacherCardSaleComparisonDetails.stream().filter(o1 -> cardNum.equals(o1.getCardNum())).collect(Collectors.toList());
                if (!CollectionUtils.isEmpty(collect)) {
                    AttacherCardSaleComparisonDetails attacherCardSaleComparisonDetails1 = collect.get(0);
                    String customerNum = attacherCardSaleComparisonDetails1.getCustomerNum();
                    List<CustomerInfo> collect1 = customerInfos.stream().filter(o2 -> customerNum.equals(o2.getCustomerNum())).collect(Collectors.toList());
                    if (!CollectionUtils.isEmpty(collect1)) {
                        CustomerInfo customerInfo = collect1.get(0);
                        o.setTelNum(customerInfo.getTelNum());//电话
                        o.setCardNum(customerInfo.getCarNum());//车牌号
                        o.setAttribute(customerInfo.getCarUse());//属性（用途）
                        return o;
                    } else {
                        return o;
                    }
                } else {
                    return o;
                }
            }).collect(Collectors.toList());
        }else{
            returnData = data;
        }

        //构建返回
        Page<AttacherCardSaleDetailsSummaryDTO> page1 = new Page<>();
        page1.setCurrent(page.getCurrent());
        page1.setSize(page.getSize());
        page1.setTotal(page.getTotal());
        page1.setRecords(returnData);

        return page1;
    }
}
