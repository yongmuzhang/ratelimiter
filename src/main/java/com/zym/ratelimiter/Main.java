package com.zym.ratelimiter;

import com.zym.ratelimiter.model.Token;
import com.zym.ratelimiter.service.RateLimiterService;

/**
 * Created by zym on 17/1/26.
 */
public class Main {
    public static void main(String[] args) throws Exception {
        RateLimiterService rateLimiterService = RateLimiterService.getInstance();
//请求api之前, 先获取令牌
        Token token = rateLimiterService.pop();
        if (token != null) {
            System.out.println("成功请求API");
        } else {
            System.out.println("当前排队人数太多, 请稍后重试");
        }
    }
}
