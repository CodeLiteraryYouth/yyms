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
     * 查询计划列表信息
     * @return
     */
    @GetMapping("/follow/plan/findPlanList")
    DataOutResponse findPlanList();

    /**
     * 根据计划单号查询病人列表
     * @param planNum
     * @return
     */
    @GetMapping("/follow/planPatient/findPlanPatientList")
    DataOutResponse findPlanPatientList(String planNum);


}
