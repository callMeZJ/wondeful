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

        //同步石化记录
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
                    .cardBuyer(cardBuyer)//抓取
                    .masterCardNum(masterCardNum)
                    .rechargeAmount(o.getDistributionAmount())//石化的分配金额-公众号的充值金额
                    .discount(discount)//抓取
                    .realPayAmount(null)//公众号的充值金额-公众号的付款金额
                    .payWay(null)//匹配上的就是公众号，匹配不上的下拉选
                    .payTime(null)//匹配上的就是公众号的生成时间
                    .serviceCharge(null)//选择付款方式后动态计算
                    .oils(oils)
                    .remarks("")
                    .isComparison("no")
                    .transactionTime(o.getTransactionTime())
                    .updateTime(LocalDateTime.now())
                    .createTime(LocalDateTime.now()).build();

            return build;

        }).collect(Collectors.toList());

        if(!attacherCardSaleComparisonDetailsService.saveAndUpdateOther(attacherCardSaleComparisonDetailsList)){
            return "石化全量记录同步失败";
        }

        //查询未比对的公众号记录
        List<AttacherCardOfficialAccountSaleDetails> list2 = attacherCardOfficialAccountSaleDetailsService.getIsNotComparison();
        if(CollectionUtils.isEmpty(list2)){
            return "暂无公众号记录需要比对";
        }
        //推入队列
        list2.forEach(o -> {
            rabbitTemplate.convertAndSend("wonderful.matching.q",o);
        });

        return "已推入队列，比对中... 请至石化-公众号明细比对中查看结果";

    }

    @RabbitListener(queues = "wonderful.matching.q")
    private void updateMatched(AttacherCardOfficialAccountSaleDetails attacherCardOfficialAccountSaleDetails){

        attacherCardSaleComparisonDetailsService.updateMatched(attacherCardOfficialAccountSaleDetails);
    }
}
