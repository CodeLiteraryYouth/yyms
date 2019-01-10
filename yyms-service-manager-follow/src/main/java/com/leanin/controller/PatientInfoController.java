package com.leanin.controller;

import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.vo.PatientInfoVo;
import com.leanin.service.PatientInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 
 * @author Administrator
 *
 */
@RestController
@RequestMapping("patient")
public class PatientInfoController {

	@Autowired
	private PatientInfoService patientInfoService;
	
	@PostMapping("addPatient")
	public DataOutResponse addPatient(@RequestBody PatientInfoVo patient) {
		return patientInfoService.addPatientInfo(patient);
	}
	
	@PostMapping("updatePatient")
	public DataOutResponse updatePatient(@RequestBody PatientInfoVo patient) {
		return patientInfoService.updatePatientInfo(patient);
	}
	
	@GetMapping("findPatientList")
	public DataOutResponse findPatientList(@RequestParam(required=false) String patientName, @RequestParam(required=false) String patientId,
                                           @RequestParam(required=false) String beginDate, @RequestParam(required=false) String endDate) {
		return patientInfoService.findPatientInfoList(patientName, patientId, beginDate, endDate);
	}
	
	@GetMapping("findPatientById")
	public DataOutResponse findPatientById(@RequestParam String patientId) {
		return patientInfoService.findPatientById(patientId);
	}
}
