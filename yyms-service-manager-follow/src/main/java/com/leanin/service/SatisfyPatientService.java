package com.leanin.service;

import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.vo.SatisfyInfoVo;
import com.leanin.domain.vo.SatisfyPatientVo;
import com.leanin.domain.vo.StyInfoRecordVo;

import java.util.Date;
import java.util.List;

public interface SatisfyPatientService {

    //条件查询 满意度计划患者信息
    DataOutResponse findList(Integer currentPage, Integer pageSize, String satisfyPlanNum,Integer sendType, String patientWard, String patientName,
                             Date startDate, Date endDate,Integer finishType,Integer patientSource);

    //批量删除
    DataOutResponse delPatientList(List<Long> longs1);

    DataOutResponse updateByPid(Long patientSatisfyId, Integer finishType,String suggess, StyInfoRecordVo styInfoRecordVo);

    DataOutResponse addPatentList(List<SatisfyPatientVo> satisfyPatientVos);

    DataOutResponse updateStatus(Long planPatientId, Integer status);
}
