package com.leanin.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.leanin.client.ManagerPatientClient;
import com.leanin.domain.plan.response.PlanResponseCode;
import com.leanin.domain.planpatient.response.PlanPatCode;
import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.response.ReturnFomart;
import com.leanin.domain.vo.PatientInfoVo;
import com.leanin.domain.vo.PlanInfoVo;
import com.leanin.domain.vo.PlanPatientVo;
import com.leanin.exception.ExceptionCast;
import com.leanin.mapper.PatientInfoMapper;
import com.leanin.mapper.PlanInfoMapper;
import com.leanin.mapper.PlanPatientMapper;
import com.leanin.mapper.RulesInfoMapper;
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

        PageHelper.startPage(currentPage, pageSize);
        Page<PlanPatientVo> page = (Page<PlanPatientVo>) planPatientMapper.findPlanPatientList(planInfo.getPlanNum(), planPatsStatus, patientName);

        //封装参数
        /*unfinishCount;//待随访人数
        finishCount;//已完成随访人数
        pastCount;//过期随访人数
        deadCount;//收案人数*/
        HashMap dataMap = new HashMap();
//        dataMap.put("unfinishCount", planPatientMapper.findUnfinishCount(planInfo.getPlanNum()));//待随访人数
//        dataMap.put("finishCount", planPatientMapper.findFinishCount(planInfo.getPlanNum()));//已完成随访人数
//        dataMap.put("pastCount", planPatientMapper.findPastCount(planInfo.getPlanNum()));//过期随访人数
//        dataMap.put("deadCount", planPatientMapper.findDeadCount(planInfo.getPlanNum()));//收案人数
        dataMap.put("totalCount",page.getTotal());
        dataMap.put("list", page.getResult());//
        System.out.println("查询的记录数："+page.getResult());
        return ReturnFomart.retParam(200, dataMap);
    }

    /**
     * 批量删除
     * @param patientPlanIds
     * @return
     */
    @Override
    public DataOutResponse delPatientList(List<Long> patientPlanIds) {
        if (patientPlanIds == null || patientPlanIds.size() <=0 ){
            return ReturnFomart.retParam(96, "请选择患者信息再进行删除");
        }
        for (Long patientPlanId : patientPlanIds) {
            planPatientMapper.updatePatientStatusById(patientPlanId);
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
    public DataOutResponse findPlanPatientById(Long patientId,Integer patientSource) {
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

        return ReturnFomart.retParam(200, dataMap);
    }


}
