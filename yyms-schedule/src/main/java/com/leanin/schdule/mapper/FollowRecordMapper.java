package com.leanin.schdule.mapper;

import com.leanin.domain.vo.PlanPatientRecordVo;
import com.leanin.domain.vo.PlanPatientVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

//随访记录表
@Mapper
public interface FollowRecordMapper {

    //添加随访记录
    void addFollowRecord(PlanPatientVo planPatientVo);

}
