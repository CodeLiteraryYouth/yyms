package com.leanin.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.leanin.dao.BaseMapper;
import com.leanin.domain.LeaninMedicalHistoryDao;

public interface LeaninMedicalHistoryDaoMapper extends BaseMapper<LeaninMedicalHistoryDao> {
	/**
	 * 根据patientId查出集合
	 * @param patientId
	 * @return
	 */
	List<LeaninMedicalHistoryDao> listByPatientId(@Param("patientId")String patientId);
}