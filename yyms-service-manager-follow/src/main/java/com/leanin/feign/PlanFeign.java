package com.leanin.feign;

import com.leanin.domain.response.DataOutResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(value = "yyms-service-manager-patient")
public interface PlanFeign {

    /**
     * 查询在院出院病人信息
     * @param mapParam
     * @return
     */
    @PostMapping(value = "managerPatient/findPatientList")
    DataOutResponse findPatientByParam(@RequestBody Map mapParam);

    /**
     * 查询门诊病人信息
     * @param mapParam
     * @return
     */
    @PostMapping(value = "managerPatient/findOutHosPatientByParam")
    DataOutResponse findOutPatientByParam(@RequestBody Map mapParam);
}
