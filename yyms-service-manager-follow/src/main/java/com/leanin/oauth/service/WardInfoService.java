package com.leanin.oauth.service;

import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.vo.WardInfoVo;

public interface WardInfoService {

	/**
	 * 查询科室列表
	 * @param wardName
	 * @return
	 */
	DataOutResponse findWardList(int page, int pageSize, String wardName);

	/**
	 * 注销科室
	 * @param wardId
	 * @return
	 */
	DataOutResponse deleteById(Long wardId, int status);

    /**
      * 新增科室
     * @param record
     * @return
     */
	DataOutResponse insertWardInfo(WardInfoVo record);

    /**
     * 查询单个科室
     * @param wardId
     * @return
     */
	DataOutResponse selectById(Long wardId);

    /**
     * 编辑科室
     * @param record
     * @return
     */
	DataOutResponse updateWardInfo(WardInfoVo record);
}
