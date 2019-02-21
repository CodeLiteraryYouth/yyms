package com.leanin.mq.dao;


import com.leanin.domain.vo.MessagePatientVo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MessagePatientMapper {

    int deleteByPrimaryKey(Long patientMsgId);

    int insert(MessagePatientVo record);

    int insertSelective(MessagePatientVo record);

    MessagePatientVo selectByPrimaryKey(Long patientMsgId);

    int updateByPrimaryKeySelective(MessagePatientVo record);

    int updateByPrimaryKey(MessagePatientVo record);
}