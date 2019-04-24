package com.leanin.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.pagehelper.Page;
import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.response.ReturnFomart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leanin.domain.CallBackConfig;
import com.leanin.mapper.CallBackConfigMapper;
import com.leanin.service.CallBackConfigService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CallBackConfigServiceImpl implements CallBackConfigService {

	@Autowired
	private CallBackConfigMapper callBackConfigMapper;
	
	@Override
	public DataOutResponse findConfigListByType(Integer configType) {
			List<CallBackConfig> configList=callBackConfigMapper.findConfigList(configType);
			PageInfo pageInfo=new PageInfo<>(configList);
			return ReturnFomart.retParam(200, pageInfo);
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public DataOutResponse updateConfigStatus(String configNum, int status) {
		CallBackConfig callBackConfig=callBackConfigMapper.findConfigById(configNum);
		log.info("要修改状态的数据为:"+JSON.toJSONString(callBackConfig));
		callBackConfigMapper.updateConfigStatus(configNum, status);
		return ReturnFomart.retParam(200, callBackConfig);
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public DataOutResponse addConfig(CallBackConfig record) {
		log.info("增加的表扬状态修改的数据为:"+JSON.toJSONString(record));
		CallBackConfig callBackConfig=callBackConfigMapper.findConfigById(record.getConfigNum());
		if(callBackConfig != null) {
			return ReturnFomart.retParam(4000, callBackConfig);
		}
		callBackConfigMapper.addConfig(record);
		return ReturnFomart.retParam(200, record);
	}

	@Override
	public DataOutResponse findConfigById(String configNum) {
		CallBackConfig callBackConfig=callBackConfigMapper.findConfigById(configNum);
		log.info("要修改状态的数据为:"+JSON.toJSONString(callBackConfig));
		return ReturnFomart.retParam(200, callBackConfig);
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public DataOutResponse updateConfig(CallBackConfig record) {
		log.info("修改的投诉表扬配置信息为:"+JSON.toJSONString(record));
		callBackConfigMapper.updateConfig(record);
		CallBackConfig callBackConfig=callBackConfigMapper.findConfigById(record.getConfigNum());
		return ReturnFomart.retParam(200, callBackConfig);
	}

	@Override
	public DataOutResponse findConfigList(Integer page, Integer size, Integer configType) {
		if (page == null || page < 0){
			page=1;
		}
		if (size == null || size < 0){
			size=10;
		}
		PageHelper.startPage(page,size);
		Page<CallBackConfig> pagelist = (Page<CallBackConfig>) callBackConfigMapper.findConfigList(configType);
		Map dataMap = new HashMap();
		dataMap.put("totalCount",pagelist.getTotal());
		dataMap.put("list",pagelist.getResult());
		return ReturnFomart.retParam(200, dataMap);
	}

	@Override
	public DataOutResponse findDealNameByType(Integer type,Integer status) {
		List<CallBackConfig> list = callBackConfigMapper.findDealNameByType(type,status);
		return ReturnFomart.retParam(200,list);
	}

}
