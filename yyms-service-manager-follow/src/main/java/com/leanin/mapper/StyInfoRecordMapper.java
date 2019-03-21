package com.leanin.mapper;

import com.leanin.domain.vo.SatisfyInfoVo;
import com.leanin.domain.vo.StyInfoRecordVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface StyInfoRecordMapper {

    void addRecord(@Param("styInfoRecordVo") StyInfoRecordVo styInfoRecordVo);
}
