package com.leanin.manager.patient.controller;


import com.leanin.api.patient.ManagerPatientApi;
import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.response.ReturnFomart;
import com.leanin.manager.patient.service.ManagerPatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


}