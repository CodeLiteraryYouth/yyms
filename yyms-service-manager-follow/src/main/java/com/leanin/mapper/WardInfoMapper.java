package com.leanin.mapper;

import com.leanin.domain.vo.WardInfoVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface WardInfoMapper {

	/**
	 * 查询科室列表
	 * @param wardName
	 * @return
	 */
	List<WardInfoVo> findWardList(@Param("wardName") String wardName);
	
	/**
	 * 注销科室
	 * @param wardId
	 * @return
	 */
    int deleteById(@Param("wardId") Long wardId, @Param("status") int status);

    /**
      * 新增科室
     * @param record
     * @return
     */
    int insertWardInfo(WardInfoVo record);

    /**
     * 查询单个科室
     * @param wardId
     * @return
     */
	WardInfoVo selectById(Long wardId);
    
    /**
     * 根据科室代码查询科室信息
     * @param wardCode
     * @return
     */
	WardInfoVo selectByCode(@Param("wardCode") String wardCode);

    /**
     * 编辑科室
     * @param record
     * @return
     */
    int updateWardInfo(WardInfoVo record);


    List<WardInfoVo> findByUserId(@Param("userId") Long userId);

	List<WardInfoVo> findlist(@Param("wardName") String wardName);
}