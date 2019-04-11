package com.leanin.mapper;


import com.leanin.domain.common.AnalysisVo;
import com.leanin.domain.request.MyFollowReq;
import com.leanin.domain.vo.PlanPatientVo;
import com.leanin.domain.vo.SatisfyPatientVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface SatisfyPatientMapper {

    int deleteByPrimaryKey(Long patientSatisfyId);

    int insert(SatisfyPatientVo record);

    int insertSelective(SatisfyPatientVo record);

    SatisfyPatientVo selectByPrimaryKey(Long patientSatisfyId);

    SatisfyPatientVo findByStyPatId(@Param("patientSatisfyId") Long patientSatisfyId);

    int updateByPrimaryKeySelective(SatisfyPatientVo record);

    int updateByPrimaryKey(SatisfyPatientVo record);

    List<SatisfyPatientVo> findList(@Param("satisfyPlanNum") String satisfyPlanNum,@Param("sendType") Integer sendType,
                                    @Param("patientWard") String patientWard, @Param("patientName") String patientName,
                                    @Param("startDate") Date startDate,@Param("endDate") Date endDate,
                                    @Param("finishType") Integer finishType,@Param("patientSource") Integer patientSource);

    void updatePatientStatus(@Param("patientSatisfyId") Long patientSatisfyId);

    Integer findUnfinishCount(@Param("satisfyPlanNum") String satisfyPlanNum);

    List<SatisfyPatientVo> findPatsByParam(@Param("myFollowReq") MyFollowReq myFollowReq);

    List<SatisfyPatientVo> bindPatient(@Param("idCard") String idCard,@Param("patientName") String patientName,@Param("phoneNum") String phoneNum);

    SatisfyPatientVo findByPnumAndPid(@Param("patientId") Long patientId,@Param("satisfyPlanNum") String satisfyPlanNum);

    List<AnalysisVo> findCountByParam(@Param("patientSource") Integer patientSource,@Param("planNum") String planNum,@Param("dept") String dept,@Param("startDate") Date startDate,@Param("endDate") Date endDate);

    List<AnalysisVo> findUserCount(@Param("userId") Long userId,@Param("time") String time);
}