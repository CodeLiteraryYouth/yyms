package com.leanin.service;

import com.leanin.domain.response.DataOutResponse;

public interface CheckPatientService {
    DataOutResponse findList(Integer currentPage,Integer pageSize,String patientName, Integer finishStatus,String checkNUm);

    DataOutResponse findById(Long checkPatientId);

    DataOutResponse delByIds(String ids);
}
