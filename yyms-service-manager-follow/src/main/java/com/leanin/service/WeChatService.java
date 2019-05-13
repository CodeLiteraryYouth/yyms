package com.leanin.service;

import com.leanin.domain.dao.PatientWxDao;
import com.leanin.domain.request.BindPat;
import com.leanin.domain.response.DataOutResponse;

public interface WeChatService {

    DataOutResponse bindPatient(BindPat bindPat);


    DataOutResponse updatePatientWx(PatientWxDao patientWxDao);
}
