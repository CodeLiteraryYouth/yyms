package com.leanin.service;

import com.leanin.domain.response.DataOutResponse;

public interface MessagePatientService {
    DataOutResponse findList(Integer currentPage, Integer pageSize, String patientName, Integer sendType);

    DataOutResponse updateStatus(Long planPatientId, Integer status);
}
