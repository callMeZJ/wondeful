package com.wonderful.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wonderful.bean.dto.MatchTimeDTO;
import com.wonderful.bean.dto.SysUserDTO;
import com.wonderful.bean.entity.MatchTime;
import com.wonderful.bean.entity.SysUser;

public interface SysUserService extends IService<SysUser> {

    IPage<SysUser> page(SysUserDTO sysUserDTO);

    boolean getByNameAndPwd(SysUserDTO sysUserDTO);

    SysUser getByName(String name);
}
