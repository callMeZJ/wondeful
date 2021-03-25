package com.wonderful.controller;

import com.wonderful.service.UserCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ConcurrentHashMap;

@RestController
//此接口不开放
@RequestMapping("/cache")
public class UserCacheController {

    @Autowired
    private UserCacheService userCacheService;

    @GetMapping("/get")
    public String get(@RequestParam String key) {
        return userCacheService.get(key);
    }
    @DeleteMapping("/delete")
    public void delete(@RequestParam String key) {

        userCacheService.delete(key);
    }

    @GetMapping("/save")
    public void save(@RequestParam String key, @RequestParam String value) {

        userCacheService.save(key,value);
    }

    @GetMapping("/update")
    public void update(@RequestParam String key, @RequestParam String value) {

        userCacheService.update(key,value);
    }

    @GetMapping("/getAll")
    public ConcurrentHashMap getAll() {

        return userCacheService.getAll();
    }
}
