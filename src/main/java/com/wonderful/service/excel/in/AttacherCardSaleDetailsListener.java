package com.wonderful.service.excel.in;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.wonderful.bean.entity.AttacherCardSaleDetails;
import com.wonderful.service.AttacherCardSaleDetailsService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class AttacherCardSaleDetailsListener extends AnalysisEventListener<AttacherCardSaleDetails> {

    @Autowired
    private AttacherCardSaleDetailsService attacherCardSaleDetailsService;

    /**
     * 每隔5条存储数据库，实际使用中可以3000条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 5;
    List<AttacherCardSaleDetails> list = new ArrayList<AttacherCardSaleDetails>();

    @Override
    public void invoke(AttacherCardSaleDetails attacherCardSaleDetails, AnalysisContext analysisContext) {
        System.out.println("invoke方法被调用");
        list.add(attacherCardSaleDetails);
        // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
        if (list.size() >= BATCH_COUNT) {
            attacherCardSaleDetailsService.saveBatch(list);
            // 存储完成清理 list
            list.clear();
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        System.out.println("doAfterAllAnalysed方法 被调用");
        // 这里也要保存数据，确保最后遗留的数据也存储到数据库
        attacherCardSaleDetailsService.saveBatch(list);
    }
}
