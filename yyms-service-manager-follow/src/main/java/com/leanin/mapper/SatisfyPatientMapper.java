package com.leanin.mapper;


import com.leanin.domain.vo.SatisfyPatientVo;

public interface SatisfyPatientMapper {
    int deleteByPrimaryKey(Long patientSatisfyId);

    int insert(SatisfyPatientVo record);

    int insertSelective(SatisfyPatientVo record);

    SatisfyPatientVo selectByPrimaryKey(Long patientSatisfyId);

    int updateByPrimaryKeySelective(SatisfyPatientVo record);

    int updateByPrimaryKey(SatisfyPatientVo record);
}