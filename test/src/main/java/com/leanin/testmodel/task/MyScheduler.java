package com.leanin.testmodel.task;

import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

@Component
public class MyScheduler {

    @Autowired
    SchedulerFactoryBean schedulerFactoryBean;

    static Scheduler scheduler;

    public void scheduleJobs() throws SchedulerException {
        //从工厂类中 获取Scheduler 对象
        scheduler = schedulerFactoryBean.getScheduler();
        startJob1(); // 每5秒钟执行一次
        startJob2(); // 每2秒钟执行一次
    }

    /**
     * 开启任务
     * @throws SchedulerException
     */
    public static void startJob1() throws SchedulerException {
        //创建一个任务
        JobDetail jobDetail = JobBuilder.newJob(ScheduledJob.class).withIdentity("job1", "group1").build();
        //设置cron表达式
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule("0/6 * * * * ?");
        CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity("trigger1", "group1")
                .withSchedule(scheduleBuilder).build();
        scheduler.scheduleJob(jobDetail, cronTrigger);
    }

    /**
     * 动态修改cron表达式
     * @param cron
     * @throws SchedulerException
     */
    public static void modifyJob1(String cron) throws SchedulerException {
        TriggerKey triggerKey = TriggerKey.triggerKey("trigger1", "group1");
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cron);
        CronTrigger newTrigger = TriggerBuilder.newTrigger().withIdentity("trigger1", "group1")
                .withSchedule(scheduleBuilder).build();
        scheduler.rescheduleJob(triggerKey, newTrigger);
    }

    //获取任务状态  NORMAL正常  PAUSED 暂停
    public static String getJob1Status() throws SchedulerException {
        TriggerKey triggerKey = TriggerKey.triggerKey("trigger1", "group1");
        return scheduler.getTriggerState(triggerKey).name();
    }
    //暂停任务
    public static void pauseJob1() throws SchedulerException {
        scheduler.pauseJob(JobKey.jobKey("job1", "group1"));
    }
    //恢复任务
    public static void resumeJob1() throws SchedulerException {
        scheduler.resumeJob(JobKey.jobKey("job1", "group1"));
    }

    private void startJob2() throws SchedulerException {
        JobDetail jobDetail = JobBuilder.newJob(ScheduledJob2.class).withIdentity("job2", "group1").build();
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule("0/2 * * * * ?");
        CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity("trigger2", "group1")
                .withSchedule(scheduleBuilder).build();
        scheduler.scheduleJob(jobDetail, cronTrigger);
    }
    public static void modifyJob2(String cron) throws SchedulerException {
        TriggerKey triggerKey = TriggerKey.triggerKey("trigger2", "group1");
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cron);
        CronTrigger newTrigger = TriggerBuilder.newTrigger().withIdentity("trigger2", "group1")
                .withSchedule(scheduleBuilder).build();
        scheduler.rescheduleJob(triggerKey, newTrigger);
    }
    public static String getJob2Status() throws SchedulerException {
        TriggerKey triggerKey = TriggerKey.triggerKey("trigger2", "group1");
        return scheduler.getTriggerState(triggerKey).name();
    }
    public static void pauseJob2() throws SchedulerException {
        scheduler.pauseJob(JobKey.jobKey("job2", "group1"));
    }
    public static void resumeJob2() throws SchedulerException {
        scheduler.resumeJob(JobKey.jobKey("job2", "group1"));
    }
}
