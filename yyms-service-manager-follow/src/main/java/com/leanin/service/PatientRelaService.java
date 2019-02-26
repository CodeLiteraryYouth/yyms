package com.leanin.service;


import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.vo.PatientRelaVo;

public interface PatientRelaService {

	/**
	 * 根据病人查询联系人列表
	 * @param patientId
	 * @return
	 */
	DataOutResponse findRelaListByPatientId(String patientId);
	
	
	/**
	 * 设为默认联系人
	 * @param patientRelaId
	 * @return
	 */
	DataOutResponse updateRelaState(Long patientRelaId, int status);

	
    /**
     * 增加默认联系人
     * @param record
     * @return
     */
	DataOutResponse addPatientRela(PatientRelaVo record);
    
}
