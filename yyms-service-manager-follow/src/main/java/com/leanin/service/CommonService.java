package com.leanin.service;

import com.leanin.domain.common.ImportPatientReq;
import com.leanin.domain.response.DataOutResponse;

import java.util.List;

public interface CommonService {

    DataOutResponse importPatient(List<ImportPatientReq> data, String planNum, Integer planType);

}
