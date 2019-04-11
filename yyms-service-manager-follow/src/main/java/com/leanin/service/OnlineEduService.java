package com.leanin.service;

import com.leanin.domain.response.DataOutResponse;

public interface OnlineEduService {

    DataOutResponse findListByParam(Integer currentPage, Integer pageSize, String dept, Integer formStatus, Integer sendStatus, String patientName, String sendName,String patientId);

    DataOutResponse updateFormStatus(String eduId, Integer formStatus);
}
