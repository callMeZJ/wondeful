package com.wonderful.service.impl;

import com.wonderful.service.UserCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class UserCacheServiceImpl implements UserCacheService {

    private final static Map<String, String> map = new HashMap<>();

    @Autowired
    private CacheManager cacheManager;

    @Override
    @Cacheable(cacheNames = "user")
    public String get(String key) {

        //System.err.println("从map里面取,The cacheManager is" + cacheManager);

        return map.get(key);
    }

    @Override
    @CacheEvict(cacheNames = "user")
    public void delete(String key) {

        map.remove(key);
    }

    @Override
    public void save(String key,String value) {

        map.put(key,value);
    }

    @Override
    @CachePut(cacheNames = "user",key = "#key")
    public void update(String key,String value) {

        map.put(key,value);
    }

    @Override
    public ConcurrentHashMap getAll() {

        Cache user = cacheManager.getCache("user");
        ConcurrentHashMap cacheMap = (ConcurrentHashMap) user.getNativeCache();
        return cacheMap;
    }
}
