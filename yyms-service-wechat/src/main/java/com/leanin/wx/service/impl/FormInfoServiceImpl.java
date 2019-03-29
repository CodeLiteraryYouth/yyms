package com.leanin.wx.service.impl;
import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.response.ReturnFomart;
import com.leanin.domain.vo.FormInfoExt;
import com.leanin.wx.mapper.FormInfoMapper;
import com.leanin.wx.service.FormInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class FormInfoServiceImpl implements FormInfoService {

	@Autowired
	private FormInfoMapper formInfoMapper;

	@Override
	public DataOutResponse findFormListByOpenid(String openid,Integer followStatus,Integer planType) {
		if (openid == null){
			return ReturnFomart.retParam(300,"请登录后在进行查询");
		}
		List<FormInfoExt> list = formInfoMapper.findFormListByOpenid(openid,followStatus,planType);
		if (followStatus != 1){//未完成
			List<FormInfoExt> formInfoExts =formInfoMapper.findFormListByOpenidExt(openid,followStatus,planType);
			list.addAll(formInfoExts);
		}

		return ReturnFomart.retParam(200, list);
	}





}
