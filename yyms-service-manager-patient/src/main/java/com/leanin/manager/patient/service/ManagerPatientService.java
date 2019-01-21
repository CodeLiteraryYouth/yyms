package com.leanin.manager.patient.service;

import com.leanin.domain.response.DataOutResponse;

import java.util.List;
import java.util.Map;

public interface ManagerPatientService {

    /**
     * 根据条件查询患者信息
     * @param paramMap
     * @return
     */
    DataOutResponse findListByParam(Map paramMap);

    /**
     * 根据条件查询就诊患者的信息
     * @param paramMap
     * @return
     */
    DataOutResponse findOutHosPatientByParam(Map paramMap);

    //给随访提供接口，出住院病人信息
    List<Map> findOutHosPatientByParamToSF(Map paramMap);


    //给随访提供接口，门诊病人信息
    List<Map> findInHosPatientByParamToSF(Map paramMap);
}
