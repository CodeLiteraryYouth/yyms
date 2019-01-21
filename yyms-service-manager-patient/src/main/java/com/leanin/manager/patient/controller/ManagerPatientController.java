package com.leanin.manager.patient.controller;


import com.leanin.api.patient.ManagerPatientApi;
import com.leanin.domain.response.DataOutResponse;
import com.leanin.manager.patient.service.ManagerPatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("/managerPatient")
public class ManagerPatientController implements ManagerPatientApi {

    @Autowired
    ManagerPatientService managerPatientService;

    /**
     * 分页查询出住院患者信息
     * @param paramMap
     * @return
     */
    @Override
    @PostMapping("/findPatientList")
    public DataOutResponse findListByParam(@RequestBody Map paramMap) {
        return managerPatientService.findListByParam(paramMap);
    }

    /**
     * 分页查询就诊患者信息
     * @param paramMap
     * @return
     */
    @Override
    @PostMapping("/findOutHosPatientByParam")
    public DataOutResponse findOutHosPatientByParam(@RequestBody Map paramMap) {
        return managerPatientService.findOutHosPatientByParam(paramMap);
    }
    //给随访提供接口，出住院病人信息
    @Override
    @PostMapping("/findOutHosPatientByParamToSF")
    public List<Map> findOutHosPatientByParamToSF(@RequestBody Map paramMap) {
        return managerPatientService.findOutHosPatientByParamToSF(paramMap);
    }
    //给随访提供接口，门诊病人信息
    @Override
    @PostMapping("/findInHosPatientByParamToSF")
    public List<Map> findInHosPatientByParamToSF(@RequestBody Map paramMap) {
        return managerPatientService.findInHosPatientByParamToSF(paramMap);
    }


}