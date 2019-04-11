package com.leanin.mapper;

import com.leanin.domain.vo.OnlineEduVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OnlineEduMapper {

    List<OnlineEduVo> findListByParam(@Param("dept") String dept,@Param("formStatus") Integer formStatus,@Param("sendStatus") Integer sendStatus,@Param("patientName") String patientName,@Param("sendName") String sendName,@Param("patientId") String patientId);

}
