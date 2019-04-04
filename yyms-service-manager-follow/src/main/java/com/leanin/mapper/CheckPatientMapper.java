package com.leanin.mapper;


import com.leanin.domain.vo.CheckPatientVo;
import com.leanin.domain.vo.PlanPatientVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CheckPatientMapper {
    void add(PlanPatientVo planPatientVo);

    List<CheckPatientVo> findList(@Param("patientName") String patientName,@Param("finishStatus") Integer finishStatus);

    Integer findCount(@Param("finishStatus") Integer finishStatus,@Param("checkNum") String checkNum);

    Integer findUnfinishCount(@Param("checkNum") String checkNum);

    CheckPatientVo findById(@Param("checkPatientId") Long checkPatientId);

    void updateById(@Param("id") long id);
}