package com.leanin.schdule.schedule;


import com.alibaba.fastjson.JSON;
import com.leanin.domain.vo.MsgInfoVo;
import com.leanin.domain.vo.SatisfyPatientVo;
import com.leanin.domain.vo.SatisfyPlanVo;
import com.leanin.schdule.mapper.MsgInfoMapper;
import com.leanin.schdule.mapper.SatisfyPatientMapper;
import com.leanin.schdule.mapper.SatisfyPlanMapper;
import com.leanin.utils.CSMSUtils;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

//满意度定时任务
@DisallowConcurrentExecution
@Slf4j
public class STYPlanTask implements Job {

    @Autowired
    SatisfyPlanMapper satisfyPlanMapper;

    @Autowired
    SatisfyPatientMapper satisfyPatientMapper;

    @Autowired
    MsgInfoMapper msgInfoMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
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
            int days = (int) ((System.currentTimeMillis() - satisfyPatientVo.getPatientDateTime().getTime()) / (60 * 3600 * 24));
            if (days > rangeDays){//判断是否过期
                satisfyPatientVo.setFinishType(3);//已过期
            }else{
                if (satisfyPatientVo.getSendType() == 1){//未发送
                    Map map = CSMSUtils.sendMessage(msgText, satisfyPatientVo.getPatientPhone());
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
