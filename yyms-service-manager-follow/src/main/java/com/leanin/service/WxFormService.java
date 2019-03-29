package com.leanin.service;

import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.vo.FormRecordVo;
import com.leanin.domain.vo.StyInfoRecordVo;

public interface WxFormService {

    DataOutResponse getWxForm(Integer planType, String formNum,Long planPatientId);

    DataOutResponse addFollowForm(FormRecordVo formRecordVo);

    DataOutResponse addStyForm(StyInfoRecordVo styInfoRecordVo);

}
