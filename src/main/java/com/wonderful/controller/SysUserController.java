package com.wonderful.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wonderful.bean.dto.MatchTimeDTO;
import com.wonderful.bean.dto.SysUserDTO;
import com.wonderful.bean.entity.MatchTime;
import com.wonderful.bean.entity.SysUser;
import com.wonderful.interceptor.UserInterceptor;
import com.wonderful.service.MatchTimeService;
import com.wonderful.service.SysUserService;
import com.wonderful.service.UserCacheService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.net.InetAddress;
import java.net.UnknownHostException;
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
    @Autowired
    private UserCacheService userCacheService;
    @Autowired
    private UserInterceptor userInterceptor;
    @Autowired
    private RabbitTemplate rabbitTemplate;

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
    public boolean login(@RequestBody SysUserDTO sysUserDTO) throws UnknownHostException {

        boolean f = sysUserService.getByNameAndPwd(sysUserDTO);

        if(f){

            userCacheService.save(sysUserDTO.getName(),sysUserDTO.getName());
            //刷入缓存
            userCacheService.get(sysUserDTO.getName());
            userInterceptor.flagSet.add(sysUserDTO.getName());
            //获取本机名称并且记录
            String hostName = InetAddress.getLocalHost().getHostName();
            userInterceptor.hostNameSet.add(hostName);
            //推入清除缓存的延时队列
            rabbitTemplate.convertAndSend("delay.clear.cache.e", "delay_clear_cache_routing_key", sysUserDTO.getName(), message -> {
                //1个小时
                Long millisecond = 3600000l;
                message.getMessageProperties().setExpiration(millisecond.toString());
                return message;
            });
        }

        return f;

    }

    @GetMapping("/logout")
    public void loginout(@RequestParam String key) throws UnknownHostException {

        String hostName = InetAddress.getLocalHost().getHostName();
        userInterceptor.hostNameSet.remove(hostName);

        userCacheService.delete(key);
        userInterceptor.flagSet.remove(key);
    }

    //清除缓存
    @RabbitListener(queues = "dead.letter.clear.cache.q")
    public void clearCache(String key){

        String hostName = null;
        try {
            hostName = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            System.err.println("rabbitmq清除机器名报错");
        }
        userInterceptor.hostNameSet.remove(hostName);

        userCacheService.delete(key);
        userInterceptor.flagSet.remove(key);
    }



}
