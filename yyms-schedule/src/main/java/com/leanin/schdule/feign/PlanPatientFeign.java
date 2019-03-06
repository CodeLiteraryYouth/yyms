package com.leanin.schdule.feign;

import com.leanin.domain.response.DataOutResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    @GetMapping("/plan/findAllPlan")///follow
    public DataOutResponse findAllPlan();

    /**
     * 根据计划单号查询病人列表
     * @param planNum
     * @return
     */
    @GetMapping("/planPatient/findListByPlanId")///follow
    public DataOutResponse findListByPlanId(@RequestParam(required=true) String planNum);


}
