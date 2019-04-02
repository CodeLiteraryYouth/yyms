package com.leanin.service;

import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.vo.StyInfoRecordVo;

public interface StyInfoRecordService {

    DataOutResponse addStyInfoRecord(StyInfoRecordVo styInfoRecordVo);

    DataOutResponse findStyInfoRecordByPid(Long planPatientId);
}
