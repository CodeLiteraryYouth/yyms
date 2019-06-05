package com.leanin.service;


import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.vo.PlanInfoVo;
import com.leanin.domain.vo.RulesInfoVo;
import com.leanin.model.response.ResponseResult;

public interface PlanInfoService {

	/**
	 * 查询计划列表
	 * @param planName
	 * @return
	 */
	DataOutResponse findPlanList(int page, int pageSize, String planName, int planType,Long userId);
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
	ResponseResult addPlanInfo(PlanInfoVo record, RulesInfoVo rulesInfoVo);

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

	/**
	 * 根据计划名称查询计划
	 * @param planName
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	DataOutResponse findPlanInfoByPlanName(String planName, Integer currentPage, Integer pageSize);

	//根据计划类型  查询 计划信息
    DataOutResponse findPlanListByType(Integer planType);

    //查询所有计划信息
    DataOutResponse findAllPlan(Integer planType,Long userId);

    DataOutResponse findByWard(String patientWard);

    DataOutResponse findFollowPlanByParam(String planName, String deptId, Long userId, Integer rulesType, String startDate, String endDate,Integer page,Integer pageSize);

}
