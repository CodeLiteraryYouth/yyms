package com.leanin.feign;
import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.response.ReturnFomart;
import org.springframework.stereotype.Component;

/**
 * 当定时任务调用此方法时发生服务异常执行此异常方法
 * @author Administrator
 */
@Component
public class PlanPatientFallback implements PlanPatientFeign {

    @Override
    public DataOutResponse findPlanList() {
        return ReturnFomart.retParam(0,"");
    }

    @Override
    public DataOutResponse findPlanPatientList(String planNum) {
        return ReturnFomart.retParam(0,planNum);
    }


}
