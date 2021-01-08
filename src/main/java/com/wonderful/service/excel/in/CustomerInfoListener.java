package com.wonderful.service.excel.in;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.wonderful.bean.dto.AttacherCardSaleDetailsImportDTO;
import com.wonderful.bean.dto.CustomerInfoImportDTO;
import com.wonderful.bean.entity.AttacherCardSaleDetails;
import com.wonderful.bean.entity.CustomerInfo;
import com.wonderful.service.AttacherCardSaleDetailsService;
import com.wonderful.service.CustomerInfoService;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class CustomerInfoListener extends AnalysisEventListener<CustomerInfoImportDTO> {


    private CustomerInfoService customerInfoService;

    /**
     * 每隔5条存储数据库，实际使用中可以3000条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 100;
    List<CustomerInfo> list = new ArrayList<CustomerInfo>();

    public CustomerInfoListener(CustomerInfoService customerInfoService) {
        this.customerInfoService= customerInfoService;
    }

    @Override
    public void invoke(CustomerInfoImportDTO customerInfoImportDTO, AnalysisContext analysisContext) {

        CustomerInfo customerInfo = BeanUtil.toBean(customerInfoImportDTO, CustomerInfo.class);
        customerInfo.setCreateTime(LocalDateTime.now());
        customerInfo.setUpdateTime(LocalDateTime.now());

        list.add(customerInfo);
        //达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
        if (list.size() >= BATCH_COUNT) {
            customerInfoService.saveBatch(list);
            //存储完成清理list
            list.clear();
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        //这里也要保存数据，确保最后遗留的数据也存储到数据库
        customerInfoService.saveBatch(list);
    }
}
