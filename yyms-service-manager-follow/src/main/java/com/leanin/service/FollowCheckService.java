package com.leanin.service;


import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.vo.FollowCheckVo;

public interface FollowCheckService {

	/**
	 * 查询抽查计划列表
	 * @param checkName
	 * @return
	 */
	DataOutResponse findCheckList(int page, int pageSize, String checkName);
	/**
	 * 修改抽查计划状态
	 * @param checkNum
	 * @return
	 */
	DataOutResponse updateCheckStatus(String checkNum, int status);

    /**
     * 添加抽查计划信息
     * @param record
     * @return
     */
	DataOutResponse addCheckInfo(FollowCheckVo record);

    /**
     * 根据ID查询随访计划
     * @param checkNum
     * @return
     */
	DataOutResponse findCheckById(String checkNum);

    /**
     * 修改随访计划信息
     * @param record
     * @return
     */
	DataOutResponse updateCheckInfo(FollowCheckVo record);
}
