package com.leanin.client;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//熔断器   当服务连接不上时 执行此方法
@Component
public class FeignClientFallback implements ManagerPatientClient {

    @Override
    public Map findOutHosPatientByParamToSF(Map paramMap) {
        Map dataMap=new HashMap();
        dataMap.put("error","error");
        return dataMap;
    }

    @Override
    public Map findInHosPatientByParamToSF(Map paramMap) {
        Map dataMap=new HashMap();
        dataMap.put("error","error");
        return dataMap;
    }

    @Override
    public Map findInHosPatientById(String patientId) {
        return null;
    }

    @Override
    public List<Map> findInHosRecordById(Map patientId) {
        return null;
    }

    @Override
    public Map findOutHosPatientById(String patientId) {
        return null;
    }

    @Override
    public List<Map> findOutHosRecordById(String patientId) {
        return null;
    }
}
