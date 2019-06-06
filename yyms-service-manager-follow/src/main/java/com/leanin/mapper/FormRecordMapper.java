package com.leanin.mapper;

import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.vo.FormRecordVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface FormRecordMapper {

    int addFormRecord(@Param("formRecordVo") FormRecordVo formRecordVo);

    FormRecordVo findFormRecordByPid(@Param("patientPlanId") Long patientPlanId);

    FormRecordVo findById(@Param("formRecordId") Long formRecordId);

    List<FormRecordVo> findByPlanNum(@Param("planNum") String planNum);
}
