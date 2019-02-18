package com.leanin.task;

import com.alibaba.fastjson.JSON;
import com.leanin.config.RabbitMQConfig;
import com.leanin.domain.dto.PlanInfoDto;
import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.vo.PlanInfoVo;
import com.leanin.domain.vo.PlanPatientVo;
import com.leanin.feign.PlanPatientFeign;
//import com.leanin.mq.config.RabbitMQConfig;
import com.leanin.mapper.PlanInfoMapper;
import com.leanin.mapper.PlanPatientMapper;
import com.leanin.utils.CSMSUtils;
import com.leanin.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 定时发送消息
 *
 * @author Administrator
 */
@DisallowConcurrentExecution
@Slf4j
public class PlanJobTask implements Job {

//    @Autowired
//    RabbitTemplate rabbitTemplate;
//
//    @Autowired
//    PlanPatientFeign planPatientFeign;

    @Autowired
    PlanInfoMapper planInfoMapper;

    @Autowired
    PlanPatientMapper planPatientMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("开始推送消息");
//        DataOutResponse planJson=planPatientFeign.findAllPlan();
//
//        List<PlanInfoDto> planList = (List<PlanInfoDto>) planJson.getData();
        //查询计划列表信息
        List<PlanInfoDto> planList = planInfoMapper.findAllPlan();
//        List<PlanInfoDto> planList= JsonUtil.json2list(planJson.getData().toString(),PlanInfoDto.class);
        log.info("计划信息列表为:" + JSON.toJSONString(planList));
        for (PlanInfoDto planInfo : planList) {
//            DataOutResponse planPatientJson=planPatientFeign.findListByPlanId(planInfo.getPlanNum());

//            List<PlanPatientVo> planPatientList = (List<PlanPatientVo>) planPatientJson.getData();
//            List<PlanPatientVo> planPatientList=JsonUtil.json2list(planPatientJson.getData().toString(),PlanPatientVo.class);
            //根据病人的编号查询计划病人信息
            List<PlanPatientVo> planPatientList = planPatientMapper.findPlanPatientList(planInfo.getPlanNum(), 0, null);
            log.info("该计划的病人列表信息为:" + JSON.toJSONString(planPatientList));
            for (PlanPatientVo patientDto : planPatientList) {
                //如果当前时间大于病人随访时间，进行发送消息
                if (System.currentTimeMillis() > patientDto.getNextDate().getTime()
                        && patientDto.getSendType() == 1 && patientDto.getPlanPatsStatus() == 0) {
//                    if (patientDto.getSendType() == 1 && patientDto.getPlanPatsStatus() == 0) {//未发送而且是预随访的执行 发送操作
                    switch (planInfo.getPlanSendType()) {
                        //短信
                        case 1:
                            String msg = planInfo.getMsgInfo().getMsgText() + "表单的URL";
                            log.info("发送的短信内容为:" + msg);
                            //将病人的手机号码以逗号隔开进行发送 planPatientList.stream().map(PlanPatientDto::getPatientPhone).collect(Collectors.joining(","))
                            Map map = CSMSUtils.sendMessage(msg, "18556531536");//patientDto.getPatientPhone()
                            //设置病人发送状态
                            String msgStatus = (String) map.get("msg");
//                            patientDto.setSendStatus(map.get("msg").toString());
                            if (msgStatus.equals("true")) {
                                patientDto.setSendType(2); //发送成功
                                patientDto.setPlanPatsStatus(1); //修改成待随访状态
                            } else {
                                patientDto.setSendType(3);//发送失败
                            }
                            break;
                        //微信公众号
                        case 2:
                            break;
                    }
                    planPatientMapper.updatePlanPatient(patientDto);
                }


            }
        }
        //发送信息的用户信息放到消息队列当中
//            rabbitTemplate.convertAndSend(RabbitMQConfig.Exchange_NAME_SEND,RabbitMQConfig.EX_ROUTING_SEND,planPatientList);
    }
}
