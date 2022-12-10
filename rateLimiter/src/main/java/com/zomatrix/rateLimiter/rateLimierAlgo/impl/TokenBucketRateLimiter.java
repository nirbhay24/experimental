package com.zomatrix.rateLimiter.rateLimierAlgo.impl;

import com.zomatrix.rateLimiter.rateLimierAlgo.RateLimiter;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class TokenBucketRateLimiter implements RateLimiter {

    private int bucketCapacity;
    private int refreshRate;

    private int tokenToRefresh;

    private AtomicInteger currentCapacity;
    private AtomicLong lastUpdateTime;

    /**
     *
     * @param bucketCapacity  Maximum token
     * @param refreshRate seconds after which Tokens are refreshed
     * @param tokenToRefresh number of tokens refreshed
     */
    public TokenBucketRateLimiter(int bucketCapacity, int refreshRate, int tokenToRefresh) {
        this.bucketCapacity = bucketCapacity;
        this.refreshRate = refreshRate;
        this.tokenToRefresh = (tokenToRefresh <= bucketCapacity)? tokenToRefresh : bucketCapacity;
        currentCapacity = new AtomicInteger(bucketCapacity);
        lastUpdateTime = new AtomicLong(System.currentTimeMillis());
    }

    @Override
    public boolean grantAccess() {
      refreshToken();
        if (currentCapacity.get() > 0) {
            currentCapacity.decrementAndGet();
            return true;
        }
        return false;
    }

    private void refreshToken() {
        long currentTime = System.currentTimeMillis();
        long timeSinceLastAccess = (currentTime - lastUpdateTime.get())/1000;
        System.out.println("Sec since Last access : "+ timeSinceLastAccess);
        System.out.println("Current Capacity : "+currentCapacity.get());
        if( timeSinceLastAccess >= refreshRate){
            int tokensToRefresh = (int) ((timeSinceLastAccess / refreshRate) * tokenToRefresh);
            int additionalTokensToAdd = Math.min (bucketCapacity , currentCapacity.get() + tokensToRefresh);
            System.out.println("additionalTokensToAdd : "+additionalTokensToAdd);
            currentCapacity.getAndSet(additionalTokensToAdd);
            lastUpdateTime.getAndSet(currentTime);
        }
    }
}
