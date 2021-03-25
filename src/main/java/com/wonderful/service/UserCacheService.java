package com.wonderful.service;

import java.util.concurrent.ConcurrentHashMap;

public interface UserCacheService {

    String get(String key);

    void delete(String key);

    void save(String key,String value);

    void update(String key,String value);

    ConcurrentHashMap getAll();
}
