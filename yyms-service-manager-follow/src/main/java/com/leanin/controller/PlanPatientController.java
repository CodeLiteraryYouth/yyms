package com.leanin.controller;

import com.alibaba.fastjson.JSON;
import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.vo.PlanPatientVo;
import com.leanin.service.PlanPatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("planPatient")
public class PlanPatientController {

    @Autowired
    PlanPatientService planPatientService;

    /**
     * 根据planId查询计划患者信息
     * @param planNum
     * @param planPatsStatus
     * @param currentPage
     * @param pageSize
     * @return
     */
    @GetMapping("findPlanPatientListByPlanId")
    public DataOutResponse findPlanPatientListByPlanId(@RequestParam(required=false) String planNum, @RequestParam(required=false) Integer planPatsStatus,
                                                       @RequestParam(required=false) Integer currentPage, @RequestParam(required=false) Integer pageSize,
                                                       @RequestParam(required=false) String patientName){
        return planPatientService.findPlanPatientListByPlanId(planNum,planPatsStatus,currentPage,pageSize,patientName);
    }

    /**
     * 批量删除
     * @param patientPlanIds
     * @return
     */
    @GetMapping("delPatientList")
    public DataOutResponse delPatientList(@RequestParam String patientPlanIds){
        List<Long> longs1 = JSON.parseArray(patientPlanIds, Long.class);
//        JSON.parseArray(patientPlanIds, Long[].class);
//        System.out.println(longs1);
//        Long[] longs = JSON.parseObject(patientPlanIds, Long[].class);
//        List<Long> longs = JSON.parseArray(patientPlanIds, Long.class);
//        Long[] longs=new Long[5];
        return planPatientService.delPatientList(longs1);
    }

    @PostMapping("addPatientList")
    public DataOutResponse addPatientList(@RequestBody List<PlanPatientVo> PlanPatients){
        return planPatientService.addPatientList(PlanPatients);
    }


    /**
     * 我的随访
     * @return
     */
    @GetMapping("findPlanPatientList")
    public DataOutResponse findPlanPatientList(Date startDate,Date endDate,String planNum,String patientName){
        return planPatientService.findPlanPatientList(startDate,endDate,planNum,patientName);
    }

    /**
     * 根据患者id查询患者信息 和病史
     * @param patientId
     * @return
     */
    @GetMapping("findPlanPatientById")
    public DataOutResponse findPlanPatientById(Long patientId,Integer patientSource){
        return planPatientService.findPlanPatientById(patientId,patientSource);
    }




}
