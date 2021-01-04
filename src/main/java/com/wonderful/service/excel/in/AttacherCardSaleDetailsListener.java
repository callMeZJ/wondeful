package com.wonderful.service.excel.in;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.wonderful.bean.dto.AttacherCardSaleDetailsImportDTO;
import com.wonderful.bean.entity.AttacherCardSaleDetails;
import com.wonderful.service.AttacherCardSaleDetailsService;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class AttacherCardSaleDetailsListener extends AnalysisEventListener<AttacherCardSaleDetailsImportDTO> {

    
    private AttacherCardSaleDetailsService attacherCardSaleDetailsService;

    /**
     * 每隔5条存储数据库，实际使用中可以3000条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 100;
    List<AttacherCardSaleDetails> list = new ArrayList<AttacherCardSaleDetails>();

    public AttacherCardSaleDetailsListener(AttacherCardSaleDetailsService attacherCardSaleDetailsService) {
        this.attacherCardSaleDetailsService= attacherCardSaleDetailsService;
    }

    @Override
    public void invoke(AttacherCardSaleDetailsImportDTO attacherCardSaleDetailsImportDTO, AnalysisContext analysisContext) {

        AttacherCardSaleDetails attacherCardSaleDetails = BeanUtil.toBean(attacherCardSaleDetailsImportDTO, AttacherCardSaleDetails.class);
        attacherCardSaleDetails.setIsSynchronise("no");
        attacherCardSaleDetails.setCreateTime(LocalDateTime.now());
        attacherCardSaleDetails.setUpdateTime(LocalDateTime.now());

        list.add(attacherCardSaleDetails);
        //达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
        if (list.size() >= BATCH_COUNT) {
            attacherCardSaleDetailsService.saveBatch(list);
            //存储完成清理list
            list.clear();
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        //这里也要保存数据，确保最后遗留的数据也存储到数据库
        attacherCardSaleDetailsService.saveBatch(list);
    }
}
