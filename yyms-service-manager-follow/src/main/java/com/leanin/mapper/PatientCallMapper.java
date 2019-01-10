package com.leanin.mapper;


import com.leanin.domain.vo.PatientCallVo;

public interface PatientCallMapper {
    int deleteByPrimaryKey(Long callPatientId);

    int insert(PatientCallVo record);

    int insertSelective(PatientCallVo record);

    PatientCallVo selectByPrimaryKey(Long callPatientId);

    int updateByPrimaryKeySelective(PatientCallVo record);

    int updateByPrimaryKey(PatientCallVo record);
}