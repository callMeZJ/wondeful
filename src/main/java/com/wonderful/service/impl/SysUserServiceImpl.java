package com.wonderful.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wonderful.bean.dto.PayServiceChargeDTO;
import com.wonderful.bean.dto.SysUserDTO;
import com.wonderful.bean.entity.MasterCardOpenBillDetails;
import com.wonderful.bean.entity.PayServiceCharge;
import com.wonderful.bean.entity.SysUser;
import com.wonderful.dao.PayServiceChargeMapper;
import com.wonderful.dao.SysUserMapper;
import com.wonderful.service.PayServiceChargeService;
import com.wonderful.service.SysUserService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Base64;
import java.util.List;

@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Override
    public IPage<SysUser> page(SysUserDTO sysUserDTO) {

        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<SysUser>();
        wrapper.orderByDesc(SysUser::getCreateTime);
        wrapper.like(!StringUtils.isEmpty(sysUserDTO.getName()), SysUser::getName,sysUserDTO.getName());


        Page page = new Page();
        page.setCurrent(sysUserDTO.getPage());
        page.setSize(sysUserDTO.getRows());

        IPage<SysUser> p = this.baseMapper.selectPage(page, wrapper);

        return p;
    }

    @Override
    public boolean getByNameAndPwd(SysUserDTO sysUserDTO) {

        //明文进行编码
        byte[] bytes = sysUserDTO.getPwd().getBytes();
        String s = new String(Base64.getUrlEncoder().encode(bytes));

        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<SysUser>();
        wrapper.eq(SysUser::getName,sysUserDTO.getName());
        wrapper.eq(SysUser::getPwd,s);

        List<SysUser> sysUsers = this.baseMapper.selectList(wrapper);

        boolean f = CollectionUtils.isEmpty(sysUsers) ? false : true;

        return f;
    }

    @Override
    public SysUser getByName(String name) {

        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<SysUser>();
        wrapper.eq(SysUser::getName,name);

        SysUser sysUser= this.baseMapper.selectOne(wrapper);

        return sysUser;
    }

}
