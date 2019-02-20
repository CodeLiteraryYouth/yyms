package com.leanin;

import com.alibaba.fastjson.JSON;
import com.leanin.domain.dto.PlanInfoDto;
import com.leanin.domain.vo.*;
import com.leanin.mapper.*;
import com.leanin.utils.CSMSUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;
import java.util.Map;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestService {

    @Autowired
    PlanInfoMapper planInfoMapper;

    @Autowired
    PlanPatientMapper planPatientMapper;

    @Autowired
    MsgInfoMapper msgInfoMapper;

    @Autowired
    SatisfyPlanMapper satisfyPlanMapper;
    @Autowired
    SatisfyPatientMapper satisfyPatientMapper;

    @Test
    public void test(){
        List<PlanInfoDto> allPlan = planInfoMapper.findAllPlan();
        System.out.println(allPlan);
    }

    @Test
    public void testSend(){
        List<PlanInfoDto> planList = planInfoMapper.findAllPlan();
        for (PlanInfoDto planInfo : planList) {
            //根据病人的编号查询计划病人信息
            List<PlanPatientVo> planPatientList = planPatientMapper.findPlanPatientList(planInfo.getPlanNum(), 0, null);
            if (planPatientList.size() == 0){
                continue;
            }
            for (PlanPatientVo patientDto : planPatientList) {
                //如果当前时间大于病人随访时间，进行发送消息
                if (System.currentTimeMillis() > patientDto.getNextDate().getTime()
                        && patientDto.getSendType() == 1 && patientDto.getPlanPatsStatus() == 0) {
                    switch (planInfo.getPlanSendType()) {
                        //短信
                        case 1:
                            /*String msg = planInfo.getMsgInfo().getMsgText() + "表单的URL";
//                            String msg = msgInfo.getMsgText();
//                            log.info("发送的短信内容为:" + msg);
                            //将病人的手机号码以逗号隔开进行发送 planPatientList.stream().map(PlanPatientDto::getPatientPhone).collect(Collectors.joining(","))
                            Map map = CSMSUtils.sendMessage(msg, "18556531536");//patientDto.getPatientPhone()
                            //设置病人发送状态
                            String msgStatus = (String) map.get("msg");
//                            patientDto.setSendStatus(map.get("msg").toString());
                            if (msgStatus.equals("true")) {
                                patientDto.setSendType(2); //发送成功
                                if (planInfo.getPlanType() == 1){//随访计划
                                    patientDto.setPlanPatsStatus(1); //修改成待随访状态
                                }else{//宣教计划
                                    patientDto.setPlanPatsStatus(2); //修改成已完成状态
                                }

                            } else {
                                patientDto.setSendType(3);//发送失败
                            }*/
                            break;
                        //微信公众号
                        case 2:
                            break;
                        case 3 :
                            String msg = planInfo.getMsgInfo().getMsgText() + "表单的URL";
                            //将病人的手机号码以逗号隔开进行发送 planPatientList.stream().map(PlanPatientDto::getPatientPhone).collect(Collectors.joining(","))
                            Map map = CSMSUtils.sendMessage(msg, "18556531536");//patientDto.getPatientPhone()
                            //设置病人发送状态
                            String msgStatus = (String) map.get("msg");
                            if (msgStatus.equals("true")) {
                                patientDto.setSendType(2); //发送成功
                                if (planInfo.getPlanType() == 1){//随访计划
                                    patientDto.setPlanPatsStatus(1); //修改成待随访状态
                                }else{//宣教计划
                                    patientDto.setPlanPatsStatus(2); //修改成已完成状态
                                }

                            } else {
                                patientDto.setSendType(3);//发送失败
                            }
                            break;
                    }
                    planPatientMapper.updatePlanPatient(patientDto);
                }
            }
        }
    }

    @Test
    public void update(){
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
                int days = (int) (System.currentTimeMillis() - nextDate.getTime()) / (60 * 3600 * 24);//两个时间相差的天数
                switch (planPatientVo.getPlanPatsStatus()) {
                    case -1: // -1 收案
                        break;
                    case 0: //未发送表单或者短信前的状态
                        //判断是否过期
                        if (days > validDays) {//失访
//                            updateRecord(3, planPatientVo, rulesInfo, timeSelect,
//                                    timeChoosed, weeks, sendTimeDays, sendTimeMonths);
                        }
                        break;
                    case 1: //1：待随访
                        //判断是否过期
                        if (days > validDays) {//失访
//                            updateRecord(3, planPatientVo, rulesInfo, timeSelect,
//                                    timeChoosed, weeks, sendTimeDays, sendTimeMonths);
                        }
                        break;
                    case 2: //已完成随访
                        //判断是否过期
//                        updateRecord(2, planPatientVo, rulesInfo, timeSelect,
//                                timeChoosed, weeks, sendTimeDays, sendTimeMonths);
                        break;
                    case 3: //已过期随访
                        break;
                }
            }
        }
    }

    @Test
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
//                    sendMsg(list,msgInfo.getMsgText(),rangeDays);
                    for (SatisfyPatientVo satisfyPatientVo : list) {

                        int days = (int) ((System.currentTimeMillis() - satisfyPatientVo.getPatientDateTime().getTime()) / (3600 * 24*1000));
                        if (System.currentTimeMillis() - satisfyPatientVo.getPatientDateTime().getTime() > 0) {

                            if (days > rangeDays){//判断是否过期
                                satisfyPatientVo.setFinishType(3);//已过期
                            }else{
                                if (satisfyPatientVo.getSendType() == 1){//未发送
                                    Map map = CSMSUtils.sendMessage(msgInfo.getMsgText(), "15378763022");
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
                    break;
                case 2 : //app
                    break;
                case 3 : //短信
                    //执行相关发送操作
//                    sendMsg(list,msgInfo.getMsgText(),rangeDays);
                    break;
            }
        }
    }
}
