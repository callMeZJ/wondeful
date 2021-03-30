package com.wonderful.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wonderful.bean.dto.CustomerInfoDTO;
import com.wonderful.bean.entity.CustomerInfo;
import com.wonderful.service.CustomerInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/customerInfo")
public class CustomerInfoController {

    @Autowired
    private CustomerInfoService customerInfoService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {

        //转换日期
        DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        binder.registerCustomEditor(Timestamp.class, new CustomDateEditor(dateFormat, true));
    }

    @PostMapping("/page")
    public Map<String, Object> page(CustomerInfoDTO customerInfoDTO){

        Map<String, Object> map = new HashMap<>();

        IPage<CustomerInfo> page = customerInfoService.page(customerInfoDTO);
        map.put("total",page.getTotal());
        map.put("rows",page.getRecords());

        return map;

    }

    @PostMapping("/save")
    public boolean save(CustomerInfoDTO customerInfoDTO){
        CustomerInfo customerInfo = BeanUtil.toBean(customerInfoDTO, CustomerInfo.class);
        customerInfo.setCreateTime(LocalDateTime.now());
        customerInfo.setUpdateTime(LocalDateTime.now());

        boolean save = customerInfoService.save(customerInfo);

        return save;

    }

    @PostMapping("/update")
    public boolean update(CustomerInfoDTO customerInfoDTO){
        CustomerInfo customerInfo = BeanUtil.toBean(customerInfoDTO, CustomerInfo.class);
        customerInfo.setUpdateTime(LocalDateTime.now());

        boolean save = customerInfoService.updateById(customerInfo);

        return save;

    }

    @GetMapping("/delete")
    public boolean delete(@RequestParam(value = "id") int id){

        boolean save = customerInfoService.removeById(id);

        return save;

    }

    @GetMapping("/find")
    public CustomerInfo find(@RequestParam(value = "id") int id){

        CustomerInfo customerInfo = customerInfoService.getById(id);

        return customerInfo;

    }
}
