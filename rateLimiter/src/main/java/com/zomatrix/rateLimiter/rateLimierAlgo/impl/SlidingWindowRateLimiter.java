package com.zomatrix.rateLimiter.rateLimierAlgo.impl;

import com.zomatrix.rateLimiter.rateLimierAlgo.RateLimiter;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class SlidingWindowRateLimiter implements RateLimiter {
    Queue<Long>  slidingWindow;
    int timeWindowInSeconds;
    int bucketCapacity;

    public SlidingWindowRateLimiter( int timeWindowInSeconds, int bucketCapacity) {
        this.timeWindowInSeconds = timeWindowInSeconds;
        this.bucketCapacity = bucketCapacity;
        this.slidingWindow = new ConcurrentLinkedQueue<>();
    }
    
    public int getTimeWindowInSeconds() {
        return timeWindowInSeconds;
    }

    public void setTimeWindowInSeconds(int timeWindowInSeconds) {
        this.timeWindowInSeconds = timeWindowInSeconds;
    }

    public int getBucketCapacity() {
        return bucketCapacity;
    }

    public void setBucketCapacity(int bucketCapacity) {
        this.bucketCapacity = bucketCapacity;
    }

    @Override
    public synchronized boolean grantAccess() {
        long currentTime = System.currentTimeMillis();
        checkAndUpdateQueue(currentTime);
        if(slidingWindow.size() < bucketCapacity)
        {
            slidingWindow.offer(currentTime);
            return true;
        }
        return false;
    }

    private void checkAndUpdateQueue(long currentTime) {
        if(slidingWindow.isEmpty()) return;
        long calculatedTime = (currentTime - slidingWindow.peek())/1000;
        while (calculatedTime >= timeWindowInSeconds) {
            slidingWindow.poll();
            if(slidingWindow.isEmpty()) break;
            calculatedTime = (currentTime - slidingWindow.peek()) / 1000;
        }
    }
}
