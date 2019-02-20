package com.leanin.mapper;

import com.leanin.domain.vo.PlanPatientRecordVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

//随访记录表
@Mapper
public interface FollowRecordMapper {

    //添加随访记录
    void addFollowRecord(@Param("patientRecordVo")PlanPatientRecordVo patientRecordVo);

    //条件查询
    List<PlanPatientRecordVo> findPlanPatientList(@Param("planNum") String planNum,@Param("planPatsStatus") Integer planPatsStatus,@Param("patientName") String patientName);

    //修改患者状态
    void updatePatientStatusById(@Param("patientPlanId") Long patientPlanId);
}
