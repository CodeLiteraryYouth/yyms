package com.leanin.oauth.controller;

import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.vo.PatientRelaVo;
import com.leanin.oauth.service.PatientRelaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("rela")
public class PatientRelaController {

	@Autowired
	private PatientRelaService patientRelaService;
	
	@GetMapping("findRelaListByPatientId")
	public DataOutResponse findRelaListByPatientId(@RequestParam String patientId) {
		return patientRelaService.findRelaListByPatientId(patientId);
	}
	
	@GetMapping("updateRelaState")
	public DataOutResponse updateRelaState(@RequestParam Long patientRelaId, @RequestParam int status) {
		return patientRelaService.updateRelaState(patientRelaId, status);
	}
	
	@PostMapping("addPatientRela")
	public DataOutResponse addPatientRela(@RequestBody PatientRelaVo patientRela) {
		return patientRelaService.addPatientRela(patientRela);
	}
}
