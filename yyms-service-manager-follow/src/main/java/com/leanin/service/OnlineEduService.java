package com.leanin.service;

import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.vo.PatientInfoEduVo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface OnlineEduService {

    DataOutResponse findListByParam(Integer currentPage, Integer pageSize, String dept, Integer formStatus, Integer sendStatus, String patientName, String sendName,String patientId);

    DataOutResponse updateFormStatus(String eduId, Integer formStatus);

    DataOutResponse sendOnlineEdu(List<PatientInfoEduVo> patientInfoEduVos, HttpServletRequest request);
}
