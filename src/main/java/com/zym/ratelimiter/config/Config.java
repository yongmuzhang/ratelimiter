package com.zym.ratelimiter.config;

/**
 * Created by zym on 17/1/25.
 */
public class Config {
    public static final String REDIS_HOST_ADDRESS = "127.0.0.1";
    public static final Integer REDIS_HOST_PORT = 6379;
    public static final String REDIS_HOST_PASSWORD = "";
    public static final Integer TOKEN_BUCKET_LENGTH = 10;
    public static final String TOKEN_BUCKET_NAME = "rate:limiter";
}
