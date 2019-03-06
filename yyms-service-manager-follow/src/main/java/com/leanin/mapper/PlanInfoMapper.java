package com.leanin.mapper;


import com.leanin.domain.dto.PlanInfoDto;
import com.leanin.domain.vo.PlanInfoVo;
import com.leanin.domain.vo.PlanPatientVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
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
    int updatePlanStatus(@Param("planNum") String planNum,@Param("status") int status);

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

	/**
	 * 根据导入数据状态查询
	 * @param i
	 * @return
	 */
	List<PlanInfoVo> findPlanListByImportData(@Param("importData") int importData);


	/**
	 * 根据计划名称查询 计划信息
	 * @param planName
	 * @return
	 */
    List<PlanInfoVo> findPlanInfoByPlanName(@Param("planName") String planName);

	//根据计划类型查询计划信息
    List<PlanInfoVo> findPlanListByType(@Param("planType") Integer planType);

    //查询所有计划信息
    List<PlanInfoVo> findAllPlan(@Param("planType") Integer planType,@Param("userId") Long userId);

    String findPlanNameById(@Param("planNum") String planNum);

}