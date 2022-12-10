package com.zomatrix.rateLimiter.controller;

import com.zomatrix.rateLimiter.rateLimierAlgo.RateLimiter;
import com.zomatrix.rateLimiter.rateLimierAlgo.impl.TokenBucketRateLimiter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class RestApiController {


    // we can put rate limiter based on per user based
    RateLimiter rateLimiter;

    public RestApiController() {
        //       rateLimiter = new SlidingWindowRateLimiter(60 , 20);
        //       rateLimiter = new LeakyBucketRateLimiter(10,5);
        rateLimiter = new TokenBucketRateLimiter(10, 5, 2);
    }


    @GetMapping(value = "/rateLimitedApi")
    public ResponseEntity<String> rateLimitedApi() {
        if (rateLimiter.grantAccess()) {
            return ResponseEntity.ok("Congratulation if you got response,This is Rate limited Api");
        }
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
    }

}