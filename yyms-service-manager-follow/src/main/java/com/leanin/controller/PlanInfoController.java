package com.leanin.controller;

import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.vo.PlanInfoVo;
import com.leanin.service.PlanInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value="plan")
public class PlanInfoController {

	@Autowired
	private PlanInfoService planInfoService;
	
	
	@GetMapping("findPlanList")
	public DataOutResponse findPlanList(@RequestParam int page, @RequestParam int pageSize, @RequestParam(required=false) String planName, @RequestParam int planType) {
		return planInfoService.findPlanList(page, pageSize, planName,planType);
	}
	
	@GetMapping("updatePlanStatus")
	public DataOutResponse updatePlanStatus(@RequestParam String planNum, @RequestParam int status) {
		return planInfoService.updatePlanStatus(planNum, status);
	}
	
	@PostMapping("addPlanInfo")
	public DataOutResponse addPlanInfo(@RequestBody PlanInfoVo planInfo) {
		return planInfoService.addPlanInfo(planInfo);
	}
	
	@GetMapping("findPlanInfoById")
	public DataOutResponse findPlanInfoById(@RequestParam String planNum) {
		return planInfoService.findPlanInfoById(planNum);
	}
	
	@PostMapping("updatePlanInfo")
	public DataOutResponse updatePlanInfo(@RequestBody PlanInfoVo planInfo) {
		return planInfoService.updatePlanInfo(planInfo);
	}
}
