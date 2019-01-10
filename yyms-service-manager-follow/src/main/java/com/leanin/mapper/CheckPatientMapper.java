package com.leanin.mapper;


import com.leanin.domain.vo.CheckPatientVo;

public interface CheckPatientMapper {
    int deleteByPrimaryKey(Long checkPatientId);

    int insert(CheckPatientVo record);

    int insertSelective(CheckPatientVo record);

    CheckPatientVo selectByPrimaryKey(Long checkPatientId);

    int updateByPrimaryKeySelective(CheckPatientVo record);

    int updateByPrimaryKey(CheckPatientVo record);
}