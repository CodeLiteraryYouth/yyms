package com.leanin.mapper;


import com.leanin.domain.dto.PlanInfoDto;
import com.leanin.domain.vo.PlanInfoVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PlanInfoMapper {
	
	/**
	 * 查询计划列表
	 * @param planName
	 * @return
	 */
	List<PlanInfoDto> findPlanList(@Param("planName") String planName, @Param("planType") int planType);
	/**
	 * 修改计划状态
	 * @param planNum
	 * @return
	 */
    int updatePlanStatus(@Param("planNum") String planNum, int status);

    /**
     * 添加计划信息
     * @param record
     * @return
     */
    int addPlanInfo(PlanInfoVo record);

    /**
     * 查询单个计划信息
     * @param planNum
     * @return
     */
	PlanInfoVo findPlanInfoById(String planNum);
    
    /**
     * 根据计划名字查询计划信息
     * @param planName
     * @return
     */
	PlanInfoVo findPlanInfoByName(String planName);

    /**
     * 编辑计划信息
     * @param record
     * @return
     */
    int updatePlanInfo(PlanInfoVo record);

}