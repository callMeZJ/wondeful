package com.wonderful.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wonderful.bean.dto.AttacherCardSaleComparisonDetailsDTO;
import com.wonderful.bean.dto.AttacherCardSaleDetailsDTO;
import com.wonderful.bean.entity.*;
import com.wonderful.dao.AttacherCardOfficialAccountSaleDetailsMapper;
import com.wonderful.dao.AttacherCardSaleComparisonDetailsMapper;
import com.wonderful.dao.AttacherCardSaleDetailsMapper;
import com.wonderful.service.AttacherCardSaleComparisonDetailsService;
import com.wonderful.service.AttacherCardSaleDetailsService;
import com.wonderful.service.MatchTimeService;
import com.wonderful.service.PayServiceChargeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class AttacherCardSaleComparisonDetailsServiceImpl extends ServiceImpl<AttacherCardSaleComparisonDetailsMapper, AttacherCardSaleComparisonDetails> implements AttacherCardSaleComparisonDetailsService {

    @Autowired
    private AttacherCardOfficialAccountSaleDetailsMapper attacherCardOfficialAccountSaleDetailsMapper;
    @Autowired
    private AttacherCardSaleDetailsService attacherCardSaleDetailsService;
    @Autowired
    private PayServiceChargeService payServiceChargeService;
    @Autowired
    private MatchTimeService matchTimeService;

    @Override
    public IPage<AttacherCardSaleComparisonDetails> page(AttacherCardSaleComparisonDetailsDTO attacherCardSaleComparisonDetailsDTO) {

        LambdaQueryWrapper<AttacherCardSaleComparisonDetails> wrapper = new LambdaQueryWrapper<AttacherCardSaleComparisonDetails>();
        wrapper.orderByDesc(AttacherCardSaleComparisonDetails::getCreateTime);
        wrapper.like(!StringUtils.isEmpty(attacherCardSaleComparisonDetailsDTO.getCardNum()),AttacherCardSaleComparisonDetails::getCardNum,attacherCardSaleComparisonDetailsDTO.getCardNum());
        wrapper.like(!StringUtils.isEmpty(attacherCardSaleComparisonDetailsDTO.getMasterCardNum()),AttacherCardSaleComparisonDetails::getMasterCardNum,attacherCardSaleComparisonDetailsDTO.getMasterCardNum());

        Page page = new Page();
        page.setCurrent(attacherCardSaleComparisonDetailsDTO.getPage());
        page.setSize(attacherCardSaleComparisonDetailsDTO.getRows());

        IPage<AttacherCardSaleComparisonDetails> p = this.baseMapper.selectPage(page, wrapper);

        return p;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateMatched(AttacherCardOfficialAccountSaleDetails attacherCardOfficialAccountSaleDetails) {

        //查询匹配时间配置表
        LocalDateTime generationTime = attacherCardOfficialAccountSaleDetails.getGenerationTime();
        MatchTime matchTime = matchTimeService.findOnlyOne();
        Long timeLong = Objects.isNull(matchTime) ? 0l : matchTime.getTimeLength();
        LocalDateTime localDateTime = generationTime.plusSeconds(timeLong);

        LambdaQueryWrapper<AttacherCardSaleComparisonDetails> wrapper = new LambdaQueryWrapper<AttacherCardSaleComparisonDetails>();
        wrapper.eq(AttacherCardSaleComparisonDetails::getCardNum,attacherCardOfficialAccountSaleDetails.getOilCardNum());
        wrapper.eq(AttacherCardSaleComparisonDetails::getRechargeAmount,attacherCardOfficialAccountSaleDetails.getRechargeAmount());
        wrapper.eq(AttacherCardSaleComparisonDetails::getIsComparison,"no");
        wrapper.le(AttacherCardSaleComparisonDetails::getTransactionTime,localDateTime);

        List<AttacherCardSaleComparisonDetails> attacherCardSaleComparisonDetailsList = this.baseMapper.selectList(wrapper);

        if(!CollectionUtils.isEmpty(attacherCardSaleComparisonDetailsList)){
            //查询服务费
            PayServiceCharge payServiceCharge = payServiceChargeService.getServiceFeeByWay("公众号");
            BigDecimal serviceFee = Objects.nonNull(payServiceCharge) ? payServiceCharge.getServiceFee() : BigDecimal.ONE;

            //此处get(0)是因为按照业务上来说，只会有一条匹配上
            AttacherCardSaleComparisonDetails attacherCardSaleComparisonDetails = attacherCardSaleComparisonDetailsList.get(0);

            attacherCardSaleComparisonDetails.setIsComparison("yes");
            attacherCardSaleComparisonDetails.setRechargeAmount(attacherCardSaleComparisonDetails.getRechargeAmount().subtract(attacherCardOfficialAccountSaleDetails.getRechargeAmount()));
            attacherCardSaleComparisonDetails.setRealPayAmount(attacherCardOfficialAccountSaleDetails.getRechargeAmount().subtract(attacherCardOfficialAccountSaleDetails.getPayAmount()));
            attacherCardSaleComparisonDetails.setServiceCharge(attacherCardOfficialAccountSaleDetails.getPayAmount().multiply(serviceFee));
            attacherCardSaleComparisonDetails.setPayWay("公众号");
            attacherCardSaleComparisonDetails.setPayTime(attacherCardOfficialAccountSaleDetails.getGenerationTime());
            attacherCardSaleComparisonDetails.setComparisonAttacherCardOfficialAccountSaleDetailsId(attacherCardOfficialAccountSaleDetails.getId());
            //匹配上的把公众号销售记录中的用户编码存入，方便在石化销售明细汇总时抓取用户数据
            attacherCardSaleComparisonDetails.setCustomerNum(attacherCardOfficialAccountSaleDetails.getCustomerNum());
            this.updateById(attacherCardSaleComparisonDetails);

            attacherCardOfficialAccountSaleDetails.setIsComparison("yes");
            attacherCardOfficialAccountSaleDetailsMapper.updateById(attacherCardOfficialAccountSaleDetails);
        }

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveAndUpdateOther(List<AttacherCardSaleComparisonDetails> attacherCardSaleComparisonDetailsList) {

        if(CollectionUtils.isEmpty(attacherCardSaleComparisonDetailsList)){
            return true;
        }

        List<BigInteger> ids = attacherCardSaleComparisonDetailsList.stream().map(AttacherCardSaleComparisonDetails::getId).collect(Collectors.toList());
        List<AttacherCardSaleDetails> collect = ids.stream().map(o -> {
            AttacherCardSaleDetails yes = AttacherCardSaleDetails.builder()
                    .id(o)
                    .isSynchronise("yes")
                    .updateTime(LocalDateTime.now()).build();
            return yes;
        }).collect(Collectors.toList());


        boolean b = this.saveBatch(attacherCardSaleComparisonDetailsList);
        //批量修改
        boolean b1 = attacherCardSaleDetailsService.saveOrUpdateBatch(collect);

        return b&&b1;
    }

    @Override
    public List<AttacherCardSaleComparisonDetails> getByAttacherCardNumList(List<String> attacherCardNumList) {

        LambdaQueryWrapper<AttacherCardSaleComparisonDetails> wrapper = new LambdaQueryWrapper<AttacherCardSaleComparisonDetails>();
        wrapper.in(AttacherCardSaleComparisonDetails::getCardNum,attacherCardNumList);
        List<AttacherCardSaleComparisonDetails> attacherCardSaleComparisonDetails = this.baseMapper.selectList(wrapper);

        return attacherCardSaleComparisonDetails;
    }

}
