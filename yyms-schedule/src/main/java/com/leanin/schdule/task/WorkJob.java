package com.leanin.schdule.task;

import com.alibaba.fastjson.JSON;
import com.leanin.domain.dao.WxSendDao;
import com.leanin.domain.dto.PlanInfoDto;
import com.leanin.domain.vo.*;
import com.leanin.schdule.mapper.*;
import com.leanin.schdule.repository.MessageRecordRepository;
import com.leanin.schdule.repository.WxSendRepository;
import com.leanin.utils.CSMSUtils;
import com.leanin.utils.HttpClient;
import com.leanin.utils.HttpClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Component
@Slf4j
public class WorkJob {

    @Value("${wx.appId}")
    private String appId;

    @Value("${wx.appSecret}")
    private String appSecret;

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

    @Autowired
    MsgRecordMapper msgRecordMapper;

    @Autowired
    MessageTopicMapper messageTopicMapper;

    @Autowired
    MessagePatientMapper messagePatientMapper;

    @Autowired
    WxSendRepository wxSendRepository;

    @Autowired
    MessageRecordRepository messageRecordRepository;

    //13817165550
    //短信主题发送短信
    @Scheduled(cron = "0 0/10 * * * ? ")
    @Transactional(rollbackFor = Exception.class)
    public void messagePlan() {
        List<MessageTopicVo> messageTopicVos = messageTopicMapper.findMsgTopicList(null);
        for (MessageTopicVo messageTopicVo : messageTopicVos) {
            log.info("正在推送的短信主题:{}", JSON.toJSONString(messageTopicVo));
            List<MessagePatientVo> messagePatientVos = messagePatientMapper.findList(null, 1);
            if (messagePatientVos.size() == 0) {
                continue;
            }
            for (MessagePatientVo messagePatientVo : messagePatientVos) {
                log.info("短信主题内的患者信息:{}", JSON.toJSONString(messagePatientVo));
                String content = messageTopicVo.getMsgTopicHead() + messageTopicVo.getMsgContent();
//                Map map = CSMSUtils.sendMessage(content, messagePatientVo.getPatientPhone());
//                String msgStatus = (String) map.get("msg");
                String msgStatus = "true";
                if (msgStatus.equals("true")) {
                    log.info("发送的内容和号码：{}", content, messagePatientVo.getPatientPhone(), msgStatus);
                    messagePatientVo.setSendType(2);//发送成功
                } else {
                    log.info("短信主题发送的短信失败");
                    messagePatientVo.setSendType(3);//发送失败
                }
                messagePatientMapper.updateByPrimaryKeySelective(messagePatientVo);///*messageTopicVo.getMsgTopicCreater()*/
                MessageRecord messageRecord = new MessageRecord();
//                messageRecord.setMsgSendId(null);//主键自增
                messageRecord.setMsgSendName(0L);// 0 表示系统自动发送
                messageRecord.setMsgSendWard(messageTopicVo.getMsgTopicCreaterWard());//计划负责科室
                messageRecord.setMsgSendDate(new Date());
                messageRecord.setMsgSendNum(messagePatientVo.getPatientPhone());//发送手机号
                messageRecord.setMsgText(content);//发送内容
                messageRecord.setMsgSendStatus(messagePatientVo.getSendType());//发送状态  2 发送成功  3 发送失败
                messageRecord.setMsgThem(messageTopicVo.getMsgTopicTitle());//短信主题
                messageRecord.setPlanType(4);//计划类型  1 随访 2 宣教 3 满意度 4 短信主题  5 自定义短信
                messageRecord.setPlanPatientId(messagePatientVo.getPatientId());//计划患者主键
                messageRecord.setPatientId(messagePatientVo.getPatientId() + "");//his 患者主键
                messageRecord.setPlanNum(messageTopicVo.getMsgTopicId());//短信主题 主键
//                messageRecord.setFormId(null);//表单主键
//                messageRecord.setPatientWard(null); //患者科室
                messageRecord.setPatientSource(messagePatientVo.getPatientType());  //患者来源
                messageRecord.setNextDate(messageTopicVo.getMsgSendDate());//计划患者发送时间
                MessageRecord save = messageRecordRepository.save(messageRecord);
                log.info("添加的短信主题发送记录为:{}", JSON.toJSONString(messageRecord));
            }
        }
    }

    //随访 宣教 发送短信
    @Scheduled(cron = "0 0/8 * * * ? ")
    @Transactional(rollbackFor = Exception.class)
    public void followPlan() {
        log.info("开始推送消息");
        //查询计划列表信息
        List<PlanInfoDto> planList = planInfoMapper.findAllPlan();
        log.info("随访/宣教计划信息列表为:" + JSON.toJSONString(planList));
        if (planList.size() < 1) {
            return;
        }

        //初始化参数
        WxSendDao wxSendDao = new WxSendDao();

        //获取accessToken
        String accessToken = getAccessToken();//获取accessToken

        for (PlanInfoDto planInfo : planList) {
            log.info("随访/宣教计划信息:{}", JSON.toJSONString(planInfo));
            //根据病人的编号查询计划病人信息
            List<PlanPatientVo> planPatientList = planPatientMapper.findPlanPatientList(planInfo.getPlanNum(), null, 1, null);
            log.info("未发送表单的病人列表信息为:" + JSON.toJSONString(planPatientList));
            if (planPatientList.size() == 0) {//患者信息长度为0的话  跳过循环
                continue;
            }
            for (PlanPatientVo patientDto : planPatientList) {
                //如果当前时间大于病人随访时间，进行发送消息
                if (System.currentTimeMillis() > patientDto.getNextDate().getTime()
                        && patientDto.getSendType() == 1 && (patientDto.getPlanPatsStatus() == 0 || patientDto.getPlanPatsStatus() == 1)) {

                    switch (planInfo.getPlanSendType()) {
                        case 1: {//短信和公众号
                            log.info("短信和公众号推送:{}");
                            patientDto = sendMessage(planInfo, patientDto);
                            String opendId = patientDto.getOpendId();
                            if (null == opendId){//患者的openid为空 跳过推送模板消息
                                log.info("患者的openId为空",JSON.toJSONString(patientDto));
                            }else{//openid不为空 则进行微信模板推送
                                int i = sendWxMsg(planInfo, patientDto, wxSendDao, null, null, 1, accessToken);

                                if (i == 3) {//accessToken失效 重新获取令牌
                                    accessToken = getAccessToken();//获取accessToken
                                    i = sendWxMsg(planInfo, patientDto, wxSendDao, null, null, 1, accessToken);
                                }
                                if (patientDto.getSendType() == 3 && i == 2) {
                                    log.info("公众号推送成功");
                                    //短信发送失败  公众号推送成功
                                    patientDto.setSendType(2); //发送成功
                                    patientDto.setPlanPatsStatus(1); //修改成待随访状态 宣教待阅读
                                }
                            }
                            //修改推送状态
                            planPatientMapper.updatePlanPatient(patientDto);
                        }
                        break;

                        case 2: {//微信公众号
                            log.info("微信公众号推送:{}");
                            String opendId = patientDto.getOpendId();
                            if (null == opendId){//患者的openid为空 跳过推送模板消息
                                log.info("患者的openId为空",JSON.toJSONString(patientDto));
                                patientDto.setSendType(3); //发送失败
                                patientDto.setPlanPatsStatus(1); //修改成待随访状态 宣教待阅读
                            }else{
                                int flag = sendWxMsg(planInfo, patientDto, wxSendDao, null, null, 1, accessToken);
                                if (flag == 3) {//accessToken失效 重新获取令牌
                                    accessToken = getAccessToken();//获取accessToken
                                    sendWxMsg(planInfo, patientDto, wxSendDao, null, null, 1, accessToken);
                                }
                                if (flag == 2) {
                                    //短信发送失败  公众号推送成功
                                    patientDto.setSendType(2); //发送成功
                                    patientDto.setPlanPatsStatus(1); //修改成待随访状态 宣教待阅读
                                } else {
                                    patientDto.setSendType(3); //发送失败
                                    patientDto.setPlanPatsStatus(1); //修改成待随访状态 宣教待阅读
                                }
                            }
                            //修改推送状态
                            planPatientMapper.updatePlanPatient(patientDto);
                        }
                        break;
                        case 3://短信
                            log.info("短信推送:{}");
                            patientDto = sendMessage(planInfo, patientDto);
                            planPatientMapper.updatePlanPatient(patientDto);
                            break;
                    }
                }
            }
            log.info("一个计划推送完毕");
        }
    }

    private PlanPatientVo sendMessage(PlanInfoDto planInfo, PlanPatientVo patientDto) {
        String msg = planInfo.getMsgInfo().getMsgText();
        log.info("发送的短信内容为:" + msg);
        //将病人的手机号码以逗号隔开进行发送 planPatientList.stream().map(PlanPatientDto::getPatientPhone).collect(Collectors.joining(","))
        String param = "";
        if (planInfo.getPlanType() == 1) {//随访
            param = "http://sf-system.leanin.com.cn/postlist?planPatientId=" + patientDto.getPatientPlanId() + "&planType=1&formNum=" + planInfo.getFollowFormNum();
        } else {//宣教
            param = "http://sf-system.leanin.com.cn/education?planPatientId=" + patientDto.getPatientPlanId() + "&planType=2&formNum=" + planInfo.getFollowFormNum();
        }
//        Map map = CSMSUtils.sendMessage(msg + param, patientDto.getPatientPhone());//patientDto.getPatientPhone()
        //设置病人发送状态
//        String msgStatus = (String) map.get("msg");
        String msgStatus = "true";
        log.info("随访/宣教短信，短信内容，患者手机号，发送状态：{}", msg + param, patientDto.getPatientPhone(), msgStatus);
        if (msgStatus.equals("true")) {
            patientDto.setSendType(2); //发送成功
            if (planInfo.getPlanType() == 1) { //随访计划
                patientDto.setPlanPatsStatus(1); //修改成待随访状态 宣教待阅读
            } else {//宣教计划
                patientDto.setPlanPatsStatus(2); //修改成已完成状态
            }
        } else {
            patientDto.setSendType(3); //发送失败
            patientDto.setPlanPatsStatus(1); //修改成待随访状态 宣教待阅读
        }
        MessageRecord messageRecord = new MessageRecord();
        messageRecord.setMsgSendId(null);//主键自增
        messageRecord.setMsgSendName(0L);// 0 表示系统自动发送
        messageRecord.setMsgSendWard(planInfo.getPlanWardCode());//计划负责科室
        messageRecord.setMsgSendDate(new Date());
        messageRecord.setMsgSendNum(patientDto.getPatientPhone());//发送手机号
        messageRecord.setMsgText(msg + param);//发送内容
        messageRecord.setMsgSendStatus(patientDto.getSendType());//发送状态  2 发送成功  3 发送失败
        messageRecord.setMsgThem(null);//短信主题
        messageRecord.setPlanType(planInfo.getPlanType());//计划类型  1 随访 2 宣教 3 满意度 4 短信主题  5 自定义短信
        messageRecord.setPlanPatientId(patientDto.getPatientPlanId());//计划患者主键
        messageRecord.setPatientId(patientDto.getPatientId() + "");//his 患者主键
        messageRecord.setPlanNum(planInfo.getPlanNum());//短信主题 主键
        messageRecord.setFormId(patientDto.getFormId());//表单主键
        messageRecord.setPatientWard(patientDto.getPatientWard()); //患者科室
        messageRecord.setPatientSource(patientDto.getPatientSource());  //患者来源
        messageRecord.setNextDate(patientDto.getNextDate());//计划患者发送时间
        MessageRecord save = messageRecordRepository.save(messageRecord);
        log.info("发送随访/宣教短信信息", JSON.toJSONString(save));
        return patientDto;
    }

    //满意度计划
    @Scheduled(cron = "0 0/9 * * * ? ")//
    @Transactional(rollbackFor = Exception.class)
    public void styPlan() {
        //查询所有计划信息
        List<SatisfyPlanVo> planVos = satisfyPlanMapper.findList();
        if (planVos.size() < 1) {
            return;
        }
        //获取accesstoken
        String accessToken = getAccessToken();//获取accessToken
        for (SatisfyPlanVo planVo : planVos) {
            log.info("满意度计划信息：{}", JSON.toJSONString(planVo));

            //获取计划对应的患者信息
            List<SatisfyPatientVo> list = satisfyPatientMapper.findList(planVo.getPlanSatisfyNum(), null, null, null, null, null, 1);
            if (list.size() == 0) {
                continue;
            }
            MsgInfoVo msgInfo = null;
            if (planVo.getDiscoverType() != 2) {
                msgInfo = msgInfoMapper.findMsgInfoById(planVo.getMsgId());
            }
            Map ruleMap = JSON.parseObject(planVo.getRulesText(), Map.class);
            int rangeDays = Integer.parseInt((String) ruleMap.get("rangeDays"));


            //判断患者信息是否处于 未发送的状态
            switch (planVo.getDiscoverType()) {
                case 1: //短信或者公众号
                    log.info("短信或者公众号推送方式");
                    //获取计划对应的短消息 信息
                    sendMsg(planVo, list, msgInfo.getMsgText(), rangeDays, 1, accessToken);
                    break;
                case 2: //公众号
                    log.info("公众号推送方式");
                    sendMsg(planVo, list, null, rangeDays, 2, accessToken);
                    break;
                case 3: //短信
                    //执行相关发送操作
                    log.info("短信推送方式");
                    sendMsg(planVo, list, msgInfo.getMsgText(), rangeDays, 3, accessToken);
                    break;
            }
        }
    }

    //发送短信操作  修改数据状态
    private void sendMsg(SatisfyPlanVo satisfyPlanVo, List<SatisfyPatientVo> list, String msgText,
                         Integer rangeDays, Integer type, String accessToken) {
        WxSendDao wxSendDao = new WxSendDao();
        //判断是否过期
        for (SatisfyPatientVo satisfyPatientVo : list) {
            //获取
            int days = (int) ((System.currentTimeMillis() - satisfyPatientVo.getPatientDateTime().getTime()) / (1000 * 3600 * 24));
            if (System.currentTimeMillis() - satisfyPatientVo.getPatientDateTime().getTime() > 0) {
                if (days > rangeDays) {//判断是否过期
//                    if (satisfyPatientVo.getFinishType() == 1) {
                    satisfyPatientVo.setFinishType(3);//已过期
                    satisfyPatientMapper.updateByPrimaryKeySelective(satisfyPatientVo);
                    continue;//跳出本次循环
//                    }
                } /*else {*/
                if (satisfyPatientVo.getSendType() == 1) {//未发送
                    switch (type) {
                        case 1: {//短信 公众号
                            //推送短信
                            String param = "http://sf-system.leanin.com.cn/satisfied?planPatientId=" + satisfyPatientVo.getPatientSatisfyId() + "&planType=3&formNum=" + satisfyPlanVo.getSatisfyNum();
//                            Map map = CSMSUtils.sendMessage(msgText + param, satisfyPatientVo.getPatientPhone()); //satisfyPatientVo.getPatientPhone()
//                            String msgStatus = (String) map.get("msg");
                            String msgStatus = "true";
                            log.info("满意度短信：{}", msgText + param, satisfyPatientVo.getPatientPhone(), msgStatus, satisfyPlanVo.getSatisfyNum());
                            if (msgStatus.equals("true")) {
                                satisfyPatientVo.setSendType(2); //已发送短信
                            } else {
                                satisfyPatientVo.setSendType(3); //发送失败
                            }
                            addMsgRecord(satisfyPatientVo, msgText + param, satisfyPlanVo);
                            //推送公众号
                            String openId = satisfyPatientVo.getOpenId();
                            if (null == openId){
                                log.info("患者的openId为空:{}",JSON.toJSONString(satisfyPatientVo));
                            }else{
                                int flag = sendWxMsg(null, null, wxSendDao, satisfyPlanVo, satisfyPatientVo, 2, accessToken);
                                if (flag == 3) {//accessToken失效 重新获取令牌
                                    accessToken = getAccessToken();//获取accessToken
                                    flag = sendWxMsg(null, null, wxSendDao, satisfyPlanVo, satisfyPatientVo, 2, accessToken);
                                }
                                if (flag == 2 && satisfyPatientVo.getSendType() == 3) {
                                    //公众号推送成功 但是短信推送失败
                                    satisfyPatientVo.setSendType(2); //推送微信消息成功
                                }
                            }

                        }
                        break;
                        case 2: {//公众号
                            String openId = satisfyPatientVo.getOpenId();
                            if (null == openId){
                                satisfyPatientVo.setSendType(3); //推送微信消息失败
                                log.info("患者的openId为空:{}",JSON.toJSONString(satisfyPatientVo));
                            }else{
                                int flag = sendWxMsg(null, null, wxSendDao, satisfyPlanVo, satisfyPatientVo, 2, accessToken);
                                if (flag == 3) {//accessToken失效 重新获取令牌
                                    accessToken = getAccessToken();//获取accessToken
                                    flag = sendWxMsg(null, null, wxSendDao, satisfyPlanVo, satisfyPatientVo, 2, accessToken);
                                }
                                if (flag ==2 ){//微信推送模板消息成功
                                    satisfyPatientVo.setSendType(2); //推送微信消息成功
                                }else{//推送模板消息失败
                                    satisfyPatientVo.setSendType(3); //推送微信消息失败
                                }
                            }
                        }
                        break;
                        case 3: {//短信
                            String param = "http://sf-system.leanin.com.cn/satisfied?planPatientId=" + satisfyPatientVo.getPatientSatisfyId() + "&planType=3&formNum=" + satisfyPlanVo.getSatisfyNum();
//                            Map map = CSMSUtils.sendMessage(msgText + param, satisfyPatientVo.getPatientPhone()); //satisfyPatientVo.getPatientPhone()
//                            String msgStatus = (String) map.get("msg");
                            String msgStatus = "true";
                            log.info("满意度短信：{}", msgText + param, satisfyPatientVo.getPatientPhone(), msgStatus, satisfyPlanVo.getSatisfyNum());
                            if (msgStatus.equals("true")) {
                                satisfyPatientVo.setSendType(2); //已发送短信
                            } else {
                                satisfyPatientVo.setSendType(3); //发送失败
                            }
                            addMsgRecord(satisfyPatientVo, msgText + param, satisfyPlanVo);
                        }
                        break;
                    }
                }
                satisfyPatientMapper.updateByPrimaryKeySelective(satisfyPatientVo);
            }
        }
    }


    @Scheduled(cron = "0 0/5 * * * ? ")
    @Transactional(rollbackFor = Exception.class)
    public void updateNextTime() {
        log.info("更新下次随访时间");
        List<PlanInfoDto> planInfoDtos = planInfoMapper.findAllPlan();
        for (PlanInfoDto planInfoDto : planInfoDtos) {

            //读取规则 解析规则
            RulesInfoVo rulesInfo = planInfoDto.getRulesInfo();
            if (planInfoDto.getPlanType() == 2 || rulesInfo.getRulesInfoTypeName() != 1) {//只更新随访
                continue;
            }
            String rulesInfoText = rulesInfo.getRulesInfoText();
            Map rulesMap = JSON.parseObject(rulesInfoText, Map.class);
//            String tiemFont = (String) rulesMap.get("tiemFont");//获取下次任务的时间 1天 2星期 3月
            int validDays = Integer.parseInt(rulesMap.get("validDays") + "");

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
            List<PlanPatientVo> planPatientList = planPatientMapper.findPlanPatientList(planInfoDto.getPlanNum(), null, null, null);
            if (planPatientList.size() == 0) {
                continue;
            }
            //判断是否完成随访或者已经过期
            for (PlanPatientVo planPatientVo : planPatientList) {
                Date nextDate = planPatientVo.getNextDate();//随访时间
                int days = (int) (System.currentTimeMillis() - nextDate.getTime()) / (1000 * 3600 * 24);//两个时间相差的天数
                if (days > validDays) {
                    updateRecord(planPatientVo.getPlanPatsStatus(), planPatientVo, rulesInfo, timeSelect,
                            timeChoosed, weeks, sendTimeDays, sendTimeMonths);
                }
                switch (planPatientVo.getPlanPatsStatus()) {
                    case -1: // -1 收案
                        break;
                    case 0: //未发送表单或者短信前的状态
                        //判断是否过期
                        if (days > validDays) {//过期
                            updateRecord(3, planPatientVo, rulesInfo, timeSelect,
                                    timeChoosed, weeks, sendTimeDays, sendTimeMonths);
                        }
                        break;
                    case 1: //1：待随访
                        //判断是否过期
                        if (days > validDays) {//过期
                            updateRecord(3, planPatientVo, rulesInfo, timeSelect,
                                    timeChoosed, weeks, sendTimeDays, sendTimeMonths);
                        }
                        break;
                    case 2: //已完成随访
                        //判断是否过期
                        if (days > validDays) {
                            updateRecord(2, planPatientVo, rulesInfo, timeSelect,
                                    timeChoosed, weeks, sendTimeDays, sendTimeMonths);
                        }
                        break;
                    case 3: //已过期随访
                        updateRecord(3, planPatientVo, rulesInfo, timeSelect,
                                timeChoosed, weeks, sendTimeDays, sendTimeMonths);
                        break;
                    default: //随访异常

                        break;

                }
            }
            log.info("一次计划更新完成{}", planInfoDto.getPlanNum());
        }

    }

    private boolean updateRecord(Integer status, PlanPatientVo planPatientVo, RulesInfoVo rulesInfo, String timeSelect,
                                 Integer timeChoosed, Integer weeks, Integer sendTimeDays, Integer sendTimeMonths) {
        //填写失访记录
        planPatientVo.setPlanPatsStatus(status); // 2 已完成随访 3 失访 4 随访异常
        followRecordMapper.addFollowRecord(planPatientVo);
        if (rulesInfo.getRulesInfoTypeName() == 1) {//阶段随访
            //计算下次随访时间
            Date date = setNextDate(new Date(), timeSelect, timeChoosed, weeks, sendTimeDays, sendTimeMonths);
            //复位
            planPatientVo.setNextDate(date); //设置下次随访时间
            planPatientVo.setSendType(1); //未发送
            planPatientVo.setPlanPatsStatus(0); //未发送状态
            planPatientVo.setFormStatus(1);     //表单填写 状态  修改为未填写
            planPatientVo.setHandleSugges("");
        }
        planPatientMapper.updatePlanPatient(planPatientVo);
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


    public String getAccessToken() {
        Map<String, String> param = new HashMap<>();
        String token_url = "https://api.weixin.qq.com/cgi-bin/token?" +
                "grant_type=client_credential&" +
                "appid=" + appId +
                "&secret=" + appSecret;
        //2.使用httpclient 发送请求
        String access_token = null;
        try {
            HttpClient httpClient = new HttpClient(token_url);
            httpClient.setHttps(true);//设置https 访问协议
            httpClient.get();//设置https 请求方式

            //3.接受返回来的数据
            String content = httpClient.getContent();//获取返回参数
            log.info("获取access_token时返回的数据:{}", content);
            Map map = JSON.parseObject(content, Map.class);
            Object access_token1 = map.get("access_token");
            if (access_token1 != null) {
                access_token = (String) access_token1;
            }

        } catch (Exception e) {
            log.info("获取access_token异常", e.getMessage());
        }
        return access_token;
//        body
        //发送链接

    }

    public int sendWxMsg(PlanInfoDto planInfo, PlanPatientVo patientDto, WxSendDao wxSendDao,
                         SatisfyPlanVo satisfyPlanVo, SatisfyPatientVo satisfyPatientVo, Integer type, String accessToken) {
        log.info("微信推送模板消息的患者信息:{}", JSON.toJSONString(patientDto));
        wxSendDao = new WxSendDao();
        if (type == 1) {//随访  宣教
            if (patientDto.getOpendId() == null && "".equals(patientDto.getOpendId())) {
                log.info("患者openid 为空");
                return 1;//openid 为空
            }
            wxSendDao.setId(null);//公众号发送记录主键
            String param = "";
            Map data = new HashMap();
            data.put("template_id", "nSZehuEC3QZpMWnBLn--IP85E7Z6lyIGAhSqfMFbTEc");
            wxSendDao.setTemplateId("nSZehuEC3QZpMWnBLn--IP85E7Z6lyIGAhSqfMFbTEc");     //公众号发送模板号
            data.put("touser", patientDto.getOpendId());
            wxSendDao.setOpenId(patientDto.getOpendId());   //公众号发送openid
            Map user = new HashMap();
            Map first = new HashMap();
            wxSendDao.setPlanPatientId(patientDto.getPatientPlanId()); //公众号发送计划患者主键
            wxSendDao.setPatientId(patientDto.getPatientId() + "");    //his患者主键
            wxSendDao.setFormId(planInfo.getFollowFormNum());          //发送表单id
            if (planInfo.getPlanType() == 1) {//随访
                param = "http://sf-system.leanin.com.cn/postlist?planPatientId=" + patientDto.getPatientPlanId() + "&planType=1&formNum=" + planInfo.getFollowFormNum();
                first.put("value", "您好！为了您的健康，请及时完成未提交的随访调查表单。");
                wxSendDao.setMsgTitle("您好！为了您的健康，请及时完成未提交的随访调查表单。"); //微信模板消息头
                wxSendDao.setPlanType(1);//发送计划类型 1 随访 2 宣教  3 满意度
            } else {//宣教
                param = "http://sf-system.leanin.com.cn/education?planPatientId=" + patientDto.getPatientPlanId() + "&planType=2&formNum=" + planInfo.getFollowFormNum();
                first.put("value", "您好！为了您的健康，请及时查看未读的宣教内容。");
                wxSendDao.setMsgTitle("您好！为了您的健康，请及时查看未读的宣教内容。");
                wxSendDao.setPlanType(2);//宣教
            }
            user.put("first", first);
            data.put("url", param);
            wxSendDao.setFormUrl(param);    //发送表单的链接
            Map keyword1 = new HashMap();
            keyword1.put("value", "建德第一人民医院");
            user.put("keyword1", keyword1);
            wxSendDao.setAreaCode("建德第一人民医院");   //医院名称
            Map keyword2 = new HashMap();
            keyword2.put("value", planInfo.getPlanWardCode());
            user.put("keyword2", keyword2);
            wxSendDao.setWardCode(planInfo.getPlanWardCode());//计划科室
            Map keyword3 = new HashMap();
            keyword3.put("value", patientDto.getPatientName());
            user.put("keyword3", keyword3);
            wxSendDao.setPatientName(patientDto.getPatientName()); //患者姓名
            Map remark = new HashMap();
            remark.put("value", "点击“详情”进行出院随访填写");
            user.put("remark", remark);
            wxSendDao.setMsgRemark("点击“详情”进行出院随访填写");  //模板结尾
            data.put("data", user);

            String dataStr = JSON.toJSONString(data);
            String url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=" + accessToken;
            String result = HttpClientUtil.doPostCarryJson(url, dataStr);
            log.info("推送模板消息是返回的信息:{}", result);
            Map map = JSON.parseObject(result, Map.class);

            Integer errcode = (Integer) map.get("errcode");
            wxSendDao.setErrorCode(errcode);   //错误代码
            String errmsg = (String) map.get("errmsg");
            wxSendDao.setErrmsg(errmsg);       //错误描述
            wxSendDao.setCreateTime(new Date());  // 发送时间
            wxSendDao.setPatientSource(patientDto.getPatientSource()); //患者来源
            wxSendDao.setPlanNum(planInfo.getPlanNum());        //计划主键
            wxSendDao.setNextDate(patientDto.getNextDate());    //计划发送时间
            wxSendDao.setPatientWard(patientDto.getPatientWard());//患者科室
            switch (errcode) {
                case 0: {//发送成功
                    wxSendDao.setSendStatus(2);
                    //(String)
                    wxSendDao.setMsgId(Long.parseLong( map.get("msgid").toString()));  //微信公众号发送成功的id
                    WxSendDao save = wxSendRepository.save(wxSendDao);
                    log.info("微信推送模板消息成功:{}", JSON.toJSONString(save));
                }
                return 2;//发送成功
                case 42001://accessToken 过期
                    log.info("accessToken过期");
//                wxSendRepository.save(wxSendDao);
                    return 3;//access_token 失效
                default://其他
                    wxSendDao.setSendStatus(3);
                    WxSendDao save = wxSendRepository.save(wxSendDao);
                    log.info("微信推送模板消息失败:{}", JSON.toJSONString(save));
                    return 4;//发送失败

            }
        } else {//满意度
            if (satisfyPatientVo.getOpenId() == null && "".equals(satisfyPatientVo.getOpenId())) {
                log.info("患者openid 为空");
                return 1;//openid 为空
            }
            wxSendDao.setId(null);
            String param = "";
            Map data = new HashMap();
            data.put("template_id", "nSZehuEC3QZpMWnBLn--IP85E7Z6lyIGAhSqfMFbTEc");
            wxSendDao.setTemplateId("nSZehuEC3QZpMWnBLn--IP85E7Z6lyIGAhSqfMFbTEc");
            data.put("touser", satisfyPatientVo.getOpenId());
            wxSendDao.setOpenId(satisfyPatientVo.getOpenId());
            Map user = new HashMap();
            Map first = new HashMap();
            wxSendDao.setPlanPatientId(satisfyPatientVo.getPatientSatisfyId());
            wxSendDao.setPatientId(satisfyPatientVo.getPatientId() + "");
            wxSendDao.setFormId(satisfyPatientVo.getFormId());
            param = "http://sf-system.leanin.com.cn/satisfied?planPatientId=" + satisfyPatientVo.getPatientSatisfyId() + "&planType=3&formNum=" + satisfyPlanVo.getSatisfyNum();
            first.put("value", "您好！为了更好地为您服务，请及时完成未提交的满意度调查表单。");
            wxSendDao.setMsgTitle("您好！为了更好地为您服务，请及时完成未提交的满意度调查表单。");
            wxSendDao.setPlanType(3);//满意度
            user.put("first", first);
            data.put("url", param);
            wxSendDao.setFormUrl(param);
            Map keyword1 = new HashMap();
            keyword1.put("value", "建德第一人民医院");
            user.put("keyword1", keyword1);
            wxSendDao.setAreaCode("建德第一人民医院");
            Map keyword2 = new HashMap();
            keyword2.put("value", satisfyPlanVo.getSatisfyPlanWard());
            user.put("keyword2", keyword2);
            wxSendDao.setWardCode(satisfyPlanVo.getSatisfyPlanWard());
            Map keyword3 = new HashMap();
            keyword3.put("value", satisfyPatientVo.getPatientName());
            user.put("keyword3", keyword3);
            wxSendDao.setPatientName(satisfyPatientVo.getPatientName());
            Map remark = new HashMap();
            remark.put("value", "点击进入院内关怀，填写并提交满意度表单");
            user.put("remark", remark);
            wxSendDao.setMsgRemark("点击进入院内关怀，填写并提交满意度表单");
            data.put("data", user);

            String dataStr = JSON.toJSONString(data);
            String url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=" + accessToken;
            String result = HttpClientUtil.doPostCarryJson(url, dataStr);
            log.info("推送模板消息是返回的信息:{}", result);
            Map map = JSON.parseObject(result, Map.class);

            Integer errcode = (Integer) map.get("errcode");
            wxSendDao.setErrorCode(errcode);
            String errmsg = (String) map.get("errmsg");
            wxSendDao.setErrmsg(errmsg);
            wxSendDao.setCreateTime(new Date());
            wxSendDao.setPatientSource(satisfyPatientVo.getPatientType()); //患者来源
            wxSendDao.setPlanNum(satisfyPlanVo.getPlanSatisfyNum());        //计划主键
            wxSendDao.setNextDate(satisfyPatientVo.getPatientDateTime());    //计划发送时间
            wxSendDao.setPatientWard(satisfyPatientVo.getPatientWard());//患者科室
            switch (errcode) {
                case 0: {//发送成功
                    wxSendDao.setSendStatus(2);
                    wxSendDao.setMsgId(Long.parseLong(map.get("msgid").toString()));
                    WxSendDao save = wxSendRepository.save(wxSendDao);
                    log.info("微信推送模板消息成功:{}", JSON.toJSONString(save));
                }
                return 2;//发送成功
                case 42001://accessToken 过期
                    log.info("accessToken过期");
//                wxSendRepository.save(wxSendDao);
                    return 3;//access_token 失效
                default://其他
                    wxSendDao.setSendStatus(3);
                    WxSendDao save = wxSendRepository.save(wxSendDao);
                    log.info("微信推送模板消息失败:{}", JSON.toJSONString(save));
                    return 4;//发送失败

            }
        }
    }

    //满意度发送短信
    private void addMsgRecord(SatisfyPatientVo satisfyPatientVo, String msgContent, SatisfyPlanVo satisfyPlanVo) {
        MessageRecord messageRecord = new MessageRecord();
        messageRecord.setMsgSendId(null);//主键自增
        messageRecord.setMsgSendName(0L);// 0 表示系统自动发送
        messageRecord.setMsgSendWard(satisfyPlanVo.getSatisfyPlanWard());//计划负责科室
        messageRecord.setMsgSendDate(new Date());
        messageRecord.setMsgSendNum(satisfyPatientVo.getPatientPhone());//发送手机号
        messageRecord.setMsgText(msgContent);//发送内容
        messageRecord.setMsgSendStatus(satisfyPatientVo.getSendType());//发送状态  2 发送成功  3 发送失败
        messageRecord.setMsgThem(null);//短信主题
        messageRecord.setPlanType(3);//计划类型  1 随访 2 宣教 3 满意度 4 短信主题  5 自定义短信
        messageRecord.setPlanPatientId(satisfyPatientVo.getPatientSatisfyId());//计划患者主键
        messageRecord.setPatientId(satisfyPatientVo.getPatientId() + "");//his 患者主键
        messageRecord.setPlanNum(satisfyPatientVo.getSatisfyPlanNum());//短信主题 主键
        messageRecord.setFormId(satisfyPatientVo.getFormId());//表单主键
        messageRecord.setPatientWard(satisfyPatientVo.getPatientWard()); //患者科室
        messageRecord.setPatientSource(satisfyPatientVo.getPatientType());  //患者来源
        messageRecord.setNextDate(satisfyPatientVo.getPatientDateTime());//计划患者发送时间
        MessageRecord save = messageRecordRepository.save(messageRecord);
        log.info("满意度计划发送短信:{}", JSON.toJSONString(messageRecord));
    }

    /*private Map setParam(Map data, Map user, Map first, Map keyword1,
                         Map keyword2, Map keyword3, Map remark) {
        Map data = new HashMap();
        data.put("touser", "openid");
        data.put("template_id", "12mvbkAmJmSPYM7op_Y5sDpbZfFWjmh3SPKxmYTrhmk");
        data.put("url", "www.baidu.com");
        Map user =new HashMap();
        Map first = new HashMap();
        first.put("value", "为了您的身体健康，请及时填写出院后的随访信息");
        user.put("first", first);
        Map keyword1 = new HashMap();
        keyword1.put("value", "9527");
        user.put("keyword1", keyword1);
        Map keyword2 = new HashMap();
        keyword2.put("value", "2019-3-14");
        user.put("keyword2", keyword2);
        Map keyword3 = new HashMap();
        keyword3.put("value", "建德人民医院");
        user.put("keyword3", keyword3);
        Map remark = new HashMap();
        remark.put("value", "点击“详情”进行出院随访填写");
        user.put("remark", remark);
        data.put("data", user);
        return data;
    }*/

}
