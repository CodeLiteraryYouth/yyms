package com.leanin.wx.service.impl;

import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.response.ReturnFomart;
import com.leanin.domain.vo.SatisfyInfoExt;
import com.leanin.wx.mapper.SatisfyInfoMapper;
import com.leanin.wx.service.SatisfyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class StaisfyServiceImpl implements SatisfyService {

	@Autowired
	private SatisfyInfoMapper satisfyInfoMapper;

	@Override
	public DataOutResponse findStyInfoByOpenId(String openId, Integer finishType) {
		List<SatisfyInfoExt> list = satisfyInfoMapper.findStyInfoByOpenId(openId,finishType);
		if ( finishType != 1){//1 未完成  2 //已完成  //3过期  //4随访异常
			List<SatisfyInfoExt> satisfyInfoExts =satisfyInfoMapper.findStyInfoByOpenIdExt(openId,finishType);
			list.addAll(satisfyInfoExts);
		}
		return ReturnFomart.retParam(200, list);
	}

}
