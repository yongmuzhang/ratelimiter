package com.zym.ratelimiter.service;


import com.zym.ratelimiter.config.Config;
import com.zym.ratelimiter.model.Token;
import com.zym.ratelimiter.model.TokenBucket;
import redis.clients.jedis.Jedis;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by zym on 17/1/24.
 */
public class RateLimiterService {
    private static RateLimiterService rateLimiterService;
    private static TokenBucket tokenBucket;
    private static ReentrantLock lock = new ReentrantLock();
    static {
        Jedis jedis = new Jedis(Config.REDIS_HOST_ADDRESS, Config.REDIS_HOST_PORT);
//        jedis.auth(Config.REDIS_HOST_PASSWORD);
        TokenBucket tokenBucket = new TokenBucket(Config.TOKEN_BUCKET_NAME, Config.TOKEN_BUCKET_LENGTH, jedis);
        rateLimiterService = new RateLimiterService(tokenBucket);
    }
    private RateLimiterService(TokenBucket tokenBucket) {
        this.tokenBucket = tokenBucket;
    }
    public static RateLimiterService getInstance(){
        return rateLimiterService;
    }

    public Token pop(){
        lock.lock();
        Token token = null;
        try {
            token = this.tokenBucket.pop();
        } finally {
            lock.unlock();
        }
        return token;
    }

    public Integer push(Token token){
        lock.lock();
        Integer num = 0;
        try {
            num = this.tokenBucket.push(token);
        } finally {
            lock.unlock();
        }
        return num;
    }

}
