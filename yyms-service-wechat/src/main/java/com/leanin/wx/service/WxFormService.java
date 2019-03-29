package com.leanin.wx.service;

import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.vo.FormRecordVo;
import com.leanin.domain.vo.StyInfoRecordVo;

public interface WxFormService {

    DataOutResponse getWxForm(Integer planType, String formNum);

    DataOutResponse addFollowForm(FormRecordVo formRecordVo);

    DataOutResponse addStyForm(StyInfoRecordVo styInfoRecordVo);

}
