package com.leanin.wx.mapper;

import com.leanin.domain.vo.FormRecordVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface FormRecordMapper {

    void addFormRecord(@Param("formRecordVo") FormRecordVo formRecordVo);

    FormRecordVo findFormRecordByPid(@Param("patientPlanId") Long patientPlanId);
}
