package com.leanin.service;

import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.vo.StyInfoRecordVo;

public interface StyInfoRecordService {

    DataOutResponse addStyInfoRecord(StyInfoRecordVo styInfoRecordVo);

    DataOutResponse findStyInfoRecordByPid(Long planPatientId);

    DataOutResponse findById(String satisfyRecordId);

    DataOutResponse findByPlanNumAndCount(String planNum, Integer count);

    DataOutResponse findByIdCard(String idCard, Integer page, Integer pageSize);
}
