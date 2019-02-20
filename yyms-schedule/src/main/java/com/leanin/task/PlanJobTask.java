package com.leanin.task;

import com.alibaba.fastjson.JSON;
import com.leanin.config.RabbitMQConfig;
import com.leanin.domain.dto.PlanInfoDto;
import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.vo.MsgInfoVo;
import com.leanin.domain.vo.PlanInfoVo;
import com.leanin.domain.vo.PlanPatientVo;
import com.leanin.feign.PlanPatientFeign;
//import com.leanin.mq.config.RabbitMQConfig;
import com.leanin.mapper.MsgInfoMapper;
import com.leanin.mapper.PlanInfoMapper;
import com.leanin.mapper.PlanPatientMapper;
import com.leanin.utils.CSMSUtils;
import com.leanin.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.naming.Name;
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

    @Autowired
    PlanInfoMapper planInfoMapper;

    @Autowired
    PlanPatientMapper planPatientMapper;

    @Autowired
    MsgInfoMapper msgInfoMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
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
        }
//        System.out.println("热舞执行了  啊啊啊啊啊啊啊啊啊");
    }

    private void sendMessage(PlanInfoDto planInfo, PlanPatientVo patientDto) {
        String msg = planInfo.getMsgInfo().getMsgText() + "表单的URL";
        log.info("发送的短信内容为:" + msg);
        //将病人的手机号码以逗号隔开进行发送 planPatientList.stream().map(PlanPatientDto::getPatientPhone).collect(Collectors.joining(","))
        Map map = CSMSUtils.sendMessage(msg, "18556531536");//patientDto.getPatientPhone()
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
}
