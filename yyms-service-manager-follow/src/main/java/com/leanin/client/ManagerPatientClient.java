package com.leanin.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

@FeignClient(value = "yyms-service-manager-patient")//制定远程调用的服务名
public interface ManagerPatientClient {


    //给随访提供接口，出住院病人信息
    @PostMapping("/findOutHosPatientByParamToSF")
    public List<Map> findOutHosPatientByParamToSF(@RequestBody Map paramMap);

    //给随访提供接口，门诊病人信息
    @PostMapping("/findInHosPatientByParamToSF")
    public List<Map> findInHosPatientByParamToSF(@RequestBody Map paramMap);
}
