package com.leanin.mapper;

import com.leanin.domain.dao.PatientWxDao;
import com.leanin.domain.request.BindPat;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PatientWxMapper {

    void addPatientWx(@Param("bindPat") BindPat bindPat);

    PatientWxDao findByIdCard(@Param("idCard") String idCard);
}
