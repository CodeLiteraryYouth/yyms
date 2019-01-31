package com.leanin.utils;

import com.leanin.domain.dto.TaskCronDto;
import lombok.extern.slf4j.Slf4j;

/**
 * Cron表达式生成工具类
 * @author Administrator
 */
@Slf4j
public class CronUtil {
    /**
     *
     *方法摘要：构建Cron表达式
     *@param  taskScheduleModel
     *@return String
     */
    public static String createCronExpression(TaskCronDto taskScheduleModel){
        StringBuffer cronExp = new StringBuffer("");

        if(null == taskScheduleModel.getJobType()) {
            log.info("执行周期未配置" );//执行周期未配置
        }

        if (null != taskScheduleModel.getSecond()
                && null != taskScheduleModel.getMinute()
                && null != taskScheduleModel.getHour()) {
            //秒
            cronExp.append(taskScheduleModel.getSecond()).append(" ");
            //分
            cronExp.append(taskScheduleModel.getMinute()).append(" ");
            //小时
            cronExp.append(taskScheduleModel.getHour()).append(" ");

            //每天
            if(taskScheduleModel.getJobType().intValue() == 1){
                cronExp.append("* ");//日
                cronExp.append("* ");//月
                cronExp.append("?");//周
            }

            //按每周
            else if(taskScheduleModel.getJobType().intValue() == 3){
                //一个月中第几天
                cronExp.append("? ");
                //月份
                cronExp.append("* ");
                //周
                Integer[] weeks = taskScheduleModel.getDayOfWeeks();
                for(int i = 0; i < weeks.length; i++){
                    if(i == 0){
                        cronExp.append(weeks[i]);
                    } else{
                        cronExp.append(",").append(weeks[i]);
                    }
                }

            }

            //按每月
            else if(taskScheduleModel.getJobType().intValue() == 2){
                //一个月中的哪几天
                Integer[] days = taskScheduleModel.getDayOfMonths();
                for(int i = 0; i < days.length; i++){
                    if(i == 0){
                        cronExp.append(days[i]);
                    } else{
                        cronExp.append(",").append(days[i]);
                    }
                }
                //月份
                cronExp.append(" * ");
                //周
                cronExp.append("?");
            }
        }
        else {
            //时或分或秒参数未配置
            log.info("时或分或秒参数未配置");
        }
        return cronExp.toString();
    }

}
