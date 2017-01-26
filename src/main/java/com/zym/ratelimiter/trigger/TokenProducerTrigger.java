package com.zym.ratelimiter.trigger;

import com.zym.ratelimiter.job.TokenProducerJob;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.DateBuilder.nextGivenSecondDate;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * Created by zym on 17/1/26.
 */
public class TokenProducerTrigger {

    public void run() throws Exception {
        Logger log = LoggerFactory.getLogger(TokenProducerTrigger.class);

        log.info("------- Initializing ----------------------");

        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();

        log.info("------- Initialization Complete -----------");

        Date runTime = nextGivenSecondDate(new Date(), 10);

        log.info("------- Scheduling Job  -------------------");

        JobDetail tokenProducerJob = newJob(TokenProducerJob.class).withIdentity("tokenProducerJob", "rateLimiter").build();

        Trigger tokenProducerTrigger = newTrigger()
                .withIdentity("tokenProducerTrigger", "rateLimiter")
                .withSchedule(cronSchedule("0/20 * * * * ?"))
                .startAt(runTime).build();

        scheduler.scheduleJob(tokenProducerJob, tokenProducerTrigger);
        log.info(tokenProducerJob.getKey() + " will run at: " + runTime);

        scheduler.start();

        log.info("------- Started Scheduler -----------------");
    }

    public static void main(String[] args) throws Exception {

        TokenProducerTrigger tokenProducerTrigger = new TokenProducerTrigger();
        tokenProducerTrigger.run();

    }
}
