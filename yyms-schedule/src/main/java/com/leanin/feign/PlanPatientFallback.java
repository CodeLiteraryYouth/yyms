package com.leanin.feign;

import com.leanin.domain.response.DataOutResponse;
import org.hibernate.mapping.Map;
import org.springframework.stereotype.Component;

/**
 * 当定时任务调用此方法时发生服务异常执行此异常方法
 * @author Administrator
 */
@Component
public class PlanPatientFallback implements PlanPatientFeign {

    @Override
    public DataOutResponse findPatientByPlanId(Map paramMap) {
        return null;
    }
}
