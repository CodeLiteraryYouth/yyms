package com.leanin.schdule.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.leanin.domain.vo.ScheduleJobVo;
import com.leanin.schdule.service.ScheduleService;
//import org.quartz.CronScheduleBuilder;
//import org.quartz.CronTrigger;
//import org.quartz.Job;
//import org.quartz.JobBuilder;
//import org.quartz.JobDetail;
//import org.quartz.JobKey;
//import org.quartz.Scheduler;
//import org.quartz.TriggerBuilder;
//import org.quartz.TriggerKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

/**
 * 调度工厂类
 *
 */
//@Configuration
//@EnableScheduling
//@Component
public class ScheduleFactory {

    /*private static Logger logger = LoggerFactory.getLogger(ScheduleFactory.class);

    private Map<String, String> jobUniqueMap = new HashMap<String, String>(); // 当前Trigger使用的

    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() {
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        return schedulerFactoryBean;
    }*/

//    public SchedulerFactoryBean getSchedulerFactoryBean() {
//        return schedulerFactoryBean;
//    }
//
//    public void setSchedulerFactoryBean(SchedulerFactoryBean schedulerFactoryBean) {
//        this.schedulerFactoryBean = schedulerFactoryBean;
//    }

    /*@Autowired
    private ScheduleService scheduleService;

    public ScheduleService getScheduleService() {
        return scheduleService;
    }

    public void setScheduleService(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }*/

    /**
     * 每隔5s查库，并根据查询结果决定是否重新设置定时任务
     * @throws Exception  360000
     */
//    @Scheduled(fixedRate = 50000)
   /* public void scheduleUpdateCronTrigger() throws Exception {

        try {
            // schedulerFactoryBean 由spring创建注入
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            List<ScheduleJobVo> jobList = scheduleService.findLegalJobList();

            // 获取最新删除(禁用)任务列表，将其从调度器中删除，并且从jobUniqueMap中删除
            List<ScheduleJobVo> jobDelList = scheduleService.findDelJobList();
            for (ScheduleJobVo delJob : jobDelList) {
                JobKey jobKey = JobKey.jobKey(delJob.getJobName(), delJob.getJobGroup());
                scheduler.deleteJob(jobKey);
                jobUniqueMap.remove(TriggerKey.triggerKey(delJob.getJobName(), delJob.getJobGroup()));
            }

            for (ScheduleJobVo job : jobList) {

                TriggerKey triggerKey = TriggerKey.triggerKey(job.getJobName(), job.getJobGroup());
                // 获取trigger，即在spring配置文件中定义的 bean id="myTrigger"
                CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);

                String dbCron = job.getCronExpression(); // 该job数据库中的Trigger表达式
                // 不存在，创建一个
                if (null == trigger) {
                    try{
                        @SuppressWarnings("unchecked")
                        Class <? extends Job> clazz = (Class<? extends Job>) Class.forName(job.getQuartzClass());
                        JobDetail jobDetail = JobBuilder.newJob(clazz).withIdentity(job.getJobName(), job.getJobGroup()).build();
                        jobDetail.getJobDataMap().put("ScheduleJobVo", job);
                        // 表达式调度构建器
                        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());
                        // 按新的cronExpression表达式构建一个新的trigger
                        trigger = TriggerBuilder.newTrigger().withIdentity(job.getJobName(), job.getJobGroup()).withSchedule(scheduleBuilder).build();
                        jobUniqueMap.put(triggerKey.toString(), trigger.getCronExpression());

                        scheduler.scheduleJob(jobDetail, trigger);
                    } catch(Exception e){
                        e.printStackTrace();
                        logger.error(e.getMessage());
                    }

                } else if(!jobUniqueMap.get(triggerKey.toString()).equals(dbCron)){
                    // Trigger已存在，那么更新相应的定时设置
                    // 表达式调度构建器
                    CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(dbCron);
                    // 按新的cronExpression表达式重新构建trigger

                    trigger = (CronTrigger) scheduler.getTrigger(triggerKey);

                    trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
                    // 按新的trigger重新设置job执行
                    scheduler.rescheduleJob(triggerKey, trigger);

                    jobUniqueMap.put(triggerKey.toString(), dbCron);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
}
