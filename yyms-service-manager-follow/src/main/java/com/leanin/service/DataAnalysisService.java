package com.leanin.service;

import com.leanin.domain.response.DataOutResponse;

public interface DataAnalysisService {

    DataOutResponse followAnalysis(Integer patientSource, String planNum, String dept, String startDate, String endDate,Integer planType,Integer formStatus,Long userId,Integer isAll);

    DataOutResponse userFollowAnalysis(Long userId,String time,Integer planType);

    DataOutResponse callBackAnalysis(Integer type, Integer dealStatus);
}
