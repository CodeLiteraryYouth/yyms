package com.leanin.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leanin.client.ManagerPatientClient;
import com.leanin.config.RabbitMQConfig;
import com.leanin.domain.dto.PlanInfoDto;
import com.leanin.domain.plan.response.PlanResponseCode;
import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.response.ReturnFomart;
import com.leanin.domain.vo.PlanInfoVo;
import com.leanin.exception.ExceptionCast;
import com.leanin.mapper.PatientInfoMapper;
import com.leanin.mapper.PlanInfoMapper;
import com.leanin.mapper.PlanPatientMapper;
import com.leanin.mapper.RulesInfoMapper;
import com.leanin.model.response.CommonCode;
import com.leanin.model.response.ResponseResult;
import com.leanin.service.PlanInfoService;
import com.leanin.utils.CompareUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class PlanInfoServiceImpl implements PlanInfoService {


    @Autowired
    PlanPatientMapper planPatientMapper;

    @Autowired
    RulesInfoMapper rulesInfoMapper;

    @Autowired
    private PlanInfoMapper planInfoMapper;

    @Autowired
    private ManagerPatientClient managerPatientClient;

    @Autowired
    private PatientInfoMapper patientInfoMapper;

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    RabbitTemplate rabbitTemplate;


    @Override
    public DataOutResponse findPlanList(int page, int pageSize, String planName, int planType) {
        log.info("搜索的计划名称为:" + planName);
        PageHelper.startPage(page, pageSize);
        List<PlanInfoDto> planList = planInfoMapper.findPlanList(planName, planType);
        PageInfo pageInfo = new PageInfo<>(planList);
        return ReturnFomart.retParam(200, pageInfo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DataOutResponse updatePlanStatus(String planNum, int status) {
        log.info("计划编号为:" + planNum + "状态为:" + status);
        PlanInfoVo planInfo = planInfoMapper.findPlanInfoById(planNum);
        log.info("计划信息为:" + planNum);
        if (CompareUtil.isEmpty(planInfo) && planInfo.getPlanStatus() == 2) {
            //禁止状态下禁止更改状态
            return ReturnFomart.retParam(96, planInfo);
        }
        planInfoMapper.updatePlanStatus(planNum, status);
        return ReturnFomart.retParam(200, planInfo);
    }

    //增加计划
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult addPlanInfo(PlanInfoVo record) {
        log.info("增加的计划信息为:" + JSON.toJSONString(record));
        record.setImportData(2);//默认设置未导入数据库
        PlanInfoVo planInfo = planInfoMapper.findPlanInfoByName(record.getPlanName());
        if (CompareUtil.isNotEmpty(planInfo)) {
            //如果已经存在该名称,不允许重复添加
            ExceptionCast.cast(PlanResponseCode.INVALID_PARAM);
//            return ReturnFomart.retParam(4000, planInfo);
        }
        //添加到计划表单中
//        String patsWardCode = record.getPatsWardCode();//患者科室

        planInfoMapper.addPlanInfo(record);
        PlanInfoVo planResult = planInfoMapper.findPlanInfoByName(record.getPlanName());
        Map resMap = new HashMap();
        // 封装参数
        //科室集合
        String patsWardCode = planResult.getPatsWardCode();
        List<String> patsWardCodeList = JSON.parseArray(patsWardCode, String.class);
        //疾病集合
        String diseaseCode = planResult.getDiseaseCode();
        List<String> diseaseCodeList = JSON.parseArray(diseaseCode, String.class);
        Map paramMap = new HashMap();
        paramMap.put("patsWardCode", patsWardCodeList);// 患者随访科室编码 可能是集合
        paramMap.put("diseaseCode", diseaseCodeList);//疾病编码
        paramMap.put("planSex", planResult.getPlanSex());//病人性别 1男 2女
        paramMap.put("beginDate", planResult.getPlanBeginTime());//开始区间
        paramMap.put("endDate", planResult.getPlanEndTime());//结束区间
        String planAgeInterval = planResult.getPlanAgeInterval();//年龄区间
        if (planAgeInterval != null) {//年龄区间
            String[] split = planAgeInterval.split(",");
            paramMap.put("startAge", split[0]);
            paramMap.put("endAge", split[1]);
        }
        paramMap.put("planExisPhone", planResult.getPlanExisPhone());//有无联系方式 1有 2无
        //判断病人来源 (1:出院 2：门诊 3：在院 4：体检 5：建档)
        switch (record.getPatientInfoSource()) {
            case 1: {//出院
//                int i = 0;

//                List<Map> datamap = null;
                paramMap.put("inOut", 2);// 1在院  2出院
//                paramMap.put("pageSize", 100);//每页展示数据

//                while (true) {
//                    i++;
//                    paramMap.put("currentPage", i);
                Map datamap = managerPatientClient.findInHosPatientByParamToSF(paramMap);
                //调用服务发生异常
                Object error = datamap.get("error");
                if (!"".equals(error) && error !=null){
                    ExceptionCast.cast(CommonCode.FEIGN_ERROR);
//                    return ReturnFomart.retParam(1006, "服务器网络异常，请联系管理员");
                }
//                Object o = datamap.get("list");
                if (datamap != null) {
                    List<Map> list = (List<Map>) datamap.get("list");
                    resMap.put("totalCount", datamap.get("totalCount"));
                    redisTemplate.boundHashOps("plan").put(planResult.getPlanNum(), list);
                }
//                else{
//                    resMap.put("totalCount",0);
//                }
//                    if (datamap.size() <= 0) {//当没有数据的时候结束循环
//                        break;
//                    }
                //将查询出来的数据 存到缓存中

//                }
            }
            break;
            case 2: {//门诊
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
//                while (true) {
//                    i++;
//                    paramMap.put("currentPage", i);
                datamap = managerPatientClient.findOutHosPatientByParamToSF(paramMap);
                //调用服务发生异常
                Object error = datamap.get("error");
                if (!"".equals(error) || error !=null){
//                    return ReturnFomart.retParam(1006, "服务器网络异常，请联系管理员");
                    ExceptionCast.cast(CommonCode.FEIGN_ERROR);
                }
//                    if (datamap.size() <= 0) {//当没有数据的时候结束循环
//                        break;
//                    }
                //将查询出来的数据 存到缓存中
//                Object o = datamap.get("list");
                if (datamap != null) {
                    List<Map> list = (List<Map>) datamap.get("list");
                    resMap.put("totalCount", datamap.get("totalCount"));
                    redisTemplate.boundHashOps("plan").put(planResult.getPlanNum(), list);
                }
//                else {
//                    resMap.put("totalCount",0);
//                }

//                }
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
//                while (true) {
//                    i++;
//                    paramMap.put("currentPage", i);
                datamap = managerPatientClient.findInHosPatientByParamToSF(paramMap);
                //调用服务发生异常
                Object error = datamap.get("error");
                if (!"".equals(error) || error !=null){
//                    return ReturnFomart.retParam(1006, "服务器网络异常，请联系管理员");
                    ExceptionCast.cast(CommonCode.FEIGN_ERROR);
                }
//                    if (datamap.size() <= 0) {//当没有数据的时候结束循环
//                        break;
//                    }
                //将查询出来的数据 存到缓存中
//                Object o = datamap.get("list");
                if (datamap != null) {
                    List<Map> list = (List<Map>) datamap.get("list");
                    resMap.put("totalCount", datamap.get("totalCount"));
                    redisTemplate.boundHashOps("plan").put(planResult.getPlanNum(), list);
                }
//                else{
//                    resMap.put("totalCount",0);
//                }

//                }
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

        resMap.put("plan", planResult);
        //发送消息   参数1 交换机  参数2 路由地址  参数3 需要发送的数据
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME,RabbitMQConfig.EX_ROUTING_PLAN_PATIENT,planResult.getPlanNum());
        log.info("rabbitMQ 传递的参数：{}",planResult.getPlanNum());
        return new ResponseResult(CommonCode.SUCCESS);
//        return ReturnFomart.retParam(200, resMap);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DataOutResponse findPlanInfoById(String planNum) {
        PlanInfoVo planInfoVo = planInfoMapper.findPlanInfoById(planNum);
//        //获取计划信息
//        PlanInfoVo planInfo = planInfoMapper.findPlanInfoById(planNum);
//        log.info("计划信息为:" + planNum);
//        //获取缓存中的病人数据
//        List<Map> list = (List<Map>) redisTemplate.boundHashOps("plan").get(planNum);
//        if (list != null) {//有缓存
//            //获取规则信息
//            RulesInfoVo rules = rulesInfoMapper.findRulesById(planInfo.getRulesInfoNum());
//            String rulesInfoText = rules.getRulesInfoText();
//            Map rulesMap = JSON.parseObject(rulesInfoText, Map.class);//获取规则
//            String tiemFont = (String) rulesMap.get("tiemFont");//获取下次任务的时间 1天 2星期 3月
//            int timeNum = Integer.parseInt((String) rulesMap.get("timeNum"));
//            int timeChoosed = Integer.parseInt((String) rulesMap.get("timeChoosed")); //1 6:00， 2 7:00 一次后推直到 16 21:00
//            log.info("规则信息:{}", rulesMap);
//
//            PlanPatientVo planPatientVo = new PlanPatientVo();
//            for (Map map : list) {
//                planPatientVo.setPlanNum(planInfo.getPlanNum());//设置计划编号
//                Long patientId = (Long) map.get("patientId");
//                planPatientVo.setPatientId(patientId);//设置病人id
//                String patientInHospitalNo = (String) map.get("patientInHospitalNo");
//                planPatientVo.setPatientInHospitalNo(patientInHospitalNo);//住院号有疑问 可能是集合
//                String patientName = (String) map.get("patientName");
//                planPatientVo.setPatientName(patientName);//设置病人姓名
//                Integer patientSex = (Integer) map.get("patientSex");
//                planPatientVo.setPatientSex(patientSex);//设置病人性别
//                Integer patientAge = (Integer) map.get("patientAge");
//                planPatientVo.setPatientAge(patientAge);//设置病人年龄
//                String patientPhone = (String) map.get("patientPhone");
//                planPatientVo.setPatientPhone(patientPhone);//设置病人手机号码
//                String patientWard = (String) map.get("patientWard");
//                planPatientVo.setPatientWard(patientWard);//设置病人科室  可能是集合
//                Date patientDateTime = (Date) map.get("patientDateTime");
//                planPatientVo.setPatientDateTime(patientDateTime);//设置病人时间  可能是集合
//                String patientDoctor = (String) map.get("patientDoctor");
//                planPatientVo.setPatientDoctor(patientDoctor);//设置诊治医生  可能是集合
//                String patientCondition = (String) map.get("patientCondition");
//                planPatientVo.setPatientCondition(patientCondition);//设置病人情况  可能是集合
//                String patientDiagous = (String) map.get("patientDiagous");
//                planPatientVo.setPatientDiagous(patientDiagous);//设置病人诊断  可能是集合
//                Integer patientType = (Integer) map.get("patientType");
//                planPatientVo.setPatientType(patientType);//设置病人来源   可能是集合
//                String areaCode = (String) map.get("areaCode");
//                planPatientVo.setAreaCode(areaCode);//设置院区编码   可能是集合
//                planPatientVo.setPlanPatsStatus(planInfo.getPlanStatus());//设置计划状态 -1:已移除 0：正在使用
//                planPatientVo.setRulesInfoId(rules.getRulesInfoId());//设置随访规则id
//
//                Calendar calendar = Calendar.getInstance();
//                calendar.setTime(patientDateTime);
//                Date nextDate = null;
//                switch (tiemFont) {
//                    case "1": {//天
//                        calendar.set(Calendar.DATE, timeNum);
//                        calendar.set(Calendar.HOUR_OF_DAY, timeChoosed + 5);
//                        nextDate = calendar.getTime();
//                    }
//                    break;
//                    case "2": {//星期
//                        calendar.set(Calendar.WEEK_OF_YEAR, timeNum);
//                        calendar.set(Calendar.HOUR_OF_DAY, timeChoosed + 5);
//                        nextDate = calendar.getTime();
//                    }
//                    break;
//                    case "3": {//月
//                        calendar.set(Calendar.MONTH, timeNum);
//                        calendar.set(Calendar.HOUR_OF_DAY, timeChoosed + 5);
//                        nextDate = calendar.getTime();
//                    }
//                    break;
//                }
//                planPatientVo.setNextDate(nextDate);//设置下次随访日期
//
//                planPatientMapper.addPlanPatient(planPatientVo);//将数据存到数据中
//
//            }
//            Long delete = redisTemplate.boundHashOps("plan").delete(planInfo.getPlanNum());
//            log.info("是否删除缓存中存的数据:{}",planInfo.getPlanNum(),delete);
//        }
//        //
//        if (currentPage == null || "".equals(currentPage) || currentPage<=0){//默认第一页
//            currentPage=1;
//        }
//        if(pageSize == null || "".equals(pageSize) || pageSize<=0){//默认每页展示20条数据
//            currentPage=20;
//        }
//        PageHelper.startPage(currentPage,currentPage);
//        Page<PlanPatientVo> page = (Page<PlanPatientVo>) planPatientMapper.findPlanPatientList(planInfo.getPlanNum(), planPatsStatus);
//        //封装参数
//        HashMap dataMap =new HashMap();
//        dataMap.put("totalCount",page.getTotal());//总记录数
//        dataMap.put("list",page.getResult());//
        return ReturnFomart.retParam(200, planInfoVo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DataOutResponse updatePlanInfo(PlanInfoVo record) {
        log.info("修改的计划信息为:" + JSON.toJSONString(record));
        PlanInfoVo planInfo = planInfoMapper.findPlanInfoById(record.getPlanNum());
        if (CompareUtil.isEmpty(planInfo) && planInfo.getPlanStatus() == 2) {
            //禁止状态下禁止修改信息
            return ReturnFomart.retParam(96, planInfo);
        }
        planInfoMapper.updatePlanInfo(record);
        return ReturnFomart.retParam(200, record);
    }


    @Override
    public DataOutResponse findPlanInfoByPlanName(String planName, Integer currentPage, Integer pageSize) {
        if (currentPage == null || "".equals(currentPage) || currentPage <= 0) {//默认第一页
            currentPage = 1;
        }
        if (pageSize == null || "".equals(pageSize) || pageSize <= 0) {//默认每页展示20条数据
            currentPage = 20;
        }
        PageHelper.startPage(currentPage, currentPage);
        Page<PlanInfoVo> page = (Page<PlanInfoVo>) planInfoMapper.findPlanInfoByPlanName("%" + planName + "%");

        //封装参数
        Map dataMap = new HashMap();
        dataMap.put("totalCount", page.getTotal());
        dataMap.put("list", page.getResult());
        return ReturnFomart.retParam(200, dataMap);
    }

    //根据计划类型 查询计划信息
    @Override
    public DataOutResponse findPlanListByType(Integer planType) {
        List<PlanInfoVo> list =planInfoMapper.findPlanListByType(planType);
        Map dataMap =new HashMap();
        dataMap.put("list",list);
        return ReturnFomart.retParam(200, dataMap);
    }

}
