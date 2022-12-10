package com.example.redisOperations.dao;

public interface IHitCount {

    long getCount(String key);

    void putToLog(String key, String... values);
}
