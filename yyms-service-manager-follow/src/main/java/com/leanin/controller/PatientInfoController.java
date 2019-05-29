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


	/**
	 * 患者档案查询分页条件查询
	 * @param page 当前页
	 * @param pageSize 每页展示条数
	 * @param hosNo	住院号 门诊号  体检号
	 * @param idCard 身份证号
	 * @param patientSource	患者来源  1 住院 2 门诊 4 体检 5 建档
	 * @return
	 */
	@GetMapping("/findByParam")
	public DataOutResponse findByParam(@RequestParam("patientSource") Integer patientSource,Integer page,Integer pageSize,String hosNo,String idCard,String patientName){
		return patientInfoService.findByParam(page,pageSize,hosNo,idCard,patientSource,patientName);
	}

	/**
	 * 查询档案  根据患者id 查看档案信息
	 * @param id
	 * @return
	 */
	@GetMapping("/findById")
	public DataOutResponse findById(@RequestParam("id") Long id){
		return patientInfoService.findById(id);
	}

	/**
	 * 修改患者档案
	 * @param patientInfoVo
	 * @return
	 */
	@PutMapping("/updatePatienInfo")
	public DataOutResponse updatePatienInfo(@RequestBody PatientInfoVo patientInfoVo){
		return patientInfoService.updatePatienInfo(patientInfoVo);
	}
}
