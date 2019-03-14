package com.leanin.mapper;


import com.leanin.domain.vo.MessagePatientVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MessagePatientMapper {
    int deleteByPrimaryKey(Long patientMsgId);

    int insert(MessagePatientVo record);

    int insertSelective(MessagePatientVo record);

    MessagePatientVo selectByPrimaryKey(Long patientMsgId);

    int updateByPrimaryKeySelective(MessagePatientVo record);

    int updateByPrimaryKey(MessagePatientVo record);

    List<MessagePatientVo> findList(@Param("patientName") String patientName,@Param("sendType") Integer sendType);

    MessagePatientVo findById(@Param("patientMsgId") Long patientMsgId);

    List<MessagePatientVo> bindPatient(@Param("idCard") String idCard,@Param("patientName") String patientName,@Param("phoneNum") String phoneNum);
}