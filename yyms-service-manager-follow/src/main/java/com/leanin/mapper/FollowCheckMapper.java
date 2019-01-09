package com.leanin.mapper;

import com.leanin.domain.vo.FollowCheckVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FollowCheckMapper {
	
	/**
	 * 查询抽查计划列表
	 * @param checkName
	 * @return
	 */
	List<FollowCheckVo> findCheckList(@Param("checkName") String checkName);
	/**
	 * 修改抽查计划状态
	 * @param checkNum
	 * @return
	 */
    int updateCheckStatus(@Param("checkNum") String checkNum, @Param("status") int status);

    /**
     * 添加抽查计划信息
     * @param record
     * @return
     */
    int addCheckInfo(FollowCheckVo record);

    /**
     * 根据ID查询随访计划
     * @param checkNum
     * @return
     */
	FollowCheckVo findCheckById(String checkNum);
    
    /**
     * 根据名字查询抽查计划信息
     * @param checkName
     * @return
     */
	FollowCheckVo findCheckByName(@Param("checkName") String checkName);

    /**
     * 修改随访计划信息
     * @param record
     * @return
     */
    int updateCheckInfo(FollowCheckVo record);

}