package com.leanin.schdule.task;

import com.alibaba.fastjson.JSON;
import com.leanin.domain.dto.PlanInfoDto;
import com.leanin.domain.vo.*;
import com.leanin.schdule.mapper.*;
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

    @Value("wx.appId")
    private String appId;

    @Value("wx.appSecret")
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
    //13817165550
    //短信主题发送短信
    @Scheduled(cron = "0 0/3 * * * ? ")
    @Transactional(rollbackFor = Exception.class)
    public void messagePlan(){
        List<MessageTopicVo> messageTopicVos = messageTopicMapper.findMsgTopicList(null);
        for (MessageTopicVo messageTopicVo : messageTopicVos) {
            List<MessagePatientVo> messagePatientVos = messagePatientMapper.findList(null, 1);
            if (messagePatientVos.size() == 0){
                continue;
            }
            for (MessagePatientVo messagePatientVo : messagePatientVos) {
                String content=messageTopicVo.getMsgTopicHead() + messageTopicVo.getMsgContent();
                Map map = CSMSUtils.sendMessage(content,"13817165550"
                        /*messagePatientVo.getPatientPhone()*/);
                String msgStatus = (String) map.get("msg");
                log.info("发送的内容和号码：{}",content,messagePatientVo.getPatientPhone(),msgStatus);
                if (msgStatus.equals("true")){
                    messagePatientVo.setSendType(2);//发送成功
                }else{
                    messagePatientVo.setSendType(3);//发送失败
                }
                messagePatientMapper.updateByPrimaryKeySelective(messagePatientVo);///*messageTopicVo.getMsgTopicCreater()*/
                msgRecordMapper.addMsgRecord(new MessageRecord(null,0l,messageTopicVo.getMsgTopicCreaterWard(),
                        new Date(),messagePatientVo.getPatientPhone(),content,messagePatientVo.getSendType(),messageTopicVo.getMsgTopicTitle(),4,messagePatientVo.getPatientMsgId(),messagePatientVo.getPatientId()+"",
                        messageTopicVo.getMsgTopicId(),messageTopicVo.getMsgTopicId()));
            }

        }
    }

    //随访 宣教 发送短信
    @Scheduled(cron = "0 0/2 * * * ? ")
    @Transactional(rollbackFor = Exception.class)
    public void followPlan() {
        log.info("开始推送消息");
        //查询计划列表信息
        List<PlanInfoDto> planList = planInfoMapper.findAllPlan();
        log.info("计划信息列表为:" + JSON.toJSONString(planList));
        for (PlanInfoDto planInfo : planList) {
            log.info("随访/满意度计划信息:{}",JSON.toJSONString(planInfo));
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
                        //短信和公众号
                        case 1:
                            sendMessage(planInfo, patientDto);
                            break;
                        //微信公众号
                        case 2:

                            break;
                        case 3://短信
                            sendMessage(planInfo, patientDto);
                            break;
                    }
                }
            }
            log.info("一个计划推送完毕");
        }
    }

    private void sendMessage(PlanInfoDto planInfo, PlanPatientVo patientDto) {
        String msg = planInfo.getMsgInfo().getMsgText();
        log.info("发送的短信内容为:" + msg);
        //将病人的手机号码以逗号隔开进行发送 planPatientList.stream().map(PlanPatientDto::getPatientPhone).collect(Collectors.joining(","))
        String param = "";
        if (planInfo.getPlanType() == 1){//随访
            param = "http://sf-system.leanin.com.cn/login#/postlist?planPatientId="+patientDto.getPatientPlanId()+"&palnType=1&formNum="+planInfo.getFollowFormNum();
        }else{//宣教
            param = "http://sf-system.leanin.com.cn/login#/education?planPatientId="+patientDto.getPatientPlanId()+"&palnType=2&formNum="+planInfo.getFollowFormNum();
        }
        Map map = CSMSUtils.sendMessage(msg+param, "13817165550");//patientDto.getPatientPhone()
        //设置病人发送状态
        String msgStatus = (String) map.get("msg");
        log.info("随访/宣教短信，短信内容，患者手机号，发送状态：{}",msg+param,patientDto.getPatientPhone(),msgStatus);
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
        msgRecordMapper.addMsgRecord(new MessageRecord(null,/*planInfo.getPlanDutyPer()*/0l,planInfo.getPlanWardCode(),new Date(),
                patientDto.getPatientPhone(),msg,patientDto.getSendType(),null,planInfo.getPlanType(),patientDto.getPatientPlanId(),
                patientDto.getPatientId()+"",patientDto.getFormId(),patientDto.getPlanNum()));
        planPatientMapper.updatePlanPatient(patientDto);
    }

    //满意度计划
    @Scheduled(cron = "0 0/5 * * * ? ")//
    @Transactional(rollbackFor = Exception.class)
    public void styPlan(){
        //查询所有计划信息
        List<SatisfyPlanVo> planVos = satisfyPlanMapper.findList();
        for (SatisfyPlanVo planVo : planVos) {
            log.info("满意度计划信息：{}",JSON.toJSONString(planVo));
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
                case 1 : //短信或者公众号
                    sendMsg(planVo,list,msgInfo.getMsgText(),rangeDays);
                    break;
                case 2 : //公众号
                    break;
                case 3 : //短信
                    //执行相关发送操作
                    sendMsg(planVo,list,msgInfo.getMsgText(),rangeDays);
                    break;
            }
        }
    }

    //发送短信操作  修改数据状态
    private void sendMsg(SatisfyPlanVo satisfyPlanVo,List<SatisfyPatientVo> list,String msgText,Integer rangeDays){
        //判断是否过期
        for (SatisfyPatientVo satisfyPatientVo : list) {
            int days = (int) ((System.currentTimeMillis() - satisfyPatientVo.getPatientDateTime().getTime()) / (1000*3600 * 24));
            if (System.currentTimeMillis() - satisfyPatientVo.getPatientDateTime().getTime() > 0){
                if (days > rangeDays){//判断是否过期
                    if (satisfyPatientVo.getFinishType() == 1 ){
                        satisfyPatientVo.setFinishType(3);//已过期
                    }
                }else{
                    if (satisfyPatientVo.getSendType() == 1){//未发送
                        String param = "http://sf-system.leanin.com.cn/login#/satisfied?planPatientId="+satisfyPatientVo.getPatientSatisfyId()+"&palnType=3&formNum="+satisfyPlanVo.getSatisfyNum();
                        Map map = CSMSUtils.sendMessage(msgText+param, "13817165550"); //satisfyPatientVo.getPatientPhone()
                        String msgStatus = (String) map.get("msg");
                        log.info("满意度短信：{}",msgText+param,satisfyPatientVo.getPatientPhone(),msgStatus,satisfyPlanVo.getSatisfyNum());
                        if (msgStatus.equals("true")){
                            satisfyPatientVo.setSendType(2); //已发送短信
                        }else{
                            satisfyPatientVo.setSendType(3); //发送失败
                        }
                        msgRecordMapper.addMsgRecord(new MessageRecord(null,/*satisfyPlanVo.getDiscoverPerson()*/0l,satisfyPlanVo.getSatisfyPlanWard(),new Date(),
                                satisfyPatientVo.getPatientPhone(),msgText,satisfyPatientVo.getSendType(),null,3,satisfyPatientVo.getPatientSatisfyId(),
                                satisfyPatientVo.getPatientId()+"",satisfyPatientVo.getFormId(),satisfyPlanVo.getPlanSatisfyName()));
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
                        if (days > validDays){
                            updateRecord(2, planPatientVo, rulesInfo, timeSelect,
                                    timeChoosed, weeks, sendTimeDays, sendTimeMonths);
                        }
                        break;
                    case 3: //已过期随访
                            updateRecord(3, planPatientVo, rulesInfo, timeSelect,
                                    timeChoosed, weeks, sendTimeDays, sendTimeMonths);
                        break;
                    case 4: //随访异常
                        if (days > validDays){
                            updateRecord(4, planPatientVo, rulesInfo, timeSelect,
                                    timeChoosed, weeks, sendTimeDays, sendTimeMonths);
                        }
                        break;
                }
            }
            log.info("一次计划更新完成{}",planInfoDto.getPlanNum());
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


    public String getAccessToken(){
        Map<String,String> param=new HashMap<>();
        String token_url="https://api.weixin.qq.com/cgi-bin/token?" +
                "grant_type=client_credential&" +
                "appid=" +appId+
                "&secret="+appSecret;
        //2.使用httpclient 发送请求
        String access_token = null;
        try {
            HttpClient httpClient =new HttpClient(token_url);
            httpClient.setHttps(true);//设置https 访问协议
            httpClient.get();//设置https 请求方式

            //3.接受返回来的数据
            String content = httpClient.getContent();//获取返回参数
            Map map = JSON.parseObject(content, Map.class);
            access_token = (String) map.get("access_token");
        } catch (Exception e) {
            log.info("获取access_token异常");
        }
        return access_token;
//        body
        //发送链接

//        String sendMsgUrl="https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+access_token;
//        String templeteId="WVatT8Lq64rB81gEYnnfNw_ulCAgJkri5iyi_FPVRrc";
//        String openId = "o0XfE5Mbr0YAyMlvyjxeapM6IvvY";
//        param.put("touser",openId);//openid
//        param.put("template_id",templeteId);
    }

    public void sendWxMsg(){
        String accessToken = getAccessToken();
        String url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+accessToken;
        Map data = new HashMap();
        data.put("touser","openid");
        data.put("template_id","12mvbkAmJmSPYM7op_Y5sDpbZfFWjmh3SPKxmYTrhmk");
        data.put("url","www.baidu.com");
        Map user =new HashMap();
        Map first = new HashMap();
        first.put("value","为了您的身体健康，请及时填写出院后的随访信息");
        user.put("first",first);
        Map keyword1 = new HashMap();
        keyword1.put("value","9527");
        user.put("keyword1",keyword1);
        Map keyword2 = new HashMap();
        keyword2.put("value","2019-3-14");
        user.put("keyword2",keyword2);
        Map keyword3 = new HashMap();
        keyword3.put("value","建德人民医院");
        user.put("keyword3",keyword3);
        Map remark = new HashMap();
        remark.put("value","点击“详情”进行出院随访填写");
        user.put("remark",remark);
        data.put("data",user);
        String dataStr = JSON.toJSONString(data);
        String result = HttpClientUtil.doPostCarryJson(url, dataStr);

        Map map = JSON.parseObject(result, Map.class);

    }

}
