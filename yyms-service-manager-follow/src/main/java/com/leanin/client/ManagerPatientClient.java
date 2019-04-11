package com.leanin.client;

import com.leanin.domain.response.DataOutResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@FeignClient(value = "yyms-service-manager-patient",fallback = FeignClientFallback.class)//制定远程调用的服务名
public interface ManagerPatientClient {


    //给随访提供接口，出住院病人信息
    @PostMapping("/managerPatient/findOutHosPatientByParamToSF")
    public Map findOutHosPatientByParamToSF(@RequestBody Map paramMap);

    //给随访提供接口，门诊病人信息
    @PostMapping("/managerPatient/findInHosPatientByParamToSF")
    public Map findInHosPatientByParamToSF(@RequestBody Map paramMap);


    //给随访提供接口，根据病人id查询 出住院记录
    @GetMapping("/managerPatient/findInHosPatientById")
    public Map findInHosPatientById(@RequestParam(value = "patientId",required = true)String patientId);

    //给随访提供接口，根据病人id查询 出住院记录
    @PostMapping("/managerPatient/findInHosRecordById")
    public List<Map> findInHosRecordById(@RequestBody Map paraMap);

    //给随访提供接口，根据病人id查询 门诊记录
    @GetMapping("/managerPatient/findOutHosPatientById")
    public Map findOutHosPatientById(@RequestParam(value = "patientId",required = true) String patientId);

    //给随访提供接口，根据病人id查询 门诊记录
    @GetMapping("/managerPatient/findOutHosRecordById")
    public List<Map> findOutHosRecordById(@RequestParam(value = "patientId",required = true) String patientId);


    @GetMapping("/user/findUserName")
    public String findUserName(@RequestParam("adminId") Long adminId);

}
