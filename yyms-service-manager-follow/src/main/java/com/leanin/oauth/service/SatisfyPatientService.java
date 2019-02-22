package com.leanin.oauth.service;

import com.leanin.domain.response.DataOutResponse;

import java.util.Date;
import java.util.List;

public interface SatisfyPatientService {

    //条件查询 满意度计划患者信息
    DataOutResponse findList(Integer currentPage, Integer pageSize, String satisfyPlanNum,Integer sendType, String patientWard, String patientName, Date startDate, Date endDate,Integer finishType);

    //批量删除
    DataOutResponse delPatientList(List<Long> longs1);
}
