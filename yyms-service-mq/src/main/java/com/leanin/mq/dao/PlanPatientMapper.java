package com.leanin.mq.dao;


import com.leanin.domain.vo.PlanPatientVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Administrator
 */
@Mapper
public interface PlanPatientMapper {

    /**
     * 修改计划中病人状态
     * @param patientPlanId
     * @return
     */
    int updatePatientStatus(String patientPlanId, @Param("status") Integer status);

    /**
     * 移入计划病人
     * @param patientInfo
     * @return
     */
    int addPlanPatient(PlanPatientVo patientInfo);

//    /**
//     * 批量移入计划病人
//     * @param patientInfo
//     * @param planNum
//     * @return
//     */
//    int addPlanPatientMap(List<Map> patientInfo, String planNum);

    /**
     * 查询单个病人信息
     * @param patientPlanId
     * @return
     */
    PlanPatientVo findPlanPatientById(Long patientPlanId);

    /**
     * 根据 计划id 和 状态 查询患者信息
     * @param planNum
     * @param status
     * @return
     */
    List<PlanPatientVo> findPlanPatientList(@Param("planNum") String planNum, @Param("status") Integer status);
}