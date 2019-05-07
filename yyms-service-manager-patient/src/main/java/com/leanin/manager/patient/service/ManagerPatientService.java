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
    Map findOutHosPatientByParamToSF(Map paramMap);


    //给随访提供接口，门诊病人信息
    Map findInHosPatientByParamToSF(Map paramMap);

    //给随访提供接口，根据病人id查询 出住院记录
    List<Map> findInHosRecordById(Map paraMap);

    //给随访提供接口，根据病人id查询 门诊记录
    List<Map> findOutHosRecordById(String patientId);

    Map findInHosPatientById(String patientId);

    Map findOutHosPatientById(String patientId);

    /**
     * 查询预约列表
     * @param paramMap
     * @return
     */
    DataOutResponse findRegList(Map paramMap);

    /**
     * 增加已预约的人数
     * @param paramMap
     * @return
     */
    DataOutResponse updateRegNum(Map paramMap);

    /**
     * 查询医生的科室列表
     * @return
     */
    DataOutResponse findDoctorDept();
}
