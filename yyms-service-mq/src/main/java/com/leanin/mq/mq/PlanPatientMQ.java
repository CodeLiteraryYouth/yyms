package com.leanin.mq.mq;

import com.alibaba.fastjson.JSON;
import com.leanin.domain.vo.*;
import com.leanin.exception.ExceptionCast;
import com.leanin.mq.config.RabbitMQConfig;
import com.leanin.mq.dao.*;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
public class PlanPatientMQ {

    @Autowired
    PlanPatientMapper planPatientMapper;

    @Autowired
    PlanInfoMapper planInfoMapper;

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    RulesInfoMapper rulesInfoMapper;

    @Autowired
    SatisfyPlanMapper satisfyPlanMapper;

    @Autowired
    SatisfyPatientMapper satisfyPatientMapper;

    //满意度监听队列
    @RabbitListener(queues = RabbitMQConfig.QUEUE_INSERT_SP)
    @Transactional(rollbackFor = Exception.class)
    public void insertSatisfyPlan(String planSatisfyNum){
        //获取满意度计划信息
        SatisfyPlanVo satisfyPlan = satisfyPlanMapper.findSatisfyPlanById(planSatisfyNum);
        //获取缓存中的患者信息
        List<Map> list = (List<Map>) redisTemplate.boundHashOps("satisfyPlan").get(planSatisfyNum);
        //获取规则内容
        String rulesText = satisfyPlan.getRulesText();
        Map rulesMap = JSON.parseObject(rulesText, Map.class);

        String tiemFont = (String) rulesMap.get("tiemFont");//获取下次任务的时间 1天
        String timeNumStr = (String) rulesMap.get("timeNum");
        int timeChoosed = Integer.parseInt((String) rulesMap.get("timeChoosed")); //1 6:00， 2 7:00 一次后推直到 16 21:00
        String timeSelect = (String) rulesMap.get("timeSelect");//1出院
        String rangeDays = (String) rulesMap.get("rangeDays");//范围天数

        SatisfyPatientVo satisfyPatientVo=new SatisfyPatientVo();
        for (Map map : list) {
            satisfyPatientVo.setSatisfyPlanNum(satisfyPlan.getPlanSatisfyNum());//设置满意度计划id
            Long patientId = Long.parseLong(map.get("patientId").toString());
            satisfyPatientVo.setPatientId(patientId);//患者id
            String patientName = (String) map.get("patientName");
            satisfyPatientVo.setPatientName(patientName);//患者姓名
            Integer patientSex = (Integer) map.get("patientSex");
            satisfyPatientVo.setPatientSex(patientSex);//设置病人性别
            Integer patientAge = (Integer) map.get("patientAge");
            satisfyPatientVo.setPatientAge(patientAge);//设置病人年龄
            String patientPhone = (String) map.get("patientPhone");
            satisfyPatientVo.setPatientPhone(patientPhone);//设置病人手机号码
            satisfyPatientVo.setPatientWard(satisfyPlan.getPatientWard());//设置病人科室  可能是集合
            Date lastDate = new Date((Long) map.get("lastDate"));//获取最近一次的出院时间
//                String patientCondition = (String) map.get("patientCondition");
//                planPatientVo.setPatientCondition(patientCondition);//设置病人情况  可能是集合
            satisfyPatientVo.setPatientDiagous(satisfyPlan.getDiseaseName());//设置病人诊断  可能是集合
            satisfyPatientVo.setPatientType(satisfyPlan.getPatientType());//设置病人来源   可能是集合
//                String areaCode = (String) map.get("areaCode");
//                planPatientVo.setAreaCode(areaCode);//设置院区编码   可能是集合
            satisfyPatientVo.setFinishType(1); //完成状态  1 未完成 2已完成
            satisfyPatientVo.setSendType(1); //发送状态；1 未发送 2 已发送 3 发送失败
            satisfyPatientVo.setPatientStatus(0); //是否删除; 0 未删除 1 已删除
//            satisfyPatientVo.setAreaCode();//设置院区编码
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(lastDate);
            calendar.add(Calendar.DATE, Integer.parseInt(timeNumStr));
            calendar.set(Calendar.HOUR_OF_DAY, timeChoosed + 5);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            Date time = calendar.getTime();
            satisfyPatientVo.setPatientDateTime(time);
            satisfyPatientMapper.insertSelective(satisfyPatientVo);
        }
        Long delete = redisTemplate.boundHashOps("satisfyPlan").delete(planSatisfyNum);

    }


    //随访监听队列
    @RabbitListener(queues = RabbitMQConfig.QUEUE_INSERT_NAME)
    @Transactional(rollbackFor = Exception.class)
    public void insertData(String planNum) {
        //获取计划信息
        PlanInfoVo planInfo = planInfoMapper.findPlanInfoById(planNum);
//        log.info("计划信息为:" + planNum);
        //获取缓存中的病人数据
        List<Map> list = (List<Map>) redisTemplate.boundHashOps("plan").get(planNum);
        if (list != null && list.size() > 0) {//有缓存
            //获取规则信息
            RulesInfoVo rules = rulesInfoMapper.findRulesById(planInfo.getRulesInfoNum());
            String rulesInfoText = rules.getRulesInfoText();
            Map rulesMap = JSON.parseObject(rulesInfoText, Map.class);//获取规则
            String tiemFont = (String) rulesMap.get("tiemFont");//获取下次任务的时间 1天 2星期 3月
            String timeNumStr = (String) rulesMap.get("timeNum");
            int timeNum = 0;
            if (!"".equals(timeNumStr) && timeNumStr != null) {
                timeNum = Integer.parseInt(timeNumStr);//
            }

            int timeChoosed = Integer.parseInt((String) rulesMap.get("timeChoosed")); //1 6:00， 2 7:00 一次后推直到 16 21:00
//            int timeChoosed = (int) rulesMap.get("timeChoosed"); //1 6:00， 2 7:00 一次后推直到 16 21:00
            String timeSelect = (String) rulesMap.get("timeSelect");
            Date timePick = null;
            Object timePick1 = rulesMap.get("timePick");
            if (!"".equals(timePick1) && timePick1 != null) {
                SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    timePick = sf.parse((String) timePick1);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
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
//            log.info("规则信息:{}", rulesMap);
            PlanPatientVo planPatientVo = new PlanPatientVo();
            for (Map map : list) {
                planPatientVo.setPlanNum(planInfo.getPlanNum());//设置计划编号
                Long patientId = Long.parseLong(map.get("patientId").toString());
                planPatientVo.setPatientId(patientId);//设置病人id
                planPatientVo.setSendType(1);//发送状态  1未发送  2已发送 3发送异常
                String patientName = (String) map.get("patientName");
                planPatientVo.setPatientName(patientName);//设置病人姓名
                Integer patientSex = (Integer) map.get("patientSex");
                planPatientVo.setPatientSex(patientSex);//设置病人性别
                Integer patientAge = (Integer) map.get("patientAge");
                planPatientVo.setPatientAge(patientAge);//设置病人年龄
                String patientPhone = (String) map.get("patientPhone");
                planPatientVo.setPatientPhone(patientPhone);//设置病人手机号码
                planPatientVo.setPatientWard(planInfo.getPatsWardCode());//设置病人科室  可能是集合
                planPatientVo.setFollowType(1);//设置随访状态 1 未完成  2 已完成
                planPatientVo.setPatientStatus(1);//设置 状态  1：未删除  2 已删除
//                String patientCondition = (String) map.get("patientCondition");
//                planPatientVo.setPatientCondition(patientCondition);//设置病人情况  可能是集合
                planPatientVo.setPatientDiagous(planInfo.getDiseaseCode());//设置病人诊断  可能是集合
                planPatientVo.setPatientType(planInfo.getPatientInfoSource());//设置病人来源   可能是集合
//                String areaCode = (String) map.get("areaCode");
//                planPatientVo.setAreaCode(areaCode);//设置院区编码   可能是集合
                planPatientVo.setPlanPatsStatus(0);//-1:收案 0：全部 1：待随访 2：已完成；3:已过期
                planPatientVo.setPatientSource(planInfo.getPatientInfoSource());//设置患者来源
//                planPatientVo.setRulesInfoId(rules.getRulesInfoId());//设置随访规则id

//                if(rules.getRulesInfoType() ==1){
//
//                }else if (rules.getRulesInfoType() ==2){
//
//                }else if (rules.getRulesInfoType() ==3){
//
//                }
                Date lastDate = new Date((Long) map.get("lastDate"));//获取最近一次的出院时间
                Date nextDate = null;

                switch (rules.getRulesInfoTypeName()) {
                    case 1: {//1：定期随访
                        nextDate = setNextDate(timeSelect, timeChoosed, weeks, sendTimeDays, sendTimeMonths);
                    }
                    break;
                    case 2: {//2：定时随访
                        nextDate = setNextDate(timePick, timeChoosed);
                    }
                    break;
                    case 3: {//3：普通随访
                        nextDate = setNextDate(lastDate, tiemFont, timeNum, timeChoosed);
                    }
                    break;
                    case 4: {//4：闭环宣教

                    }
                    break;
                    case 5: {//5：普通宣教

                    }
                    break;
                    case 6: {//6：药品宣教

                    }
                    break;
                    case 7: {//7：疾病宣教

                    }
                    break;
                    case 8: {//8：普通提醒

                    }
                    break;
                }
                planPatientVo.setNextDate(nextDate);//设置下次随访日期

                planPatientMapper.addPlanPatient(planPatientVo);//将数据存到数据中

            }
            Long delete = redisTemplate.boundHashOps("plan").delete(planInfo.getPlanNum());
//            log.info("是否删除缓存中存的数据:{}", planInfo.getPlanNum(), delete);
        }
    }

    //设置普通随访时间
    private Date setNextDate(Date lastDate, String tiemFont, Integer timeNum, Integer timeChoosed) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(lastDate);
        Date nextDate = null;
        //普通随访
        switch (tiemFont) {
            case "1": {//天
                calendar.add(Calendar.DATE, timeNum);
                calendar.set(Calendar.HOUR_OF_DAY, timeChoosed + 5);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                nextDate = calendar.getTime();
            }
            break;
            case "2": {//星期
                calendar.add(Calendar.WEEK_OF_YEAR, timeNum);
                calendar.set(Calendar.HOUR_OF_DAY, timeChoosed + 5);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                nextDate = calendar.getTime();
            }
            break;
            case "3": {//月
                calendar.add(Calendar.MONTH, timeNum);
                calendar.set(Calendar.HOUR_OF_DAY, timeChoosed + 5);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                nextDate = calendar.getTime();
            }
            break;
        }
        return nextDate;
    }

    //定期随访
    private Date setNextDate(String timeSelect, Integer timeChoosed, Integer weeks, Integer sendTimeDays, Integer sendTimeMonths) {
        Calendar calendar = Calendar.getInstance();
        Date nowDate = new Date();
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

    //定时随访
    private Date setNextDate(Date timePick, Integer timeChoosed) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(timePick);
        calendar.set(Calendar.HOUR_OF_DAY, timeChoosed + 5);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date nextDate = calendar.getTime();
        return nextDate;
    }
}
