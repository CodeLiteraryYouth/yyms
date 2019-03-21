package com.leanin.mapper;

import com.leanin.domain.vo.SatisfyInfoExt;
import com.leanin.domain.vo.SatisfyInfoVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SatisfyInfoMapper {
	
	/**
	 * 查询满意度表单列表
	 * @return
	 */
	List<SatisfyInfoVo> findSatisfyList(@Param("typeId") Long typeId, @Param("satisfyName") String satisfyName);
	/**
	 * 注销满意度表单
	 * @param satisfyNum
	 * @return
	 */
    int logoutSatisfyInfo(String satisfyNum);

    /**
     * 增加满意度表单类型
     * @param record
     * @return
     */
    int addSatisfyInfo(SatisfyInfoVo record);

    /**
     * 根据表单号查询表单数据
     * @param satisfyNum
     * @return
     */
	SatisfyInfoVo findSatisfyById(String satisfyNum);
    
    /**
     * 根据名字查询表单信息
     * @param satisfyName
     * @return
     */
	SatisfyInfoVo findSatisfyByName(String satisfyName);

    /**
     * 编辑满意度表单数据
     * @param record
     * @return
     */
    int updateSatisfyInfo(SatisfyInfoVo record);

	List<SatisfyInfoExt> findStyInfoByOpenId(@Param("openId") String openId,@Param("finishType") Integer finishType);

	List<SatisfyInfoExt> findStyInfoByOpenIdExt(@Param("openId")String openId,@Param("finishType") Integer finishType);
}