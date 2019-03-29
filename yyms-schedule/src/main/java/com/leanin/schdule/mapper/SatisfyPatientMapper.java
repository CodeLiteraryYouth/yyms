package com.leanin.schdule.mapper;


import com.leanin.domain.vo.SatisfyPatientVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Mapper
public interface SatisfyPatientMapper {

//    int deleteByPrimaryKey(Long patientSatisfyId);

//    int insert(SatisfyPatientVo record);

//    int insertSelective(SatisfyPatientVo record);

//    SatisfyPatientVo selectByPrimaryKey(Long patientSatisfyId);

    int updateByPrimaryKeySelective(SatisfyPatientVo record);

//    int updateByPrimaryKey(SatisfyPatientVo record);

    List<SatisfyPatientVo> findList(@Param("satisfyPlanNum") String satisfyPlanNum, @Param("sendType") Integer sendType,
                                    @Param("patientWard") String patientWard, @Param("patientName") String patientName,
                                    @Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("finishType") Integer finishType);

//    void updatePatientStatus(@Param("patientSatisfyId") Long patientSatisfyId);

//    Integer findUnfinishCount(@Param("satisfyPlanNum") String satisfyPlanNum);
}