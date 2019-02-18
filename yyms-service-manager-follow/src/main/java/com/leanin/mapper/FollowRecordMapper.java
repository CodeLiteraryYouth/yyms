package com.leanin.mapper;

import com.leanin.domain.vo.PlanPatientRecordVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

//随访记录表
@Mapper
public interface FollowRecordMapper {

    //添加随访记录
    void addFollowRecord(@Param("patientRecordVo")PlanPatientRecordVo patientRecordVo);

}
