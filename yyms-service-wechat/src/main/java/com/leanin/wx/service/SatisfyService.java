package com.leanin.wx.service;


import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.vo.SatisfyInfoVo;

public interface SatisfyService {

	DataOutResponse findStyInfoByOpenId(String openId, Integer finishType);
}
