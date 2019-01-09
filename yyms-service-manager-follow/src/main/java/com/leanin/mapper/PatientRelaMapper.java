package com.leanin.mapper;

import com.leanin.domain.vo.PatientRelaVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PatientRelaMapper {
	
	/**
	 * 根据病人查询联系人列表
	 * @param patientId
	 * @return
	 */
	List<PatientRelaVo> findRelaListByPatientId(String patientId);
	/**
	 * 设为默认联系人
	 * @param patientRelaId
	 * @return
	 */
    int updateRelaState(Long patientRelaId, @Param("status") int status);

    /**
     * 增加默认联系人
     * @param record
     * @return
     */
    int addPatientRela(PatientRelaVo record);
    
    /**
     * 查询默认联系人
     * @param patientRelaName
     * @return
     */
	PatientRelaVo findPatientRelaByName(String patientRelaName);
}