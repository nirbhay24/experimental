package com.example.redisOperations.dao;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HyperLogLogOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class HitCountImpl implements IHitCount {

    private HyperLogLogOperations hyperLogLogOps;

    @Autowired
    public  HitCountImpl(RedisTemplate redisTemplate) {
        this.hyperLogLogOps = redisTemplate.opsForHyperLogLog();
    }


    @Override
    public long getCount(String key) {
        return hyperLogLogOps.size(key);
    }

    @Override
    public void putToLog(String key, String... values) {
        hyperLogLogOps.add(key,values);
    }
}
