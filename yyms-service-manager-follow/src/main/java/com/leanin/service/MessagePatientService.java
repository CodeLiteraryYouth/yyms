package com.leanin.service;

import com.leanin.domain.response.DataOutResponse;

public interface MessagePatientService {
    DataOutResponse findList(Integer currentPage, Integer pageSize, String patientName, Integer sendType,String msgTopicId);

    DataOutResponse updateStatus(Long planPatientId, Integer status);

    DataOutResponse findByMsgTopicId(Integer currentPage, Integer pageSize, String msgTopicId);
}
