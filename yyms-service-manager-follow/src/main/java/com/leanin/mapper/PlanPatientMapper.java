package com.leanin.mapper;


import com.leanin.domain.vo.PlanPatientVo;

public interface PlanPatientMapper {
    int deleteByPrimaryKey(Long patientPlanId);

    int insert(PlanPatientVo record);

    int insertSelective(PlanPatientVo record);

    PlanPatientVo selectByPrimaryKey(Long patientPlanId);

    int updateByPrimaryKeySelective(PlanPatientVo record);

    int updateByPrimaryKey(PlanPatientVo record);
}