package com.leanin.mapper;


import com.leanin.domain.analysis.DeptAnalysis;
import com.leanin.domain.common.AnalysisVo;
import com.leanin.domain.request.MyFollowReq;
import com.leanin.domain.vo.PlanPatientVo;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
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
    int updatePatientStatus(@Param("patientPlanId") String patientPlanId,@Param("status") Integer status);

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
    List<PlanPatientVo> findPlanPatientList(@Param("planNum") String planNum,@Param("status") Integer status,@Param("patientName")String patientName);

    void updatePatientStatusById(@Param("patientPlanId") Long patientPlanId);

    /**
     * 根据patientId查询 患者信息
     * @param patientId
     * @return
     */
    PlanPatientVo findPlanPatientByPatientId(@Param("patientId") Long patientId);

    PlanPatientVo findPlanPatientByPIdAndPNum(@Param("patientId") Long patientId,@Param("planNum") String planNum);

    /*
     * 待随访人数
     */
    Integer findUnfinishCount(@Param("planNum") String planNum);

    /*
     * 已完成随访人数
     */
    Integer findFinishCount(@Param("planNum") String planNum);

    /*
     * 过期随访人数
     */
    Integer findPastCount(@Param("planNum") String planNum);

    /*
     * 过期随访人数
     */
    Integer findDeadCount(@Param("planNum") String planNum);

    void updatePlanPatient(@Param("planPatientVo") PlanPatientVo planPatientVo);


    List<PlanPatientVo> findPatsByParam(@Param("myFollowReq") MyFollowReq myFollowReq);

    List<PlanPatientVo> bindPatient(@Param("idCard") String idCard,@Param("patientName") String patientName,@Param("phoneNum") String phoneNum);

    List<PlanPatientVo> findListByPlanNum(@Param("planNums") String[] planNums);

    PlanPatientVo findByPnumAndPid(@Param("patientId") Long patientId,@Param("planNum") String planNum);

    List<AnalysisVo> findCountByParam(@Param("patientSource") Integer patientSource, @Param("planNum") String planNum, @Param("dept") String dept, @Param("startDate") Date startDate, @Param("endDate") Date endDate/*, @Param("status") int status*/,@Param("planType") Integer planType,@Param("formStatus") Integer formStatus,@Param("userId")Long userId);

    List<AnalysisVo> findUserCount(@Param("userId") Long userId,@Param("time") String time);

    List<DeptAnalysis> deptFollowAnalysis(@Param("patientSource") Integer patientSource,@Param("planNum") String planNum,@Param("dept") String dept,@Param("startDate") String startDate,@Param("endDate") String endDate);

}