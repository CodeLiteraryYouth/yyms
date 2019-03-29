package com.leanin.wx.service;

import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.vo.FormInfoVo;

/**
 * 表单信息Service
 * @author Administrator
 *
 */
public interface FormInfoService {

	DataOutResponse findFormListByOpenid(String openid, Integer followStatus, Integer planType);
}
