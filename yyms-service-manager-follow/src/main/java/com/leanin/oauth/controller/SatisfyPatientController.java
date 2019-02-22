package com.leanin.oauth.controller;

import com.alibaba.fastjson.JSON;
import com.leanin.domain.response.DataOutResponse;
import com.leanin.oauth.service.SatisfyPatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/satisfyPatient")
public class SatisfyPatientController {

    @Autowired
    SatisfyPatientService satisfyPatientService;

    //条件查询 满意度计划患者信息
    @GetMapping("/findList")
    public DataOutResponse findList( Integer currentPage,  Integer pageSize, String satisfyPlanNum,
                                     Integer sendType,  String patientWard, Integer finishType,
                                     String patientName,  String startDateStr,  String endDateStr) {
        SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd");
        Date startDate =null;
        Date endDate =null;
        if (startDateStr != null ){
            try {
                startDate = sdf.parse(startDateStr);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        if (endDateStr != null ){
            try {
                endDate = sdf.parse(endDateStr);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return satisfyPatientService.findList(currentPage,pageSize,satisfyPlanNum,sendType,patientWard,patientName,startDate,endDate,finishType);
    }

    @GetMapping("/delPatientList")
    public DataOutResponse delPatientList(@RequestParam String patientSatisfyId){
        List<Long> longs1 = JSON.parseArray(patientSatisfyId, Long.class);
//        JSON.parseArray(patientPlanIds, Long[].class);
//        System.out.println(longs1);
//        Long[] longs = JSON.parseObject(patientPlanIds, Long[].class);
//        List<Long> longs = JSON.parseArray(patientPlanIds, Long.class);
//        Long[] longs=new Long[5];
        return satisfyPatientService.delPatientList(longs1);
    }

}
