package com.leanin.mapper;

import com.leanin.domain.analysis.DeptAnalysis;
import com.leanin.domain.common.AnalysisVo;
import com.leanin.domain.vo.FormInfoExt;
import com.leanin.domain.vo.PlanPatientRecordVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

//随访记录表
@Mapper
public interface FollowRecordMapper {

    //添加随访记录
//    void addFollowRecord(@Param("patientRecordVo")PlanPatientRecordVo patientRecordVo);

    //条件查询
    List<PlanPatientRecordVo> findPlanPatientList(@Param("planNum") String planNum,@Param("planPatsStatus") Integer planPatsStatus,@Param("patientName") String patientName);

    //修改患者状态
    void updatePatientStatusById(@Param("patientPlanId") Long patientPlanId);

    List<AnalysisVo> findCountByParam(@Param("patientSource") Integer patientSource, @Param("planNum") String planNum, @Param("dept") String dept, @Param("startDate") Date startDate, @Param("endDate") Date endDate/*, @Param("status") Integer status*/,@Param("planType") Integer planType,@Param("formStatus") Integer formStatus,@Param("userId") Long userId);

    List<AnalysisVo> findUserCount(@Param("userId") Long userId,@Param("time") String time);

    List<DeptAnalysis> deptFollowAnalysis(@Param("patientSource") Integer patientSource,@Param("planNum") String planNum,@Param("dept") String dept,@Param("startDate") String startDate,@Param("endDate") String endDate);

}
