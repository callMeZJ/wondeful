package com.wonderful.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wonderful.bean.dto.MatchTimeDTO;
import com.wonderful.bean.dto.SysUserDTO;
import com.wonderful.bean.entity.MatchTime;
import com.wonderful.bean.entity.SysUser;
import com.wonderful.service.MatchTimeService;
import com.wonderful.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/sysUser")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;

    @PostMapping("/page")
    public Map<String, Object> page(SysUserDTO sysUserDTO){

        Map<String, Object> map = new HashMap<>();

        IPage<SysUser> page = sysUserService.page(sysUserDTO);
        map.put("total",page.getTotal());
        map.put("rows",page.getRecords());

        return map;

    }

    @PostMapping("/save")
    public boolean save(SysUserDTO sysUserDTO){
        SysUser sysUser = BeanUtil.toBean(sysUserDTO, SysUser.class);


        byte[] bytes = sysUser.getPwd().getBytes();
        String s = new String(Base64.getUrlEncoder().encode(bytes));
        sysUser.setCreateTime(LocalDateTime.now());
        sysUser.setUpdateTime(LocalDateTime.now());
        sysUser.setPwd(s);

        if(Objects.nonNull(sysUserService.getByName(sysUser.getName()))){
            return false;
        }

        boolean save = sysUserService.save(sysUser);

        return save;

    }

    @PostMapping("/update")
    public boolean update(SysUserDTO sysUserDTO){
        SysUser sysUser = BeanUtil.toBean(sysUserDTO, SysUser.class);
        sysUser.setUpdateTime(LocalDateTime.now());

        boolean save = sysUserService.updateById(sysUser);

        return save;

    }

    @GetMapping("/delete")
    public boolean delete(@RequestParam(value = "id") int id){

        boolean save = sysUserService.removeById(id);

        return save;

    }

    @GetMapping("/find")
    public SysUser find(@RequestParam(value = "id") int id){

        SysUser sysUser = sysUserService.getById(id);

        return sysUser;

    }

    @PostMapping("/login")
    public boolean login(@RequestBody SysUserDTO sysUserDTO){

        boolean f = sysUserService.getByNameAndPwd(sysUserDTO);

        return f;

    }

}
