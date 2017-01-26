package com.zym.ratelimiter.job;


import com.zym.ratelimiter.model.Token;
import com.zym.ratelimiter.service.RateLimiterService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * Created by zym on 17/1/26.
 */
public class TokenProducerJob implements Job {
    private static Logger _log = LoggerFactory.getLogger(TokenProducerJob.class);
    private RateLimiterService rateLimiterService = RateLimiterService.getInstance();
    public TokenProducerJob() {
    }

    public void execute(JobExecutionContext context) throws JobExecutionException {
        Token token = new Token((new Date().toString()));
        rateLimiterService.push(token);
    }
}
