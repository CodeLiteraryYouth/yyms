package com.leanin.mapper;

import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.vo.SatisfyInfoVo;
import com.leanin.domain.vo.StyInfoRecordVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface StyInfoRecordMapper {

    void addRecord(@Param("styInfoRecordVo") StyInfoRecordVo styInfoRecordVo);

    StyInfoRecordVo findStyInfoRecord(@Param("planPatientId") Long planPatientId);

    StyInfoRecordVo findById(@Param("satisfyRecordId") String satisfyRecordId);

    List<StyInfoRecordVo> findByPlanNumAndCount(@Param("planNum") String planNum);

    List<StyInfoRecordVo> findByIdCard(@Param("idCard") String idCard);
}
