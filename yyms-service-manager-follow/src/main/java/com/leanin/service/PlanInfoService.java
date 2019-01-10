package com.leanin.service;


import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.vo.PlanInfoVo;

public interface PlanInfoService {

	/**
	 * 查询计划列表
	 * @param planName
	 * @return
	 */
	DataOutResponse findPlanList(int page, int pageSize, String planName, int planType);
	/**
	 * 修改计划状态
	 * @param planNum
	 * @return
	 */
	DataOutResponse updatePlanStatus(String planNum, int status);

    /**
     * 添加计划信息
     * @param record
     * @return
     */
	DataOutResponse addPlanInfo(PlanInfoVo record);

    /**
     * 查询单个计划信息
     * @param planNum
     * @return
     */
	DataOutResponse findPlanInfoById(String planNum);

    /**
     * 编辑计划信息
     * @param record
     * @return
     */
	DataOutResponse updatePlanInfo(PlanInfoVo record);
}
