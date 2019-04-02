package com.leanin.service;


import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.vo.FocusPatientVo;

import java.util.List;

public interface FocusPatientService {

	/**
	 * 查询关注患者信息列表
	 * @param patientName
	 * @return
	 */
	DataOutResponse findPatientList(String patientName,Long userId,Integer page,Integer pageSize);
	
	/**
	 * 修改病人的状态信息
	 * @param focusId
	 * @param status
	 * @return
	 */
	DataOutResponse updatePatientStatus(String[] focusId, Integer status);
	
	 /**
     * 保存关注患者的信息
     * @param record
     * @return
     */
	DataOutResponse insertFocusPatient(FocusPatientVo record);
    
    /**
     * 查询单独的病人信息
     * @param patientId
     * @return
     */
	DataOutResponse selectFocusPatientById(String patientId,Long userId);
}
