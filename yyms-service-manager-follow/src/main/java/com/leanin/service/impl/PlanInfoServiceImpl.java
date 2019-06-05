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
import com.leanin.domain.vo.PlanPatientVo;
import com.leanin.domain.vo.RulesInfoVo;
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

import java.util.*;

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
    public DataOutResponse findPlanList(int page, int pageSize, String planName, int planType,Long userId) {
        log.info("搜索的计划名称为:" + planName);
        if (page < 1){
            page = 1;
        }
        if (pageSize <1){
            pageSize = 10;
        }
        PageHelper.startPage(page, pageSize);
        List<PlanInfoDto> planList = planInfoMapper.findPlanList(planName, planType,userId);
        PageInfo pageInfo = new PageInfo<>(planList);
        return ReturnFomart.retParam(200, pageInfo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DataOutResponse updatePlanStatus(String planNum, int status) {
        log.info("计划编号为:" + planNum + "状态为:" + status);
        if(status == -1 ){//删除计划
            PlanPatientVo planPatientVo = planPatientMapper.findByPlanNumAndSendType(planNum,1);
            if (planPatientVo != null){//如果计划内的患者已经发送过表单 则不能删除
                return ReturnFomart.retParam(5600,planNum);
            }
        }

        PlanInfoVo planInfo = planInfoMapper.findPlanInfoById(planNum);
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
    public ResponseResult addPlanInfo(PlanInfoVo record, RulesInfoVo rulesInfoVo) {
        log.info("增加的计划信息为:" + JSON.toJSONString(record));
        if (record.getPlanEndTime().getTime() < new Date().getTime()){
            record.setImportData(2);//导入患者信息状态  1 导入未完成  2 导入完成
        }else{
            record.setImportData(1);//导入患者信息状态  1 导入未完成  2 导入完成
        }
        PlanInfoVo planInfo = planInfoMapper.findPlanInfoByName(record.getPlanName());
        if (planInfo != null) {
            //如果已经存在该名称,不允许重复添加
            ExceptionCast.cast(PlanResponseCode.INVALID_PARAM);
//            return ReturnFomart.retParam(4000, planInfo);
        }
        //添加到计划表单中
//        String patsWardCode = record.getPatsWardCode();//患者科室
        rulesInfoVo.setRulesCreateTime(new Date());
        rulesInfoVo.setRulesInfoStatus(0);
        rulesInfoMapper.addRulesInfo(rulesInfoVo);
        log.info("添加的规则信息:{}",JSON.toJSONString(rulesInfoVo));
        record.setRulesInfoNum(rulesInfoVo.getRulesInfoId());
        planInfoMapper.addPlanInfo(record);
        log.info("增加的计划信息为:" + JSON.toJSONString(record));
        PlanInfoVo planResult = planInfoMapper.findPlanInfoByName(record.getPlanName());
        Map resMap = new HashMap();
        // 封装参数
        Map paramMap = new HashMap();
        //科室集合
//        String patsWardCode = planResult.getPatsWardCode();
        String patsWardId = planResult.getPatsWardId();
        if (patsWardId != null){
            List<String> patsWardCodeList = JSON.parseArray(patsWardId, String.class);
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
            paramMap.put("beginDate", planResult.getPlanBeginTime());//开始区间
        }
        if (planResult.getPlanEndTime() != null){
            paramMap.put("endDate", planResult.getPlanEndTime());//结束区间
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
        switch (record.getPatientInfoSource()) {
            case 1: {//出院
                paramMap.put("inOut", 2);// 1在院  2出院
                Map datamap = managerPatientClient.findInHosPatientByParamToSF(paramMap);
                //调用服务发生异常
                Object error = datamap.get("error");
                if ((!"".equals(error) && error !=null) || datamap == null){
                    ExceptionCast.cast(CommonCode.FEIGN_ERROR);
//                    return ReturnFomart.retParam(1006, "服务器网络异常，请联系管理员");
                }
//                Object o = datamap.get("list");
                if (datamap != null) {
                    List<Map> list = (List<Map>) datamap.get("list");
                    resMap.put("totalCount", datamap.get("totalCount"));
                    redisTemplate.boundHashOps("plan").put(planResult.getPlanNum(), list);
                }
            }
            break;
            case 2: {//门诊
                ExceptionCast.cast(CommonCode.DATA_ERROR);
//                int i = 0;
//                Map paramMap = new HashMap();
                Map datamap = null;
                datamap = managerPatientClient.findOutHosPatientByParamToSF(paramMap);
                //调用服务发生异常
                Object error = datamap.get("error");
                if ((!"".equals(error) && error !=null) || datamap == null){
//                    return ReturnFomart.retParam(1006, "服务器网络异常，请联系管理员");
                    ExceptionCast.cast(CommonCode.FEIGN_ERROR);
                }
                //将查询出来的数据 存到缓存中
                if (datamap != null) {
                    List<Map> list = (List<Map>) datamap.get("list");
                    resMap.put("totalCount", datamap.get("totalCount"));
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
                    ExceptionCast.cast(CommonCode.FEIGN_ERROR);
                }
                if (datamap != null) {
                    List<Map> list = (List<Map>) datamap.get("list");
                    resMap.put("totalCount", datamap.get("totalCount"));
                    redisTemplate.boundHashOps("plan").put(planResult.getPlanNum(), list);
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

    //查询所有计划信息 给其他模块调用
    @Override
    public DataOutResponse findAllPlan(Integer planType,Long userId) {
        List<PlanInfoVo> planInfoVos = planInfoMapper.findAllPlan(planType,userId);
        return ReturnFomart.retParam(200, planInfoVos);
    }

    @Override
    public DataOutResponse findByWard(String patientWard) {
        List<PlanInfoVo> list = planInfoMapper.findByWard(patientWard);
        return ReturnFomart.retParam(200,list);
    }

    @Override
    public DataOutResponse findFollowPlanByParam(String planName, String deptId, Long userId, Integer rulesType, String startDate, String endDate,Integer page,Integer pageSize) {
        if (page == null || page < 1 ){
            page = 1;
        }
        if (pageSize == null || pageSize < 0){
            pageSize = 10;
        }
        PageHelper.startPage(page,pageSize);
        Page<PlanInfoVo> pageList = (Page<PlanInfoVo>) planInfoMapper.findFollowPlanByParam(planName,deptId,userId,rulesType,startDate,endDate);
        Map result = new HashMap();
        result.put("totalCount",pageList.getTotal());
        result.put("list",pageList.getResult());
        return ReturnFomart.retParam(200,result);
    }

}
