package com.leanin.mapper;

import java.util.List;

import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.vo.SatisfyPlanVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
@Mapper
public interface SatisfyPlanMapper {

	/**
	 * 查询满意度计划列表
	 * @param satisfyPlanName
	 * @return
	 */
	List<SatisfyPlanVo> findSatisfyPlanList(@Param("satisfyPlanName") String satisfyPlanName,@Param("uid") Long uid);
	/**
	 * 更改满意度计划状态
	 * @param planSatisfyNum
	 * @param status
	 * @return
	 */
    int updateSatisfyStatus(@Param("planSatisfyNum") String planSatisfyNum, @Param("status") int status);
    
    /**
     * 增加满意度计划
     * @param record
     * @return
     */
    int addSatisfyPlan(SatisfyPlanVo record);

    /**
     * 根据ID查询满意度计划信息
     * @param planSatisfyNum
     * @return
     */
	SatisfyPlanVo findSatisfyPlanById(String planSatisfyNum);
    
    /**
     * 根据名字查询满意度计划信息
     * @param satisfyPlanName
     * @return
     */
	SatisfyPlanVo findSatisfyPlanByName(@Param("satisfyPlanName") String satisfyPlanName);

    /**
     * 修改满意度计划信息
     * @param record
     * @return
     */
    int updateSatisfyPlan(SatisfyPlanVo record);

    String findPNById(@Param("planNum") String planNum);

    List<SatisfyPlanVo> findAll(@Param("userId") Long userId);

    List<SatisfyPlanVo> findByWard(@Param("patientWard") String patientWard);

    SatisfyPlanVo findByParamId(@Param("msgId") String msgId,@Param("formId")String formId);

    List<SatisfyPlanVo> findSatisfyPlanByParam(@Param("planName") String planName,@Param("deptId") String deptId,@Param("userId") Long userId,@Param("startDate") String startDate,@Param("endDate") String endDate);
}