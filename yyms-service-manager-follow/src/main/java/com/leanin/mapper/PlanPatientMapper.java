package com.leanin.mapper;


import com.leanin.domain.dto.PatientInfoDto;
import com.leanin.domain.vo.PlanPatientVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Administrator
 */
public interface PlanPatientMapper {

    /**
     * 修改计划中病人状态
     * @param patientPlanId
     * @return
     */
    int updatePatientStatus(String patientPlanId,@Param("status") Integer status);

    /**
     * 批量移入计划病人
     * @param patientInfo
     * @param planNum
     * @return
     */
    int addPlanPatient(List<PatientInfoDto> patientInfo, String planNum);

    /**
     * 查询单个病人信息
     * @param patientPlanId
     * @return
     */
    PlanPatientVo findPlanPatientById(Long patientPlanId);

    /**
     * 查询病人名单列表
     * @param patientName
     * @param status
     * @return
     */
    List<PlanPatientVo> findPlanPatientList(@Param("patientName") String patientName,@Param("status") Integer status);
}