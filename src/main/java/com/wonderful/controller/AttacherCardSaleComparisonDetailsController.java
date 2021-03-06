package com.wonderful.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wonderful.bean.dto.AttacherCardSaleComparisonDetailsDTO;
import com.wonderful.bean.entity.AttacherCardOfficialAccountSaleDetails;
import com.wonderful.bean.entity.AttacherCardSaleComparisonDetails;
import com.wonderful.bean.entity.AttacherCardSaleDetails;
import com.wonderful.bean.entity.MasterAttacherCardRelationship;
import com.wonderful.service.AttacherCardOfficialAccountSaleDetailsService;
import com.wonderful.service.AttacherCardSaleComparisonDetailsService;
import com.wonderful.service.AttacherCardSaleDetailsService;
import com.wonderful.service.MasterAttacherCardRelationshipService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/attacherCardSaleComparisonDetails")
public class AttacherCardSaleComparisonDetailsController {

    @Autowired
    private AttacherCardSaleComparisonDetailsService attacherCardSaleComparisonDetailsService;
    @Autowired
    private AttacherCardSaleDetailsService attacherCardSaleDetailsService;
    @Autowired
    private MasterAttacherCardRelationshipService masterAttacherCardRelationshipService;
    @Autowired
    private AttacherCardOfficialAccountSaleDetailsService attacherCardOfficialAccountSaleDetailsService;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostMapping("/page")
    public Map<String, Object> page(AttacherCardSaleComparisonDetailsDTO attacherCardSaleComparisonDetailsDTO){

        Map<String, Object> map = new HashMap<>();

        IPage<AttacherCardSaleComparisonDetails> page = attacherCardSaleComparisonDetailsService.page(attacherCardSaleComparisonDetailsDTO);
        map.put("total",page.getTotal());
        map.put("rows",page.getRecords());

        return map;

    }

    @PostMapping("/save")
    public boolean save(AttacherCardSaleComparisonDetailsDTO attacherCardSaleComparisonDetailsDTO){
        AttacherCardSaleComparisonDetails attacherCardSaleComparisonDetails = BeanUtil.toBean(attacherCardSaleComparisonDetailsDTO, AttacherCardSaleComparisonDetails.class);
        attacherCardSaleComparisonDetails.setCreateTime(LocalDateTime.now());
        attacherCardSaleComparisonDetails.setUpdateTime(LocalDateTime.now());
        attacherCardSaleComparisonDetails.setIsComparison("no");

        boolean save = attacherCardSaleComparisonDetailsService.save(attacherCardSaleComparisonDetails);

        return save;

    }

    @PostMapping("/update")
    public boolean update(AttacherCardSaleComparisonDetailsDTO attacherCardSaleComparisonDetailsDTO){
        AttacherCardSaleComparisonDetails attacherCardSaleComparisonDetails = BeanUtil.toBean(attacherCardSaleComparisonDetailsDTO, AttacherCardSaleComparisonDetails.class);
        attacherCardSaleComparisonDetails.setUpdateTime(LocalDateTime.now());

        boolean save = attacherCardSaleComparisonDetailsService.updateById(attacherCardSaleComparisonDetails);

        return save;

    }

    @GetMapping("/delete")
    public boolean delete(@RequestParam(value = "id") int id){

        boolean save = attacherCardSaleComparisonDetailsService.removeById(id);

        return save;

    }

    @GetMapping("/find")
    public AttacherCardSaleComparisonDetails find(@RequestParam(value = "id") int id){

        AttacherCardSaleComparisonDetails attacherCardSaleComparisonDetails = attacherCardSaleComparisonDetailsService.getById(id);

        return attacherCardSaleComparisonDetails;

    }

    @GetMapping("/comparison")
    public String comparison(){

        //??????????????????
        List<AttacherCardSaleDetails> list = attacherCardSaleDetailsService.getIsNotSynchronise();

        List<String> cardNumList = list.stream().map(AttacherCardSaleDetails::getCardNum).collect(Collectors.toList());
        cardNumList.add("-");
        List<MasterAttacherCardRelationship> masterAttacherCardRelationshipList = masterAttacherCardRelationshipService.getByAttacherCardNumList(cardNumList);

        List<AttacherCardSaleComparisonDetails> attacherCardSaleComparisonDetailsList = list.stream().map(o -> {
            List<MasterAttacherCardRelationship> collect = masterAttacherCardRelationshipList.stream().filter(o1 -> o1.getAttacherCardNum().equals(o.getCardNum())).collect(Collectors.toList());

            String masterCardNum = !CollectionUtils.isEmpty(collect) && !StringUtils.isEmpty(collect.get(0).getMasterCardNum()) ? collect.get(0).getMasterCardNum() : "";
            String oils = !CollectionUtils.isEmpty(collect) && !StringUtils.isEmpty(collect.get(0).getOils()) ? collect.get(0).getOils() : "";
            String cardBuyer = !CollectionUtils.isEmpty(collect) && !StringUtils.isEmpty(collect.get(0).getCardBuyer()) ? collect.get(0).getCardBuyer() : "";
            BigDecimal discount = !CollectionUtils.isEmpty(collect) && !StringUtils.isEmpty(collect.get(0).getDiscount()) ? collect.get(0).getDiscount() : null;

            AttacherCardSaleComparisonDetails build = AttacherCardSaleComparisonDetails.builder()
                    .id(o.getId())
                    .date(o.getTransactionTime().toLocalDate())
                    .cardNum(o.getCardNum())
                    .cardBuyer(cardBuyer)//??????
                    .masterCardNum(masterCardNum)
                    .rechargeAmount(o.getDistributionAmount())//?????????????????????-????????????????????????
                    .discount(discount)//??????
                    .realPayAmount(null)//????????????????????????-????????????????????????
                    .payWay(null)//??????????????????????????????????????????????????????
                    .payTime(null)//??????????????????????????????????????????
                    .serviceCharge(null)//?????????????????????????????????
                    .oils(oils)
                    .remarks("")
                    .isComparison("no")
                    .transactionTime(o.getTransactionTime())
                    .updateTime(LocalDateTime.now())
                    .createTime(LocalDateTime.now()).build();

            return build;

        }).collect(Collectors.toList());

        if(!attacherCardSaleComparisonDetailsService.saveAndUpdateOther(attacherCardSaleComparisonDetailsList)){
            return "??????????????????????????????";
        }

        //?????????????????????????????????
        List<AttacherCardOfficialAccountSaleDetails> list2 = attacherCardOfficialAccountSaleDetailsService.getIsNotComparison();
        if(CollectionUtils.isEmpty(list2)){
            return "?????????????????????????????????";
        }
        //????????????
        list2.forEach(o -> {
            rabbitTemplate.convertAndSend("wonderful.matching.q",o);
        });

        return "???????????????????????????... ????????????-????????????????????????????????????";

    }

    @RabbitListener(queues = "wonderful.matching.q")
    private void updateMatched(AttacherCardOfficialAccountSaleDetails attacherCardOfficialAccountSaleDetails){

        attacherCardSaleComparisonDetailsService.updateMatched(attacherCardOfficialAccountSaleDetails);
    }
}
