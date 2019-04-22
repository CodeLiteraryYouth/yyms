package com.leanin.schdule.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PatientWxMapper {

    String findOpenIdByIdCard(@Param("idCard") String idCard);
}
