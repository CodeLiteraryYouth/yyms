package com.leanin.schdule.task;

import com.alibaba.fastjson.JSON;
import com.leanin.domain.vo.PlanInfoVo;
import com.leanin.domain.vo.SatisfyPlanVo;
import com.leanin.schdule.config.RabbitMQConfig;
import com.leanin.schdule.feign.ManagerPatientClient;
import com.leanin.schdule.mapper.PlanInfoMapper;
import com.leanin.schdule.mapper.SatisfyPlanMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author CPJ.
 * @date 2019/5/22.
 * @time 17:19.
 */
@Component
@Slf4j
public class ImportPlanPatient {


    @Autowired
    PlanInfoMapper planInfoMapper;

    @Autowired
    SatisfyPlanMapper satisfyPlanMapper;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    ManagerPatientClient managerPatientClient;



    //随访  宣教 计划导入患者
    @Scheduled(cron = "0 0 0 * * ? *")//每日 0:00:00  执行
    @Transactional(rollbackFor = Exception.class)
    public void importFollowPatient(){
        Date endDate = new Date();
        Calendar calendar =Calendar.getInstance();
        calendar.setTime(endDate);
        calendar.add(Calendar.DAY_OF_YEAR,-1);
        Date startDate = calendar.getTime();
        List<PlanInfoVo> PlanInfoVos = planInfoMapper.findByImportData(1);//1 导入未完成  2 导入完成
        for (PlanInfoVo planResult : PlanInfoVos) {
            Map paramMap =new HashMap();
            String patsWardCode = planResult.getPatsWardCode();
            if (patsWardCode != null){
                List<String> patsWardCodeList = JSON.parseArray(patsWardCode, String.class);
                paramMap.put("patsWardCode", patsWardCodeList);// 患者随访科室编码 可能是集合
            }

            //疾病集合
            String diseaseCode = planResult.getDiseaseCode();
            if (diseaseCode != null && !"[]".equals(diseaseCode)){
                List<String> diseaseCodeList = JSON.parseArray(diseaseCode, String.class);
                paramMap.put("diseaseCode", diseaseCodeList);//疾病编码
            }

            if (planResult.getPlanSex() != 3 ){
                paramMap.put("planSex", planResult.getPlanSex());//病人性别 1男 2女
            }
            if (planResult.getPlanBeginTime() != null){
                paramMap.put("beginDate", startDate);//开始区间
            }
            if (planResult.getPlanEndTime() != null){
                paramMap.put("endDate", endDate);//结束区间
            }
            String planAgeInterval = planResult.getPlanAgeInterval();//年龄区间
            if (planAgeInterval != null && planAgeInterval.contains(",")) {//年龄区间
                String[] split = planAgeInterval.split(",");
                paramMap.put("startAge", split[0]);
                paramMap.put("endAge", split[1]);
            }
            if (planResult.getPlanExisPhone() != null){
                paramMap.put("planExisPhone",planResult.getPlanExisPhone());//有无联系方式 1有 2无
            }
            //判断病人来源 (1:出院 2：门诊 3：在院 4：体检 5：建档)
            switch (planResult.getPatientInfoSource()) {
                case 1: {//出院
                    paramMap.put("inOut", 2);// 1在院  2出院
                    Map datamap = managerPatientClient.findInHosPatientByParamToSF(paramMap);
                    //调用服务发生异常
                    Object error = datamap.get("error");
                    if ((!"".equals(error) && error !=null) || datamap == null){
                        log.info("调取患者信息异常的计划:{}",JSON.toJSONString(planResult));
                        continue;
//                        ExceptionCast.cast(CommonCode.FEIGN_ERROR);
//                    return ReturnFomart.retParam(1006, "服务器网络异常，请联系管理员");
                    }
//                Object o = datamap.get("list");
                    if (datamap != null) {
                        List<Map> list = (List<Map>) datamap.get("list");
                        redisTemplate.boundHashOps("plan").put(planResult.getPlanNum(), list);
                    }
                }
                break;
                case 2: {//门诊
//                int i = 0;
//                Map paramMap = new HashMap();
                    Map datamap = null;
                    datamap = managerPatientClient.findOutHosPatientByParamToSF(paramMap);
                    //调用服务发生异常
                    Object error = datamap.get("error");
                    if ((!"".equals(error) && error !=null) || datamap == null){
                        log.info("调取患者信息异常的计划:{}",JSON.toJSONString(planResult));
                        continue;
//                    return ReturnFomart.retParam(1006, "服务器网络异常，请联系管理员");
//                        ExceptionCast.cast(CommonCode.FEIGN_ERROR);
                    }
                    //将查询出来的数据 存到缓存中
                    if (datamap != null) {
                        List<Map> list = (List<Map>) datamap.get("list");
                        redisTemplate.boundHashOps("plan").put(planResult.getPlanNum(), list);
                    }
                }
                break;
                case 3: {//在院
                    Map datamap = null;
                    paramMap.put("inOut", 1);// 1在院  2出院
                    datamap = managerPatientClient.findInHosPatientByParamToSF(paramMap);
                    //调用服务发生异常
                    Object error = datamap.get("error");
                    if ((!"".equals(error) && error !=null) || datamap == null){
//                    return ReturnFomart.retParam(1006, "服务器网络异常，请联系管理员");
                        log.info("调取患者信息异常的计划:{}",JSON.toJSONString(planResult));
                        continue;
//                        ExceptionCast.cast(CommonCode.FEIGN_ERROR);
                    }
                    if (datamap != null) {
                        List<Map> list = (List<Map>) datamap.get("list");
                        redisTemplate.boundHashOps("plan").put(planResult.getPlanNum(), list);
                    }
                }
                break;
                case 4: {//体检

                }
                break;
                case 5: {//建档

                }
                break;
                default: {

                }
                break;
            }
            //发送消息   参数1 交换机  参数2 路由地址  参数3 需要发送的数据
            rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.EX_ROUTING_PLAN_PATIENT,planResult.getPlanNum());
            log.info("rabbitMQ传递的随访/宣教计划:{}",planResult.getPlanNum());
            int i = startDate.compareTo(planResult.getPlanEndTime());// -1 小于  0 等于 2 大于
            if (i != -1){
                planInfoMapper.updateImportData(planResult.getPlanNum(),2);
                log.info("患者信息导入完成的随访/宣教计划",planResult.getPlanNum());
            }
        }
    }


    //随访  宣教 计划导入患者
    @Scheduled(cron = "0 0 0 * * ? *")//每日 0:00:00  执行
    @Transactional(rollbackFor = Exception.class)
    public void importSatisfyPatient(){
        Date endDate = new Date();
        Calendar calendar =Calendar.getInstance();
        calendar.setTime(endDate);
        calendar.add(Calendar.DAY_OF_YEAR,-1);
        Date startDate = calendar.getTime();
        List<SatisfyPlanVo> satisfyInfoVos = satisfyPlanMapper.findByImportData(1);
        for (SatisfyPlanVo satisfyPlan : satisfyInfoVos) {
            // 封装参数
            Map paramMap = new HashMap();
            //科室集合
            String patsWardCode = satisfyPlan.getPatientWard();
            if (patsWardCode != null && !"[]".equals(patsWardCode)){
                List<String> patsWardCodeList =JSON.parseArray(patsWardCode, String.class);
                paramMap.put("patsWardCode", patsWardCodeList);// 患者随访科室编码 可能是集合
            }
            //疾病集合
            String diseaseCode = satisfyPlan.getDiseaseName();
            if (diseaseCode != null && !"[]".equals(diseaseCode)){
                List<String> diseaseCodeList =JSON.parseArray(diseaseCode, String.class);
                paramMap.put("diseaseCode", diseaseCodeList);//疾病编码
            }
            if (satisfyPlan.getPatientSex() != 3){
                paramMap.put("planSex", satisfyPlan.getPatientSex());//病人性别 1男 2女
            }
            if (satisfyPlan.getPlanSatisfyBeginDate() != null){
                paramMap.put("beginDate", startDate);//开始区间
            }
            if (satisfyPlan.getPlanSatisfyEndDate() != null){
                paramMap.put("endDate", endDate);//结束区间
            }

            String planAgeInterval = satisfyPlan.getPatientAge();//年龄区间
            if (planAgeInterval != null && planAgeInterval.contains(",")) {//年龄区间
                String[] split = planAgeInterval.split(",");
                paramMap.put("startAge", split[0]);
                paramMap.put("endAge", split[1]);
            }
            if (satisfyPlan.getPatientType() != null){
                paramMap.put("planExisPhone", satisfyPlan.getPatientType());//有无联系方式 1有 2无
            }
            //判断病人来源 (1:出院 2：门诊 3：在院 4：体检 5：建档)
            switch (satisfyPlan.getPatientType()) {
                case 1: {//出院
                    paramMap.put("inOut", 2);// 1在院  2出院
                    Map datamap = managerPatientClient.findInHosPatientByParamToSF(paramMap);
                    //调用服务发生异常
                    Object error = datamap.get("error");
                    if ((!"".equals(error) && error != null) || datamap == null) {
                        log.info("调取患者信息异常的计划:{}",JSON.toJSONString(satisfyPlan));
                        continue;
//                        ExceptionCast.cast(CommonCode.FEIGN_ERROR);
                    }
                    if (datamap != null) {
                        List<Map> list = (List<Map>) datamap.get("list");
                        redisTemplate.boundHashOps("satisfyPlan").put(satisfyPlan.getPlanSatisfyNum(), list);
                    }
                }
                break;
                case 2: {//门诊
                    Map datamap = null;
                    datamap = managerPatientClient.findOutHosPatientByParamToSF(paramMap);
                    //调用服务发生异常
                    Object error = datamap.get("error");
                    if ((!"".equals(error) && error != null) || datamap == null) {
                        log.info("调取患者信息异常的计划:{}",JSON.toJSONString(satisfyPlan));
                        continue;
//                        ExceptionCast.cast(CommonCode.FEIGN_ERROR);
                    }
                    //将查询出来的数据 存到缓存中
//                Object o = datamap.get("list");
                    if (datamap != null) {
                        List<Map> list = (List<Map>) datamap.get("list");
                        redisTemplate.boundHashOps("satisfyPlan").put(satisfyPlan.getPlanSatisfyNum(), list);
                    }
                }
                break;
                case 3: {//在院
//                int i = 0;
//                Map paramMap = new HashMap();
                    Map datamap = null;
                    paramMap.put("inOut", 1);// 1在院  2出院
                    datamap = managerPatientClient.findInHosPatientByParamToSF(paramMap);
                    //调用服务发生异常
                    Object error = datamap.get("error");
                    if ((!"".equals(error) && error != null)|| datamap == null) {
                        log.info("调取患者信息异常的计划:{}",JSON.toJSONString(satisfyPlan));
                        continue;
//                        ExceptionCast.cast(CommonCode.FEIGN_ERROR);
                    }
                    if (datamap != null) {
                        List<Map> list = (List<Map>) datamap.get("list");
                        redisTemplate.boundHashOps("satisfyPlan").put(satisfyPlan.getPlanSatisfyNum(), list);
                    }
                }
                break;
                case 4: {//体检

                }
                break;
                case 5: {//建档

                }
                break;
                default: {

                }
                break;
            }
            //发送消息   参数1 交换机  参数2 路由地址  参数3 需要发送的数据
            rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME_SP, RabbitMQConfig.EX_ROUTING_SATISFY_PLAN, satisfyPlan.getPlanSatisfyNum());
            log.info("rabbitMQ 传递的满意度计划：{}", satisfyPlan.getPlanSatisfyNum());
            int i = startDate.compareTo(satisfyPlan.getPlanSatisfyEndDate());
            if(i != -1){//开始时间大于 随访完成时间
                satisfyPlanMapper.updateImportData(satisfyPlan.getPlanSatisfyNum(),2);
                log.info("满意度计划导入患者信息完成：{}", satisfyPlan.getPlanSatisfyNum());
            }
        }


    }
}
