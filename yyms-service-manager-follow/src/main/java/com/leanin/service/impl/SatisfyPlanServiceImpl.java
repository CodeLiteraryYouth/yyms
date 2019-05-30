package com.leanin.service.impl;

import java.util.*;

import com.leanin.client.ManagerPatientClient;
import com.leanin.config.RabbitMQConfig;
import com.leanin.domain.plan.response.PlanResponseCode;
import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.response.ReturnFomart;
import com.leanin.domain.vo.SatisfyPatientVo;
import com.leanin.domain.vo.SatisfyPlanVo;
import com.leanin.exception.ExceptionCast;
import com.leanin.mapper.SatisfyPatientMapper;
import com.leanin.model.response.CommonCode;
import com.leanin.utils.CompareUtil;
import com.leanin.utils.UUIDUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leanin.mapper.SatisfyPlanMapper;
import com.leanin.service.SatisfyPlanService;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Administrator
 */
@Service
@Slf4j
public class SatisfyPlanServiceImpl implements SatisfyPlanService {

    @Autowired
    SatisfyPlanMapper satisfyPlanMapper;

    @Autowired
    ManagerPatientClient managerPatientClient;

    @Autowired
    SatisfyPatientMapper satisfyPatientMapper;

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Override
    public DataOutResponse findSatisfyPlanList(int page, int pageSize, String satisfyPlanName) {
        log.info("搜索的满意度计划名称为:" + satisfyPlanName);
        PageHelper.startPage(page, pageSize);
        List<SatisfyPlanVo> satisfyPlanList = satisfyPlanMapper.findSatisfyPlanList(satisfyPlanName);
        PageInfo pageInfo = new PageInfo<>(satisfyPlanList);
        return ReturnFomart.retParam(200, pageInfo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DataOutResponse updateSatisfyStatus(String planSatisfyNum, int status) {
        log.info("满意度表单号为:" + planSatisfyNum + "-" + "状态为:" + status);
        if (status == -1){//如果是删除
            SatisfyPatientVo satisfyPatientVo = satisfyPatientMapper.findByPlanNumAndSendType(planSatisfyNum,1);
            if (satisfyPatientVo != null){//满意度计划内有已经发送过表单的患者则不能删除
                return ReturnFomart.retParam(5600,planSatisfyNum);
            }
        }
        SatisfyPlanVo satisfyPlan = satisfyPlanMapper.findSatisfyPlanById(planSatisfyNum);
        if(satisfyPlan ==null){
            return ReturnFomart.retParam(300, "计划不存在");
        }
        satisfyPlanMapper.updateSatisfyStatus(planSatisfyNum, status);
        log.info("修改满意度计划的信息为:" + JSON.toJSONString(satisfyPlan));
        return ReturnFomart.retParam(200, "操作成功");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DataOutResponse addSatisfyPlan(SatisfyPlanVo record) {
        log.info("增加的满意度计划信息为:" + JSON.toJSONString(record));
//        record.setPlanSatisfyNum(UUIDUtils.getUUID());
//        SatisfyPlanVo satisfyPlanPlanVo = satisfyPlanMapper.findSatisfyPlanByName(record.getPlanSatisfyName());
        SatisfyPlanVo satisfyPlanPlanVo = satisfyPlanMapper.findSatisfyPlanById(record.getPlanSatisfyNum());
        if (CompareUtil.isNotEmpty(satisfyPlanPlanVo)) {
            //如果已经存在该信息,请勿重复添加
            ExceptionCast.cast(PlanResponseCode.INVALID_PARAM);
        }
        if (record.getPlanSatisfyEndDate().getTime() < new Date().getTime()){//结束时间小于当前时间 计划导入患者完成
            record.setImportData(2);
        }else{//结束时间大于当前时间 导入计划未完成
            record.setImportData(1);
        }
        satisfyPlanMapper.addSatisfyPlan(record);
        //添加到计划表单中
//        String patsWardCode = record.getPatsWardCode();//患者科室
//        SatisfyPlanVo satisfyPlan = satisfyPlanMapper.findSatisfyPlanByName(record.getPlanSatisfyName());
        SatisfyPlanVo satisfyPlan = satisfyPlanMapper.findSatisfyPlanById(record.getPlanSatisfyNum());
        if (satisfyPlan == null) {
            ExceptionCast.cast(PlanResponseCode.FEIGN_ERROR);
        }
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
            paramMap.put("beginDate", satisfyPlan.getPlanSatisfyBeginDate());//开始区间
        }
        if (satisfyPlan.getPlanSatisfyEndDate() != null){
            paramMap.put("endDate", satisfyPlan.getPlanSatisfyEndDate());//结束区间
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
        switch (record.getPatientType()) {
            case 1: {//出院
//                int i = 0;

//                List<Map> datamap = null;
                paramMap.put("inOut", 2);// 1在院  2出院
//                paramMap.put("pageSize", 100);//每页展示数据
                Map datamap = managerPatientClient.findInHosPatientByParamToSF(paramMap);
                //调用服务发生异常
                Object error = datamap.get("error");
                if ((!"".equals(error) && error != null) || datamap == null) {
                    ExceptionCast.cast(CommonCode.FEIGN_ERROR);
                }
//                Object o = datamap.get("list");
                if (datamap != null) {
                    List<Map> list = (List<Map>) datamap.get("list");
                    redisTemplate.boundHashOps("satisfyPlan").put(satisfyPlan.getPlanSatisfyNum(), list);
                }
            }
            break;
            case 2: {//门诊
                ExceptionCast.cast(CommonCode.DATA_ERROR);
//                int i = 0;
//                Map paramMap = new HashMap();
                Map datamap = null;
//                paramMap.put("pageSize", 100);//每页展示数据
//                paramMap.put("patsWardCode", patsWardCodeList);//随访科室编码
//                paramMap.put("diseaseCode", diseaseCodeList);//疾病编码
//                paramMap.put("planSex", planResult.getPlanSex());//病人性别 1男 2女
//                paramMap.put("beginDate",planResult.getPlanBeginTime());//开始区间
//                paramMap.put("endDate",planResult.getPlanEndTime());//结束区间
//
//                String planAgeInterval = planResult.getPlanAgeInterval();
//                if (planAgeInterval != null) {//年龄区间
//                    String[] split = planAgeInterval.split(",");
//                    paramMap.put("startAge", split[0]);
//                    paramMap.put("endAge", split[1]);
//                }
//                paramMap.put("planExisPhone", planResult.getPlanExisPhone());//有无联系方式 1有 2无
                datamap = managerPatientClient.findOutHosPatientByParamToSF(paramMap);
                //调用服务发生异常
                Object error = datamap.get("error");
                if ((!"".equals(error) && error != null) || datamap == null) {
                    ExceptionCast.cast(CommonCode.FEIGN_ERROR);
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
//                paramMap.put("pageSize", 100);//每页展示数据
//                paramMap.put("patsWardCode", patsWardCodeList);//随访科室编码
//                paramMap.put("diseaseCode", diseaseCodeList);//疾病编码
//                paramMap.put("planSex", planResult.getPlanSex());//病人性别 1男 2女
//                paramMap.put("beginDate",planResult.getPlanBeginTime());//开始区间
//                paramMap.put("endDate",planResult.getPlanEndTime());//结束区间
//
//                String planAgeInterval = planResult.getPlanAgeInterval();
//                if (planAgeInterval != null) {//年龄区间
//                    String[] split = planAgeInterval.split(",");
//                    paramMap.put("startAge", split[0]);
//                    paramMap.put("endAge", split[1]);
//                }
//                paramMap.put("planExisPhone", planResult.getPlanExisPhone());//有无联系方式 1有 2无
                datamap = managerPatientClient.findInHosPatientByParamToSF(paramMap);
                //调用服务发生异常
                Object error = datamap.get("error");
                if ((!"".equals(error) && error != null)|| datamap == null) {
                    ExceptionCast.cast(CommonCode.FEIGN_ERROR);
                }
                if (datamap != null) {
                    List<Map> list = (List<Map>) datamap.get("list");
                    redisTemplate.boundHashOps("satisfyPlan").put(satisfyPlan.getPlanSatisfyNum(), list);
                }
            }
            break;
            case 4: {//体检
                ExceptionCast.cast(CommonCode.DATA_ERROR);
            }
            break;
            case 5: {//建档
                ExceptionCast.cast(CommonCode.DATA_ERROR);
            }
            break;
            default: {
                ExceptionCast.cast(CommonCode.DATA_ERROR);
            }
            break;
        }

//		resMap.put("plan", planResult);
        //发送消息   参数1 交换机  参数2 路由地址  参数3 需要发送的数据
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME_SP, RabbitMQConfig.EX_ROUTING_SATISFY_PLAN, satisfyPlan.getPlanSatisfyNum());
        log.info("rabbitMQ 传递的参数：{}", satisfyPlan.getPlanSatisfyNum());
        return ReturnFomart.retParam(200, satisfyPlan);
    }

    @Override
    public DataOutResponse findSatisfyPlanById(String planSatisfyNum) {
        SatisfyPlanVo satisfyPlan = satisfyPlanMapper.findSatisfyPlanById(planSatisfyNum);
        log.info("满意度计划的信息为:" + JSON.toJSONString(satisfyPlan));
        return ReturnFomart.retParam(200, satisfyPlan);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DataOutResponse updateSatisfyPlan(SatisfyPlanVo record) {
        log.info("修改的满意度计划信息为:" + JSON.toJSONString(record));
        SatisfyPlanVo satisfyPlan = satisfyPlanMapper.findSatisfyPlanById(record.getPlanSatisfyNum());
        if (CompareUtil.isEmpty(satisfyPlan) || satisfyPlan.getSatisfyPlanStatus() == 1) {
            return ReturnFomart.retParam(96, satisfyPlan);
        }
        satisfyPlanMapper.updateSatisfyPlan(record);
        return ReturnFomart.retParam(200, record);
    }

    @Override
    public DataOutResponse findAll(Long userId) {
        if (userId == null){
            return ReturnFomart.retParam(300,"请登录后再使用");
        }
//        satisfyPlanMapper.findAll(userId);
        return ReturnFomart.retParam(200, satisfyPlanMapper.findAll(userId));
    }

    @Override
    public DataOutResponse findByWard(String patientWard) {
        List<SatisfyPlanVo> list = satisfyPlanMapper.findByWard(patientWard);
        return ReturnFomart.retParam(200,list);
    }

}
