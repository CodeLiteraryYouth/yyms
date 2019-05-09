package com.leanin.service;


import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.vo.SatisfyInfoVo;

public interface SatisfyService {

	/**
	 * 查询满意度表单列表
	 * @return
	 */
	DataOutResponse findSatisfyList(Integer page, Integer pageSize, Long typeId, String satisfyName,Integer shareStatus);
	/**
	 * 注销满意度表单
	 * @param satisfyNum
	 * @return
	 */
	DataOutResponse logoutSatisfyInfo(String satisfyNum);

    /**
     * 增加满意度表单类型
     * @param record
     * @return
     */
	DataOutResponse addSatisfyInfo(SatisfyInfoVo record);

    /**
     * 根据表单号查询表单数据
     * @param satisfyNum
     * @return
     */
	DataOutResponse findSatisfyById(String satisfyNum);

    /**
     * 编辑满意度表单数据
     * @param record
     * @return
     */
	DataOutResponse updateSatisfyInfo(SatisfyInfoVo record);

	DataOutResponse findStyInfoByOpenId(String openId, Integer finishType,Integer formStatus);

    DataOutResponse shareSatisfy(String satisfyNum, Integer status);
}
