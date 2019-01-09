package com.leanin.mapper;


import com.leanin.domain.vo.MessagePatientVo;

public interface MessagePatientMapper {
    int deleteByPrimaryKey(Long patientMsgId);

    int insert(MessagePatientVo record);

    int insertSelective(MessagePatientVo record);

    MessagePatientVo selectByPrimaryKey(Long patientMsgId);

    int updateByPrimaryKeySelective(MessagePatientVo record);

    int updateByPrimaryKey(MessagePatientVo record);
}