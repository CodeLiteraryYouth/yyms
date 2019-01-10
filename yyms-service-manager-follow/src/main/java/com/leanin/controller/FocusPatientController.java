package com.leanin.controller;


import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.vo.FocusPatientVo;
import com.leanin.service.FocusPatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 关注病人Controller
 * @author Administrator
 *
 */
@RestController
@RequestMapping(value="focus")
public class FocusPatientController {

	@Autowired
	private FocusPatientService focusPatientService;
	
	@GetMapping("findPatientList")
	public DataOutResponse findPatientList(@RequestParam(required=false) String patientName) {
		return focusPatientService.findPatientList(patientName);
	}
	
	@PostMapping("addFocusPatient")
	public DataOutResponse addFocusPatient(@RequestBody FocusPatientVo focusPatient) {
		return focusPatientService.insertFocusPatient(focusPatient);
	}
	
	@GetMapping("updatePatientStatus")
	public DataOutResponse updatePatientStatus(@RequestParam Long focusId, @RequestParam Integer status) {
		return focusPatientService.updatePatientStatus(focusId, status);
	}
	
	@GetMapping("selectFocusPatientById")
	public DataOutResponse selectFocusPatientById(@RequestParam String patientId) {
		return focusPatientService.selectFocusPatientById(patientId);
	}
}
