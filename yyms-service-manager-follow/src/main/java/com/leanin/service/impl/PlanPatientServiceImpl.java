package com.leanin.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.leanin.client.ManagerPatientClient;
import com.leanin.domain.plan.response.PlanResponseCode;
import com.leanin.domain.planpatient.response.PlanPatCode;
import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.response.ReturnFomart;
import com.leanin.domain.vo.*;
import com.leanin.exception.ExceptionCast;
import com.leanin.mapper.*;
import com.leanin.service.PlanPatientService;
import com.netflix.discovery.converters.Auto;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class PlanPatientServiceImpl implements PlanPatientService {

    @Autowired
    PlanPatientMapper planPatientMapper;

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    PlanInfoMapper planInfoMapper;

    @Autowired
    RulesInfoMapper rulesInfoMapper;

    @Autowired
    ManagerPatientClient managerPatientClient;

    @Autowired
    PatientInfoMapper patientInfoMapper;

    @Autowired
    FollowRecordMapper followRecordMapper;

    @Autowired
    FormRecordMapper formRecordMapper;

    @Autowired
    SatisfyPlanMapper satisfyPlanMapper;



    @Override
    public DataOutResponse findPlanPatientListByPlanId(String planNum, Integer planPatsStatus, Integer currentPage, Integer pageSize,String patientName) {
        //获取计划信息
        PlanInfoVo planInfo = planInfoMapper.findPlanInfoById(planNum);
        log.info("计划信息为:" + planNum);
        if (planInfo == null){//计划为空
            ExceptionCast.cast(PlanResponseCode.Data_ERROR);
        }
        //
        if (currentPage == null || "".equals(currentPage) || currentPage <= 0) {//默认第一页
            currentPage = 1;
        }
        if (pageSize == null || "".equals(pageSize) || pageSize <= 0) {//默认每页展示20条数据
            pageSize = 20;
        }
        if ("null".equals(patientName)){
            patientName=null;
        }
        HashMap dataMap = new HashMap();

        PageHelper.startPage(currentPage, pageSize);
//        if (planPatsStatus == null || planPatsStatus == -1 || planPatsStatus ==0 || planPatsStatus == 1){
            Page<PlanPatientVo> page = (Page<PlanPatientVo>) planPatientMapper.findPlanPatientList(planInfo.getPlanNum(), planPatsStatus, patientName);
            dataMap.put("totalCount",page.getTotal());
            dataMap.put("list", page.getResult());//
//        }else{
//            Page<PlanPatientRecordVo> page= (Page<PlanPatientRecordVo>) followRecordMapper.findPlanPatientList(planInfo.getPlanNum(), planPatsStatus, patientName);
//            dataMap.put("totalCount",page.getTotal());
//            dataMap.put("list", page.getResult());//
//        }
        return ReturnFomart.retParam(200, dataMap);

//        //封装参数
//        /*unfinishCount;//待随访人数
//        finishCount;//已完成随访人数
//        pastCount;//过期随访人数
//        deadCount;//收案人数*/
//        dataMap.put("unfinishCount", planPatientMapper.findUnfinishCount(planInfo.getPlanNum()));//待随访人数
//        dataMap.put("finishCount", planPatientMapper.findFinishCount(planInfo.getPlanNum()));//已完成随访人数
//        dataMap.put("pastCount", planPatientMapper.findPastCount(planInfo.getPlanNum()));//过期随访人数
//        dataMap.put("deadCount", planPatientMapper.findDeadCount(planInfo.getPlanNum()));//收案人数

//        System.out.println("查询的记录数："+page.getResult());

    }

    /**
     * 批量删除
     * @param patientPlanIds
     * @return
     */
    @Override
    public DataOutResponse delPatientList(List<Long> patientPlanIds,Integer planPatsStatus) {
        if (patientPlanIds == null || patientPlanIds.size() <=0 ){
            return ReturnFomart.retParam(96, "请选择患者信息再进行删除");
        }
        for (Long patientPlanId : patientPlanIds) {
            if (planPatsStatus != null && planPatsStatus < 2){
                planPatientMapper.updatePatientStatusById(patientPlanId);
            }else{
                followRecordMapper.updatePatientStatusById(patientPlanId);
            }

        }



        return ReturnFomart.retParam(200, "删除成功");
    }

    /**
     * 导入患者信息
     * @param planPatients
     * @return
     */
    @Override
    public DataOutResponse addPatientList(List<PlanPatientVo> planPatients) {
        for (PlanPatientVo planPatient : planPatients) {
            PlanPatientVo patientVo=planPatientMapper.findPlanPatientByPatientId(planPatient.getPatientId());
            if (patientVo != null){
                return ReturnFomart.retParam(4000, "数据重复请勿重复导入");
            }
            planPatientMapper.addPlanPatient(planPatient);
        }
        return ReturnFomart.retParam(200, "添加成功");
    }

    /**
     * 我的随访
     * @param startDate
     * @param endDate
     * @param planNum
     * @param patientName
     * @return
     */
    @Override
    public DataOutResponse findPlanPatientList(Date startDate, Date endDate, String planNum, String patientName) {

        //获取登录人信息 判断是否是管理员


        String userId="1";

//        planPatientMapper.findPlanPatientList()
        return null;
    }

    //根据patientId查询患者信息和病史
    @Override
    public DataOutResponse findPlanPatientById(Long patientId,Integer patientSource,String planNum,Integer planType) {
        Map dataMap=new HashMap();
        //查询本地患者信息
        PatientInfoVo PatientInfoVo = patientInfoMapper.findPatientById(patientId + "", null);

//        PlanPatientVo planPatient = planPatientMapper.findPlanPatientById(patientId);

//        Integer patientSource = planPatient.getPatientSource();
        Map patientMap=new HashMap();
        Map paraMap=new HashMap();
        paraMap.put("patientId",patientId.toString());
        paraMap.put("inOut",patientSource);
        switch (patientSource){
            case 2:{//出院
                if (PatientInfoVo == null){//没有本地患者信息  调用远程患者信息
                    patientMap = managerPatientClient.findInHosPatientById(patientId + "");
                }
                List<Map> inHosRecord = managerPatientClient.findInHosRecordById(paraMap);
                dataMap.put("record",inHosRecord);
            }break;
            case 3:{//门诊
                if (PatientInfoVo == null){//没有本地患者信息  调用远程患者信息
                    patientMap = managerPatientClient.findOutHosPatientById(patientId + "");
                }
                List<Map> outHosRecord = managerPatientClient.findOutHosRecordById(patientId + "");
                dataMap.put("record",outHosRecord);
            }break;
            case 1:{//在院
                if (PatientInfoVo == null){//没有本地患者信息  调用远程患者信息
                    patientMap = managerPatientClient.findInHosPatientById(patientId + "");
                }
                List<Map> inHosRecord = managerPatientClient.findInHosRecordById(paraMap);
                dataMap.put("record",inHosRecord);

            }break;
        }

        if(PatientInfoVo != null){
            dataMap.put("patientInfo",PatientInfoVo);
        }else{
            dataMap.put("patientInfo",patientMap);
        }
        if (planNum != null && !"".equals(planNum)){
            switch (planType){
                case 1://随访和宣教
                {
                    PlanInfoVo planInfoVo = planInfoMapper.findPlanInfoById(planNum);
                    dataMap.put("planInfo",planInfoVo);
                }
                break;
                case 2://满意度
                    SatisfyPlanVo satisfyPlan = satisfyPlanMapper.findSatisfyPlanById(planNum);
                    dataMap.put("planInfo",satisfyPlan);
                    break;
                default:
                    break;
            }
        }
        return ReturnFomart.retParam(200, dataMap);
    }

    @Override
    public DataOutResponse findListByPlanId(String planNum) {
        //获取计划信息
        PlanInfoVo planInfo = planInfoMapper.findPlanInfoById(planNum);
        log.info("计划信息为:" + planNum);
        if (planInfo == null){//计划为空
            ExceptionCast.cast(PlanResponseCode.Data_ERROR);
        }

        List<PlanPatientVo> planPatientList = planPatientMapper.findPlanPatientList(planNum, 0, null);
        return ReturnFomart.retParam(200, planPatientList);
    }

    @Override
    public DataOutResponse updatePlanPatient(Long patientPlanId, Integer followType, String handleSugges,FormRecordVo formRecordVo) {
        PlanPatientVo patient = planPatientMapper.findPlanPatientById(patientPlanId);
        if (patient == null){
            return ReturnFomart.retParam(300, "信息不存在");
        }
        if (patient.getFormStatus() == 1){//表单完成状态
            formRecordMapper.addFormRecord(formRecordVo);//添加表单记录
            patient.setFormStatus(2);//改成已添加
        }

        if (followType != null){
            patient.setPlanPatsStatus(followType);
        }
        if (handleSugges != null){
            patient.setHandleSugges(handleSugges);
        }
        planPatientMapper.updatePlanPatient(patient);
        return ReturnFomart.retParam(200, "保存成功");
    }



}
