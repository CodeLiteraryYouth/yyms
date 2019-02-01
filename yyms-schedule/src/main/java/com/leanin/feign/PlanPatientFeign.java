package com.leanin.feign;

import com.leanin.domain.response.DataOutResponse;
import org.hibernate.mapping.Map;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Feign调用客户端
 * @author Administrator
 */
@FeignClient(value="yyms-service-manager-follow",fallback = PlanPatientFeign.class)
public interface PlanPatientFeign {

    /**
     * 根据计划ID查询病人信息
     * @param paramMap
     * @return
     */
    @GetMapping("/follow/planPatient/findPlanPatientListByPlanId")
    DataOutResponse findPatientByPlanId(Map paramMap);
}
