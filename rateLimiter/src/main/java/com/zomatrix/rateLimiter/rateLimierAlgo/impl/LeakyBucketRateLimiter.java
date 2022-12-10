package com.zomatrix.rateLimiter.rateLimierAlgo.impl;

import com.zomatrix.rateLimiter.rateLimierAlgo.RateLimiter;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class LeakyBucketRateLimiter implements RateLimiter {

    BlockingQueue<Integer> queue;

    int capacity;

    volatile long lastRequestTime;
    private int leakTime;

    public LeakyBucketRateLimiter(int capacity, int leakTime) {
        this.capacity = capacity;
        this.leakTime = leakTime;
        queue = new LinkedBlockingQueue<>(capacity);
        lastRequestTime = System.currentTimeMillis();
    }


    @Override
    public synchronized boolean grantAccess() {
        long currentTime = System.currentTimeMillis();
        leakFromBucket(currentTime);
        if (queue.remainingCapacity() > 0) {
            lastRequestTime = currentTime;
            queue.add(1);
            return true;
        }
        return false;
    }

    private void leakFromBucket(long currentTime) {
        long timePassedFromLastRequest = (currentTime - lastRequestTime) / 1000;
        System.out.println(Thread.currentThread() + " timePassedFromLastRequest : "+ timePassedFromLastRequest );
        System.out.println(Thread.currentThread() + " QueueSize : "+ queue.size() );
        if (timePassedFromLastRequest >= leakTime) {
            long requestsToLeak = timePassedFromLastRequest / leakTime;
            requestsToLeak = requestsToLeak * 2;
            System.out.println(Thread.currentThread() + " requestsToLeak : "+ requestsToLeak );
            if (requestsToLeak >= capacity)
                queue.clear();
            else {
                while (requestsToLeak > 0) {
                    if (queue.isEmpty()) return;
                    queue.remove();
                    requestsToLeak--;
                }
            }
        }

    }
}
