package com.leanin.oauth.service;

import com.leanin.domain.response.DataOutResponse;

public interface MessagePatientService {
    DataOutResponse findList(Integer currentPage, Integer pageSize, String patientName, Integer sendType);
}
