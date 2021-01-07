package com.wonderful.service.excel.in;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.wonderful.bean.dto.AttacherCardOfficialAccountSaleDetailsImportDTO;
import com.wonderful.bean.entity.AttacherCardOfficialAccountSaleDetails;
import com.wonderful.service.AttacherCardOfficialAccountSaleDetailsService;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class AttacherCardOfficialAccountSaleDetailsListener extends AnalysisEventListener<AttacherCardOfficialAccountSaleDetailsImportDTO> {

    private AttacherCardOfficialAccountSaleDetailsService attacherCardOfficialAccountSaleDetailsService;

    /**
     * 每隔5条存储数据库，实际使用中可以3000条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 100;
    List<AttacherCardOfficialAccountSaleDetails> list = new ArrayList<AttacherCardOfficialAccountSaleDetails>();

    public AttacherCardOfficialAccountSaleDetailsListener(AttacherCardOfficialAccountSaleDetailsService attacherCardOfficialAccountSaleDetailsService) {
        this.attacherCardOfficialAccountSaleDetailsService= attacherCardOfficialAccountSaleDetailsService;
    }

    @Override
    public void invoke(AttacherCardOfficialAccountSaleDetailsImportDTO attacherCardOfficialAccountSaleDetailsImportDTO, AnalysisContext analysisContext) {

        AttacherCardOfficialAccountSaleDetails attacherCardOfficialAccountSaleDetails = BeanUtil.toBean(attacherCardOfficialAccountSaleDetailsImportDTO, AttacherCardOfficialAccountSaleDetails.class);

        attacherCardOfficialAccountSaleDetails.setIsComparison("no");
        attacherCardOfficialAccountSaleDetails.setCreateTime(LocalDateTime.now());
        attacherCardOfficialAccountSaleDetails.setUpdateTime(LocalDateTime.now());


        list.add(attacherCardOfficialAccountSaleDetails);


        //达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
        if (list.size() >= BATCH_COUNT) {
            attacherCardOfficialAccountSaleDetailsService.saveBatch(list);
            //存储完成清理list
            list.clear();
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        //这里也要保存数据，确保最后遗留的数据也存储到数据库
        attacherCardOfficialAccountSaleDetailsService.saveBatch(list);
    }
}
