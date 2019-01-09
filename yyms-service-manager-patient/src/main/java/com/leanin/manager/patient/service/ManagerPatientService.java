package com.leanin.manager.patient.service;

import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.response.ReturnFomart;

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
}
