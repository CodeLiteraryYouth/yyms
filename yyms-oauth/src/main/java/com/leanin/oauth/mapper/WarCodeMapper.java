package com.leanin.oauth.mapper;

import com.leanin.domain.vo.WardInfoVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface WarCodeMapper {

    List<WardInfoVo> findByUserId(@Param("userId") Long userId);
}
