package com.leanin.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.leanin.dao.BaseMapper;
import com.leanin.domain.LeaninPatientRelaDao;

public interface LeaninPatientRelaDaoMapper extends BaseMapper<LeaninPatientRelaDao> {
	/**
	 * 查询
	 * @param patientId
	 * @return
	 */
	List<LeaninPatientRelaDao> listPage(@Param("patientId")String patientId);
}