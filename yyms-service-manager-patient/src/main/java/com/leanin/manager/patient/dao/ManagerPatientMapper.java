package com.leanin.manager.patient.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface ManagerPatientMapper {
    /**
     * 按照条件查询患者信息
     * @param paramMap
     */
    public List<Map> findListByParam(@Param("paramMap") Map paramMap);

    /**
     * 按照条件查询就诊患者信息
     * @param paramMap
     * @return
     */
    List<Map> findOutHosPatientByParam(@Param("paramMap") Map paramMap);
}
