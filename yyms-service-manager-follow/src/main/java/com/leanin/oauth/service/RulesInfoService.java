package com.leanin.oauth.service;


import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.vo.RulesInfoVo;

public interface RulesInfoService {

	/**
	 * 查询共享规则信息
	 * @param rulesType
	 * @param rulesName
	 * @return
	 */
	DataOutResponse findCommonRules(Integer rulesType, String rulesName);
	/**
	 * 查询规则列表信息
	 * @param rulesName
	 * @param type
	 * @return
	 */
	DataOutResponse findRulesList(Integer page, Integer pageSize, String rulesName, Integer type, Long typeId);

	/**
	 * 根据院区编码查询共享列表信息
	 * @param wardCode
	 * @param rulesType
	 * @param rulesName
	 * @return
	 */
	DataOutResponse findRulesByWardCode(Integer page, Integer pageSize, String wardCode, Integer rulesType, String rulesName);
	/**
	 * 修改规则状态
	 * @param rulesInfoId
	 * @param status
	 * @return
	 */
	DataOutResponse updateRulesState(Long rulesInfoId, Integer status);

    /**
     * 增加规则信息
     * @param record
     * @return
     */
	DataOutResponse addRulesInfo(RulesInfoVo record);

    /**
     * 根绝ID查规则信息
     * @param rulesInfoId
     * @return
     */
	DataOutResponse findRulesById(Long rulesInfoId);
    /**
     * 编辑规则信息
     * @param record
     * @return
     */
	DataOutResponse updateRulesInfo(RulesInfoVo record);
}
