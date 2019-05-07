package com.leanin.schdule.task;

import com.alibaba.fastjson.JSON;
import com.leanin.domain.dto.PlanInfoDto;
import com.leanin.domain.vo.PlanPatientVo;
import com.leanin.domain.vo.RulesInfoVo;
import com.leanin.schdule.mapper.FollowRecordMapper;
import com.leanin.schdule.mapper.PlanInfoMapper;
import com.leanin.schdule.mapper.PlanPatientMapper;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

//修改下次随访时间
@DisallowConcurrentExecution
@Slf4j
public class UpdateNextTask implements Job {

    @Autowired
    PlanInfoMapper planInfoMapper;

    @Autowired
    PlanPatientMapper planPatientMapper;

    @Autowired
    FollowRecordMapper followRecordMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        List<PlanInfoDto> planInfoDtos = planInfoMapper.findAllPlan();
        for (PlanInfoDto planInfoDto : planInfoDtos) {
            //读取规则 解析规则
            RulesInfoVo rulesInfo = planInfoDto.getRulesInfo();
            String rulesInfoText = rulesInfo.getRulesInfoText();
            Map rulesMap = JSON.parseObject(rulesInfoText, Map.class);
//            String tiemFont = (String) rulesMap.get("tiemFont");//获取下次任务的时间 1天 2星期 3月
            int validDays = Integer.parseInt((String) rulesMap.get("validDays"));

            int timeChoosed = Integer.parseInt((String) rulesMap.get("timeChoosed")); //1 6:00， 2 7:00 一次后推直到 16 21:00
            String timeSelect = (String) rulesMap.get("timeSelect");
            String weeks1 = (String) rulesMap.get("weeks");
            int weeks = 0;
            if (!"".equals(weeks1) && weeks1 != null) {
                weeks = Integer.parseInt(weeks1);
            }
            String sendTimeDays1 = (String) rulesMap.get("sendTimeDays");
            int sendTimeDays = 0;
            if (!"".equals(sendTimeDays1) && sendTimeDays1 != null) {
                sendTimeDays = Integer.parseInt(sendTimeDays1);
            }
            String sendTimeMonths1 = (String) rulesMap.get("sendTimeMonths");
            int sendTimeMonths = 0;
            if (!"".equals(sendTimeMonths1) && sendTimeMonths1 != null) {
                sendTimeMonths = Integer.parseInt(sendTimeMonths1);
            }
            List<PlanPatientVo> planPatientList = planPatientMapper.findPlanPatientList(planInfoDto.getPlanNum(), null,null, null);
            if (planPatientList.size() == 0){
                continue;
            }
            //判断是否完成随访或者已经过期
            for (PlanPatientVo planPatientVo : planPatientList) {
                Date nextDate = planPatientVo.getNextDate();//随访时间
                int days = (int) (System.currentTimeMillis() - nextDate.getTime()) / (60 * 3600 * 24);//两个时间相差的天数
                switch (planPatientVo.getPlanPatsStatus()) {
                    case -1: // -1 收案
                        break;
                    case 0: //未发送表单或者短信前的状态
                        //判断是否过期
                        if (days > validDays) {//已过期
                            updateRecord(3, planPatientVo, rulesInfo, timeSelect,
                                        timeChoosed, weeks, sendTimeDays, sendTimeMonths);
                        }

                        break;
                    case 1: //1：待随访
                        //判断是否过期
                        if (days > validDays) {//已过期
                            updateRecord(3, planPatientVo, rulesInfo, timeSelect,
                                    timeChoosed, weeks, sendTimeDays, sendTimeMonths);
                        }
                        break;
                    case 2: //已完成随访
                        //判断是否过期
                        if (days > validDays) {//已过期
                            updateRecord(2, planPatientVo, rulesInfo, timeSelect,
                                    timeChoosed, weeks, sendTimeDays, sendTimeMonths);
                        }
                        break;
                    case 3: //已过期随访
                        updateRecord(3, planPatientVo, rulesInfo, timeSelect,
                                timeChoosed, weeks, sendTimeDays, sendTimeMonths);
                        break;
                    case 4: //随访异常
                        if (days > validDays) {//已过期
                            updateRecord(4, planPatientVo, rulesInfo, timeSelect,
                                    timeChoosed, weeks, sendTimeDays, sendTimeMonths);
                        }
                        break;
                }
            }
        }

    }

    private boolean updateRecord(Integer status,PlanPatientVo planPatientVo,RulesInfoVo rulesInfo,String timeSelect,
                                 Integer timeChoosed,Integer weeks,Integer sendTimeDays,Integer sendTimeMonths){
        //填写失访记录
        planPatientVo.setPlanPatsStatus(status); // 2 已完成随访 3 已过期  4 随访异常
        followRecordMapper.addFollowRecord(planPatientVo);
        if (rulesInfo.getRulesInfoTypeName() == 1) {//阶段随访
            //计算下次随访时间
            Date date = setNextDate(new Date(), timeSelect, timeChoosed, weeks, sendTimeDays, sendTimeMonths);
            //复位
            planPatientVo.setNextDate(date); //设置下次随访时间
            planPatientVo.setSendType(1); //未发送
            planPatientVo.setPlanPatsStatus(0);
            planPatientVo.setFormStatus(1);
            planPatientVo.setHandleSugges("");
            planPatientMapper.updatePlanPatient(planPatientVo);
        }
        return true;
    }

    //设置下次随访时间

    //定期随访
    private Date setNextDate(Date nowDate,String timeSelect, Integer timeChoosed, Integer weeks, Integer sendTimeDays, Integer sendTimeMonths) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(nowDate);
        Date nextDate = null;
        switch (timeSelect) {
            case "1": {//每天
                calendar.add(Calendar.DATE, 1);//默认从明天开始
                calendar.set(Calendar.HOUR_OF_DAY, timeChoosed + 5);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                nextDate = calendar.getTime();
            }
            break;
            case "2": {//每周
                calendar.set(Calendar.DAY_OF_WEEK, weeks + 1);
                calendar.set(Calendar.HOUR_OF_DAY, timeChoosed + 5);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                nextDate = calendar.getTime();

                if (nextDate.getTime() < nowDate.getTime()) {
                    calendar.add(Calendar.WEEK_OF_YEAR, 1);//默认从下周开始
                    nextDate = calendar.getTime();
                }

            }
            break;
            case "3": {//每月
                calendar.set(Calendar.DAY_OF_MONTH, sendTimeDays);
                calendar.set(Calendar.HOUR_OF_DAY, timeChoosed + 5);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);

                nextDate = calendar.getTime();
                if (nextDate.getTime() < nowDate.getTime()) {
                    calendar.add(Calendar.MONTH, 1);//默认从下个月开始
                    nextDate = calendar.getTime();
                }
            }
            break;
            case "4": {//每年
                calendar.set(Calendar.MONTH, sendTimeMonths - 1);//0 表示 一月
                calendar.set(Calendar.DAY_OF_MONTH, sendTimeDays);//超过当月日期则表示则超过日期累计到下个月
                calendar.set(Calendar.HOUR_OF_DAY, timeChoosed + 5);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                nextDate = calendar.getTime();
                if (nextDate.getTime() < nowDate.getTime()) {
                    calendar.add(Calendar.YEAR, 1);//默认从开始
                    nextDate = calendar.getTime();
                }
            }
            break;
        }
        return nextDate;
    }


}
