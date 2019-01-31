package com.leanin.mq.dao;


import com.leanin.domain.dto.CommonRulesInfoDto;
import com.leanin.domain.vo.RulesInfoVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface RulesInfoMapper {

	/**
	 * 查询共享规则信息
	 * @param rulesType
	 * @param rulesName
	 * @return
	 */
	List<CommonRulesInfoDto> findCommonRules(@Param("rulesType") Integer rulesType, @Param("rulesName") String rulesName);
	/**
	 * 查询规则列表信息
	 * @param rulesName
	 * @param type
	 * @return
	 */
	List<RulesInfoVo> findRulesList(@Param("rulesName") String rulesName, @Param("type") Integer type, @Param("typeId") Long typeId);

	/**
	 * 根据院区编码查询共享列表信息
	 * @param wardCode
	 * @param rulesType
	 * @param rulesName
	 * @return
	 */
	List<RulesInfoVo> findRulesByWardCode(@Param("wardCode") String wardCode, @Param("rulesType") Integer rulesType, @Param("rulesName") String rulesName);
	/**
	 * 修改规则状态
	 * @param rulesInfoId
	 * @param status
	 * @return
	 */
    int updateRulesState(@Param("rulesInfoId") Long rulesInfoId, @Param("status") Integer status);

    /**
     * 增加规则信息
     * @param record
     * @return
     */
    int addRulesInfo(RulesInfoVo record);

    /**
     * 根绝ID查规则信息
     * @param rulesInfoId
     * @return
     */
	RulesInfoVo findRulesById(@Param("rulesInfoId") Long rulesInfoId);

    /**
     * 根据规则名称查询规则信息
     * @param rulesName
     * @return
     */
	RulesInfoVo findRulesByName(@Param("rulesName") String rulesName);
    /**
     * 编辑规则信息
     * @param record
     * @return
     */
    int updateRulesInfo(RulesInfoVo record);
    
}