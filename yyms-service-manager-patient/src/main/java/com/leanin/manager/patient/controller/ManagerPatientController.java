package com.leanin.manager.patient.controller;


import com.leanin.api.patient.ManagerPatientApi;
import com.leanin.domain.response.DataOutResponse;
import com.leanin.manager.patient.service.ManagerPatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public Map findOutHosPatientByParamToSF(@RequestBody Map paramMap) {
        return managerPatientService.findOutHosPatientByParamToSF(paramMap);
    }
    //给随访提供接口，门诊病人信息
    @Override
    @PostMapping("/findInHosPatientByParamToSF")
    public Map findInHosPatientByParamToSF(@RequestBody Map paramMap) {
        return managerPatientService.findInHosPatientByParamToSF(paramMap);
    }

    //给随访提供接口，根据病人id查询 出住院记录
    @Override
    @GetMapping("findInHosPatientById")
    public Map findInHosPatientById(@RequestParam(value = "patientId",required = true)String patientId) {

        return managerPatientService.findInHosPatientById(patientId);
    }

    //给随访提供接口，根据病人id查询 出住院记录
    @Override
    @PostMapping("findInHosRecordById")
    public List<Map> findInHosRecordById(@RequestBody Map paraMap) {
        return managerPatientService.findInHosRecordById(paraMap);
    }

    //给随访提供接口，根据病人id查询 门诊记录
    @Override
    @GetMapping("findOutHosPatientById")
    public Map findOutHosPatientById(@RequestParam(value = "patientId",required = true) String patientId) {
        return managerPatientService.findOutHosPatientById(patientId);
    }

    //给随访提供接口，根据病人id查询 门诊记录
    @Override
    @GetMapping("findOutHosRecordById")
    public List<Map> findOutHosRecordById(@RequestParam(value = "patientId",required = true) String patientId) {
        return managerPatientService.findOutHosRecordById(patientId);
    }


}