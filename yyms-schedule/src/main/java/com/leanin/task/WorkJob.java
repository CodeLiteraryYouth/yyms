package com.leanin.task;

import com.alibaba.fastjson.JSON;
import com.leanin.domain.dto.PlanInfoDto;
import com.leanin.domain.vo.*;
import com.leanin.mapper.*;
import com.leanin.utils.CSMSUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class WorkJob {

    @Autowired
    PlanInfoMapper planInfoMapper;

    @Autowired
    PlanPatientMapper planPatientMapper;

    @Autowired
    MsgInfoMapper msgInfoMapper;

    @Autowired
    FollowRecordMapper followRecordMapper;

    @Autowired
    SatisfyPlanMapper satisfyPlanMapper;

    @Autowired
    SatisfyPatientMapper satisfyPatientMapper;

    //随访 满意度 发送短信
//    @Scheduled(cron = "0 0/2 * * * ? ")
//    @Transactional(rollbackFor = Exception.class)
    public void followPlan() {
        log.info("开始推送消息");
        //查询计划列表信息
        List<PlanInfoDto> planList = planInfoMapper.findAllPlan();
        log.info("计划信息列表为:" + JSON.toJSONString(planList));
        for (PlanInfoDto planInfo : planList) {
            //根据病人的编号查询计划病人信息
            List<PlanPatientVo> planPatientList = planPatientMapper.findPlanPatientList(planInfo.getPlanNum(), 0, null);
            log.info("该计划的病人列表信息为:" + JSON.toJSONString(planPatientList));
            if (planPatientList.size() == 0) {//患者信息长度为0的话  跳过循环
                continue;
            }
            for (PlanPatientVo patientDto : planPatientList) {
                //如果当前时间大于病人随访时间，进行发送消息
                if (System.currentTimeMillis() > patientDto.getNextDate().getTime()
                        && patientDto.getSendType() == 1 && patientDto.getPlanPatsStatus() == 0) {
                    switch (planInfo.getPlanSendType()) {
                        //短信
                        case 1:
                            sendMessage(planInfo, patientDto);
                            break;
                        //微信公众号
                        case 2:
                            break;
                        case 3:
                            sendMessage(planInfo, patientDto);
                            break;
                    }
                }
            }
            log.info("一个计划推送完毕");
        }
    }

    private void sendMessage(PlanInfoDto planInfo, PlanPatientVo patientDto) {
        String msg = planInfo.getMsgInfo().getMsgText() + "表单的URL";
        log.info("发送的短信内容为:" + msg);
        //将病人的手机号码以逗号隔开进行发送 planPatientList.stream().map(PlanPatientDto::getPatientPhone).collect(Collectors.joining(","))
        Map map = CSMSUtils.sendMessage(msg, "15378763022");//patientDto.getPatientPhone()
        //设置病人发送状态
        String msgStatus = (String) map.get("msg");
        if (msgStatus.equals("true")) {
            patientDto.setSendType(2); //发送成功
            if (planInfo.getPlanType() == 1) { //随访计划
                patientDto.setPlanPatsStatus(1); //修改成待随访状态
            } else {//宣教计划
                patientDto.setPlanPatsStatus(2); //修改成已完成状态
            }
        } else {
            patientDto.setSendType(3); //发送失败
        }
        planPatientMapper.updatePlanPatient(patientDto);
    }

    //满意度计划
    @Scheduled(cron = "0 0/2 * * * ? ")//
    @Transactional(rollbackFor = Exception.class)
    public void styPlan(){
        //查询所有计划信息
        List<SatisfyPlanVo>planVos=satisfyPlanMapper.findList();
        for (SatisfyPlanVo planVo : planVos) {
            //获取计划对应的短消息 信息
            MsgInfoVo msgInfo = msgInfoMapper.findMsgInfoById(planVo.getMsgId());
            //获取计划对应的患者信息
            List<SatisfyPatientVo> list = satisfyPatientMapper.findList(planVo.getPlanSatisfyNum(), null, null, null, null, null, 1);
            if (list.size() == 0){
                continue;
            }
            Map ruleMap = JSON.parseObject(planVo.getRulesText(), Map.class);
            int rangeDays = Integer.parseInt((String) ruleMap.get("rangeDays"));
            //判断患者信息是否处于 未发送的状态
            switch (planVo.getDiscoverType()){
                case 1 : //短信或者app
                    sendMsg(list,msgInfo.getMsgText(),rangeDays);
                    break;
                case 2 : //app
                    break;
                case 3 : //短信
                    //执行相关发送操作
                    sendMsg(list,msgInfo.getMsgText(),rangeDays);
                    break;
            }
        }
    }

    //发送短信操作  修改数据状态
    private void sendMsg(List<SatisfyPatientVo> list,String msgText,Integer rangeDays){
        //判断是否过期
        for (SatisfyPatientVo satisfyPatientVo : list) {
            int days = (int) ((System.currentTimeMillis() - satisfyPatientVo.getPatientDateTime().getTime()) / (1000*3600 * 24));
            if (System.currentTimeMillis() - satisfyPatientVo.getPatientDateTime().getTime() > 0){
                if (days > rangeDays){//判断是否过期
                    satisfyPatientVo.setFinishType(3);//已过期
                }else{
                    if (satisfyPatientVo.getSendType() == 1){//未发送
                        Map map = CSMSUtils.sendMessage(msgText, "15378763022"); //satisfyPatientVo.getPatientPhone()
                        String msgStatus = (String) map.get("msg");
                        if (msgStatus.equals("true")){
                            satisfyPatientVo.setSendType(2); //已发送短信
                        }else{
                            satisfyPatientVo.setSendType(3); //发送失败
                        }
                    }
                }
                satisfyPatientMapper.updateByPrimaryKeySelective(satisfyPatientVo);
            }
        }
    }

//    @Scheduled(cron = "0 0/2 * * * ? ")
//    @Transactional(rollbackFor = Exception.class)
    public void updateNextTime() {
        log.info("更新下次随访时间");
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
            List<PlanPatientVo> planPatientList = planPatientMapper.findPlanPatientList(planInfoDto.getPlanNum(), null, null);
            if (planPatientList.size() == 0) {
                continue;
            }
            //判断是否完成随访或者已经过期
            for (PlanPatientVo planPatientVo : planPatientList) {
                Date nextDate = planPatientVo.getNextDate();//随访时间
                int days = (int) (System.currentTimeMillis() - nextDate.getTime()) / (1000 * 3600 * 24);//两个时间相差的天数
                switch (planPatientVo.getPlanPatsStatus()) {
                    case -1: // -1 收案
                        break;
                    case 0: //未发送表单或者短信前的状态
                        //判断是否过期
                        if (days > validDays) {//失访
                            updateRecord(3, planPatientVo, rulesInfo, timeSelect,
                                    timeChoosed, weeks, sendTimeDays, sendTimeMonths);
                        }
                        break;
                    case 1: //1：待随访
                        //判断是否过期
                        if (days > validDays) {//失访
                            updateRecord(3, planPatientVo, rulesInfo, timeSelect,
                                    timeChoosed, weeks, sendTimeDays, sendTimeMonths);
                        }
                        break;
                    case 2: //已完成随访
                        //判断是否过期
                        updateRecord(2, planPatientVo, rulesInfo, timeSelect,
                                timeChoosed, weeks, sendTimeDays, sendTimeMonths);
                        break;
                    case 3: //已过期随访
                        break;
                }
            }
            log.info("一次计划更新完成{}",planInfoDto.getPlanNum());
        }

    }

    private boolean updateRecord(Integer status, PlanPatientVo planPatientVo, RulesInfoVo rulesInfo, String timeSelect,
                                 Integer timeChoosed, Integer weeks, Integer sendTimeDays, Integer sendTimeMonths) {
        //填写失访记录
        planPatientVo.setPlanPatsStatus(status); // 2 已完成随访 3 失访
        followRecordMapper.addFollowRecord(planPatientVo);
        if (rulesInfo.getRulesInfoTypeName() == 1) {//阶段随访
            //计算下次随访时间
            Date date = setNextDate(new Date(), timeSelect, timeChoosed, weeks, sendTimeDays, sendTimeMonths);
            //复位
            planPatientVo.setNextDate(date); //设置下次随访时间
            planPatientVo.setSendType(1); //未发送
            planPatientVo.setPlanPatsStatus(0); //未发送状态
            planPatientVo.setFollowType(1);
            planPatientMapper.updatePlanPatient(planPatientVo);
        }
        return true;
    }

    //设置下次随访时间

    //定期随访
    private Date setNextDate(Date nowDate, String timeSelect, Integer timeChoosed, Integer weeks, Integer sendTimeDays, Integer sendTimeMonths) {
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
