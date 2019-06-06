package com.leanin.service;

import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.vo.FormRecordVo;

public interface FormRecordService {

    DataOutResponse addFormRecord(FormRecordVo formRecordVo);

    DataOutResponse findFormRecordByPid(Long patientPlanId);

    DataOutResponse findById(Long formRecordId);

    DataOutResponse findByPlanNUmAndCount(String planNum, Integer count);

    /*DataOutResponse findFormByplanPatientId(Long planPatientId);*/
}
