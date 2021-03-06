package com.leanin.service;

import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.vo.FormRecordVo;
import com.leanin.domain.vo.PlanPatientVo;

import java.util.Date;
import java.util.List;

/**
 * 计划患者
 */
public interface PlanPatientService {
    /**
     * 根据planId查询计划患者信息
     * @param planNum
     * @param planPatsStatus
     * @param currentPage
     * @param pageSize
     * @return
     */
    DataOutResponse findPlanPatientListByPlanId(String planNum, Integer planPatsStatus, Integer currentPage, Integer pageSize,String patientName);

    /**
     * 批量删除 患者信息
     * @param patientPlanIds
     * @return
     */
    DataOutResponse delPatientList(List<Long> patientPlanIds,Integer planPatsStatus);

    /**
     * 导入患者信息
     * @param planPatients
     * @return
     */
    DataOutResponse addPatientList(List<PlanPatientVo> planPatients);

    /**
     * 我的随访
     * @param startDate
     * @param endDate
     * @param planNum
     * @param patientName
     * @return
     */
//    DataOutResponse findPlanPatientList(Date startDate, Date endDate, String planNum, String patientName);

    /**
     * 根据patientId查询患者信息和病史
     * @param patientId
     * @return
     */
    DataOutResponse findPlanPatientById(Long patientId,Integer patientSource,String planNum,Integer planType,Integer type);

    //根据计划id 查询全部患者信息
    DataOutResponse findListByPlanId(String planNum);

    //修改随访状态
    DataOutResponse updatePlanPatient(Long patientPlanId, Integer followType, String handleSugges,
                                      FormRecordVo formRecordVo,Integer formStatus);

    DataOutResponse updateStatus(Long planPatientId, Integer status);
    /**
     * 查询his患者信息
     * @param patientId
     * @param patientSource
     * @param planNum
     * @param planType
     * @param type
     * @return
     */
	DataOutResponse findPlanHisPatientById(Long patientId, Integer patientSource);

    DataOutResponse updateFormStatus(Long planPatientId,Integer formStatus);

}
