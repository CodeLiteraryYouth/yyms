package com.leanin.service;

import com.leanin.domain.dao.PatientWxDao;
import com.leanin.domain.request.BindPat;
import com.leanin.domain.response.DataOutResponse;

public interface WeChatService {

    DataOutResponse bindPatient(BindPat bindPat);


    DataOutResponse updatePatientWx(PatientWxDao patientWxDao);

    /**
     * 根据patientId查询病人列表
     * @param patientId
     * @return
     */
    DataOutResponse findHosList(String patientId);
}
