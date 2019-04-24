package com.leanin.service;

import com.leanin.domain.CallBackConfig;
import com.leanin.domain.response.DataOutResponse;

public interface CallBackConfigService {

	/**
	 * 查询所有投诉表扬的配置
	 * @return
	 */
	DataOutResponse findConfigListByType(Integer configType);
	/**
	 * 更新投诉表扬状态配置状态
	 * @param configNum
	 * @return
	 */
	DataOutResponse updateConfigStatus(String configNum, int status);
    
    /**
     * 增加投诉表扬配置
     * @return
     */
	DataOutResponse addConfig(CallBackConfig record);

    /**
     * 根据ID查询投诉表扬信息
     * @param configNum
     * @return
     */
	DataOutResponse findConfigById(String configNum);

    /**
     * 修改投诉表扬配置信息
     * @param record
     * @return
     */
	DataOutResponse updateConfig(CallBackConfig record);

	DataOutResponse findConfigList(Integer page, Integer size, Integer configType);

	DataOutResponse findDealNameByType(Integer type,Integer status);
}
