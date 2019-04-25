package com.leanin.service.impl;

import com.alibaba.fastjson.JSON;
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
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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
    public DataOutResponse findPlanPatientListByPlanId(String planNum, Integer planPatsStatus, Integer currentPage, Integer pageSize, String patientName) {
        //获取计划信息
        PlanInfoVo planInfo = planInfoMapper.findPlanInfoById(planNum);
        log.info("计划信息为:" + planNum);
        if (planInfo == null) {//计划为空
            ExceptionCast.cast(PlanResponseCode.Data_ERROR);
        }
        //
        if (currentPage == null || "".equals(currentPage) || currentPage <= 0) {//默认第一页
            currentPage = 1;
        }
        if (pageSize == null || "".equals(pageSize) || pageSize <= 0) {//默认每页展示20条数据
            pageSize = 20;
        }
        if ("null".equals(patientName)) {
            patientName = null;
        }
        HashMap dataMap = new HashMap();

        PageHelper.startPage(currentPage, pageSize);
//        if (planPatsStatus == null || planPatsStatus == -1 || planPatsStatus ==0 || planPatsStatus == 1){
        Page<PlanPatientVo> page = (Page<PlanPatientVo>) planPatientMapper.findPlanPatientList(planInfo.getPlanNum(), planPatsStatus, patientName);
        dataMap.put("totalCount", page.getTotal());
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
     *
     * @param patientPlanIds
     * @return
     */
    @Override
    public DataOutResponse delPatientList(List<Long> patientPlanIds, Integer planPatsStatus) {
        if (patientPlanIds == null || patientPlanIds.size() <= 0) {
            return ReturnFomart.retParam(96, "请选择患者信息再进行删除");
        }
        for (Long patientPlanId : patientPlanIds) {
            if (planPatsStatus != null && planPatsStatus < 2) {
                planPatientMapper.updatePatientStatusById(patientPlanId);
            } else {
                followRecordMapper.updatePatientStatusById(patientPlanId);
            }

        }


        return ReturnFomart.retParam(200, "删除成功");
    }

    /**
     * 导入患者信息
     *
     * @param planPatients
     * @return
     */
    @Override
    public DataOutResponse addPatientList(List<PlanPatientVo> planPatients) {
        for (PlanPatientVo planPatient : planPatients) {
            PlanInfoVo planInfo = planInfoMapper.findPlanInfoById(planPatient.getPlanNum());
//            PlanPatientVo patientVo=planPatientMapper.findPlanPatientByPatientId(planPatient.getPatientId());
            PlanPatientVo patientVo = planPatientMapper.findByPnumAndPid(planPatient.getPatientId(), planPatient.getPlanNum());
            if (patientVo != null) {
                return ReturnFomart.retParam(4000, "数据重复请勿重复导入");
            }
            planPatient.setSendType(1);
            planPatient.setFormStatus(1);
            planPatient.setFormId(planInfo.getFollowFormNum());
            planPatient.setPatientStatus(1);
            planPatient.setPlanPatsStatus(0);
            planPatient.setRulesInfoId(planInfo.getRulesInfoNum());
            RulesInfoVo rules = rulesInfoMapper.findRulesById(planInfo.getRulesInfoNum());
            planPatient.setNextDate(getNextDate(rules, planPatient.getNextDate()));
//            planPatient.
            planPatientMapper.addPlanPatient(planPatient);
        }
        return ReturnFomart.retParam(200, "添加成功");
    }

    private Date getNextDate(RulesInfoVo rulesInfo, Date lastDate) {
        String rulesInfoText = rulesInfo.getRulesInfoText();
        Map rulesMap = JSON.parseObject(rulesInfoText, Map.class);//获取规则
        String tiemFont = (String) rulesMap.get("tiemFont");//获取下次任务的时间 1天 2星期 3月
        String timeNumStr = (String) rulesMap.get("timeNum");
        int timeNum = 0;
        if (!"".equals(timeNumStr) && timeNumStr != null) {
            timeNum = Integer.parseInt(timeNumStr);//
        }

        int timeChoosed = Integer.parseInt((String) rulesMap.get("timeChoosed")); //1 6:00， 2 7:00 一次后推直到 16 21:00
//            int timeChoosed = (int) rulesMap.get("timeChoosed"); //1 6:00， 2 7:00 一次后推直到 16 21:00
        String timeSelect = (String) rulesMap.get("timeSelect");
        Date timePick = null;
        Object timePick1 = rulesMap.get("timePick");
        if (!"".equals(timePick1) && timePick1 != null) {
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                timePick = sf.parse((String) timePick1);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
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
//        Date lastDate = new Date((Long) map.get("lastDate"));//获取最近一次的出院时间
        Date nextDate = null;

        switch (rulesInfo.getRulesInfoTypeName()) {
            case 1: {//1：定期随访
                nextDate = setNextDate(new Date(), timeSelect, timeChoosed, weeks, sendTimeDays, sendTimeMonths);
            }
            break;
            case 2: {//2：定时随访
                nextDate = setNextDate(timePick, timeChoosed);
            }
            break;
            case 3: {//3：普通随访
                nextDate = setNextDate(lastDate, tiemFont, timeNum, timeChoosed);
            }
            break;
            default: {
                nextDate = setNextDate(lastDate, tiemFont, timeNum, timeChoosed);
            }
            break;
                    /*case 4: {//4：闭环宣教

                    }
                    break;
                    case 5: {//5：普通宣教

                    }
                    break;
                    case 6: {//6：药品宣教

                    }
                    break;
                    case 7: {//7：疾病宣教

                    }
                    break;
                    case 8: {//8：普通提醒

                    }
                    break;*/
        }
        return nextDate;
    }

    //设置普通随访时间
    private Date setNextDate(Date lastDate, String tiemFont, Integer timeNum, Integer timeChoosed) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(lastDate);
        Date nextDate = null;
        //普通随访
        switch (tiemFont) {
            case "1": {//天
                calendar.add(Calendar.DATE, timeNum);
                calendar.set(Calendar.HOUR_OF_DAY, timeChoosed + 5);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                nextDate = calendar.getTime();
            }
            break;
            case "2": {//星期
                calendar.add(Calendar.WEEK_OF_YEAR, timeNum);
                calendar.set(Calendar.HOUR_OF_DAY, timeChoosed + 5);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                nextDate = calendar.getTime();
            }
            break;
            case "3": {//月
                calendar.add(Calendar.MONTH, timeNum);
                calendar.set(Calendar.HOUR_OF_DAY, timeChoosed + 5);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                nextDate = calendar.getTime();
            }
            break;
        }
        return nextDate;
    }

    //定期随访
    private Date setNextDate(Date nowDate, String timeSelect, Integer timeChoosed, Integer weeks, Integer sendTimeDays, Integer sendTimeMonths) {
        Calendar calendar = Calendar.getInstance();
//        Date nowDate = new Date();
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

    //定时随访
    private Date setNextDate(Date timePick, Integer timeChoosed) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(timePick);
        calendar.set(Calendar.HOUR_OF_DAY, timeChoosed + 5);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date nextDate = calendar.getTime();
        return nextDate;
    }

    /**
     * 我的随访
     *
     * @param startDate
     * @param endDate
     * @param planNum
     * @param patientName
     * @return
     */
    /*@Override
    public DataOutResponse findPlanPatientList(Date startDate, Date endDate, String planNum, String patientName) {

        //获取登录人信息 判断是否是管理员


        String userId = "1";

//        planPatientMapper.findPlanPatientList()
        return null;
    }*/

    //根据patientId查询患者信息和病史
    @Override
    public DataOutResponse findPlanPatientById(Long patientId, Integer patientSource, String planNum, Integer planType, Integer type) {
        Map dataMap = new HashMap();
//        Integer patientSource = planPatient.getPatientSource();
        if (type == 1) {//患者管理查看档案
            //查询本地患者信息
            PatientInfoVo PatientInfoVo = patientInfoMapper.findPatientById(patientId + "", null);
            if (PatientInfoVo != null) {//
                dataMap.put("patientInfo", PatientInfoVo);
            } else {//获取原数据患者信息 和记录
                dataMap = getRecord(patientId + "", patientSource, dataMap);
            }
        } else {//查询随访/宣教计划患者信息
            dataMap = getRecord(patientId + "", patientSource, dataMap);
            PlanPatientVo planPatient = planPatientMapper.findPlanPatientById(patientId);
//            PatientInfoVo PatientInfoVo = patientInfoMapper.findPatientById(patientId + "", null);
//            if (PatientInfoVo != null ){
//                dataMap.put("patientInfo", PatientInfoVo);
//            }else {
                dataMap.put("patientInfo", planPatient);
//            }

        }

        //查询计划信息
        if (planNum != null && !"".equals(planNum)) {
            switch (planType) {
                case 1://随访和宣教
                {
                    PlanInfoVo planInfoVo = planInfoMapper.findPlanInfoById(planNum);
                    dataMap.put("planInfo", planInfoVo);
                }
                break;
                case 2://满意度
                    SatisfyPlanVo satisfyPlan = satisfyPlanMapper.findSatisfyPlanById(planNum);
                    dataMap.put("planInfo", satisfyPlan);
                    break;
                default:
                    break;
            }
        }
        return ReturnFomart.retParam(200, dataMap);
    }

    private Map getRecord(String patientId, Integer patientSource, Map dataMap) {
        Map paraMap = new HashMap();
        paraMap.put("patientId", patientId);
        paraMap.put("inOut", patientSource);

        Map patientMap = new HashMap();
        switch (patientSource) {
            case 2: {//出院
//                if (PatientInfoVo == null){//没有本地患者信息  调用远程患者信息
                patientMap = managerPatientClient.findInHosPatientById(patientId + "");
//                }
                List<Map> inHosRecord = managerPatientClient.findInHosRecordById(paraMap);
                dataMap.put("record", inHosRecord);
            }
            break;
            case 3: {//门诊
//                if (PatientInfoVo == null){//没有本地患者信息  调用远程患者信息
                patientMap = managerPatientClient.findOutHosPatientById(patientId + "");
//                }
                List<Map> outHosRecord = managerPatientClient.findOutHosRecordById(patientId + "");
                dataMap.put("record", outHosRecord);
            }
            break;
            case 1: {//在院
//                if (PatientInfoVo == null){//没有本地患者信息  调用远程患者信息
                patientMap = managerPatientClient.findInHosPatientById(patientId + "");
//                }
                List<Map> inHosRecord = managerPatientClient.findInHosRecordById(paraMap);
                dataMap.put("record", inHosRecord);

            }
            break;
        }
        dataMap.put("patientInfo", patientMap);
        return dataMap;
    }

    @Override
    public DataOutResponse findListByPlanId(String planNum) {
        //获取计划信息
        PlanInfoVo planInfo = planInfoMapper.findPlanInfoById(planNum);
        log.info("计划信息为:" + planNum);
        if (planInfo == null) {//计划为空
            ExceptionCast.cast(PlanResponseCode.Data_ERROR);
        }

        List<PlanPatientVo> planPatientList = planPatientMapper.findPlanPatientList(planNum, 0, null);
        return ReturnFomart.retParam(200, planPatientList);
    }

    @Override
    public DataOutResponse updatePlanPatient(Long patientPlanId, Integer followType, String handleSugges, FormRecordVo formRecordVo) {
        PlanPatientVo patient = planPatientMapper.findPlanPatientById(patientPlanId);
        if (patient == null) {
            return ReturnFomart.retParam(300, "信息不存在");
        }
        if (patient.getFormStatus() == 1) {//表单完成状态
            formRecordMapper.addFormRecord(formRecordVo);//添加表单记录
            patient.setFormStatus(2);//改成已添加
        }

        if (followType != null) {
            patient.setPlanPatsStatus(followType);
        }
        if (handleSugges != null) {
            patient.setHandleSugges(handleSugges);
        }
        planPatientMapper.updatePlanPatient(patient);
        return ReturnFomart.retParam(200, "保存成功");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DataOutResponse updateStatus(Long planPatientId, Integer status) {
        PlanPatientVo planPatient = planPatientMapper.findPlanPatientById(planPatientId);
        if (planPatient == null){
            return ReturnFomart.retParam(2011,"数据不存在");
        }
        planPatient.setPlanPatsStatus(status);
        planPatientMapper.updatePlanPatient(planPatient);
        return ReturnFomart.retParam(200,planPatient);
    }


}
