package com.leanin.service;


import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.vo.PatientInfoVo;

public interface PatientInfoService {

	/**
	 * 查询病人建档列表信息
	 * @param patientName
	 * @param patientId
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	DataOutResponse findPatientInfoList(String patientName, String patientId, String beginDate, String endDate);
	/**
	 * 新增建党病人信息
	 * @param patientInfo
	 * @return
	 */
	DataOutResponse addPatientInfo(PatientInfoVo patientInfo);
	
	/**
	 * 修改建档病人信息
	 * @param patientInfo
	 * @return
	 */
	DataOutResponse updatePatientInfo(PatientInfoVo patientInfo);
	
	/**
	 * 根绝ID查询病人信息
	 * @param patientId
	 * @return
	 */
	DataOutResponse findPatientById(String patientId);

	DataOutResponse findByParam(Integer page, Integer pageSize, String hosNo, String idCard, Integer patientSource,String patientName);

	DataOutResponse findById(Long id);

	DataOutResponse updatePatienInfo(PatientInfoVo patientInfoVo);

    DataOutResponse findByIdAndSource(String patientId, Integer patientSource);
}
