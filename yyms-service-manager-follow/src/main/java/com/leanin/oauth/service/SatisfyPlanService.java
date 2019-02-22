package com.leanin.oauth.service;

import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.vo.SatisfyPlanVo;

public interface SatisfyPlanService {

	/**
	 * 查询满意度计划列表
	 * @param satisfyPlanName
	 * @return
	 */
	DataOutResponse findSatisfyPlanList(int page, int pageSize, String satisfyPlanName);
	/**
	 * 更改满意度计划状态
	 * @param planSatisfyNum
	 * @param status
	 * @return
	 */
	DataOutResponse updateSatisfyStatus(String planSatisfyNum, int status);
    
    /**
     * 增加满意度计划
     * @param record
     * @return
     */
	DataOutResponse addSatisfyPlan(SatisfyPlanVo record);

    /**
     * 根据ID查询满意度计划信息
     * @param planSatisfyNum
     * @return
     */
	DataOutResponse findSatisfyPlanById(String planSatisfyNum);

    /**
     * 修改满意度计划信息
     * @param record
     * @return
     */
	DataOutResponse updateSatisfyPlan(SatisfyPlanVo record);
}
