package com.leanin.controller;

import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.vo.SatisfyPlanVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.leanin.service.SatisfyPlanService;

@RestController
@RequestMapping("satisfyPlan")
public class SatisfyPlanController {

	@Autowired
	private SatisfyPlanService satisfyPlanService;
	
	@GetMapping("findSatisfyPlanList")
	public DataOutResponse findSatisfyPlanList(@RequestParam int page, @RequestParam int pageSize, @RequestParam(required=false) String satisfyPlanName) {
		return satisfyPlanService.findSatisfyPlanList(page, pageSize, satisfyPlanName);
	}
	
	@GetMapping("updateSatisfyStatus")
	public DataOutResponse updateSatisfyStatus(@RequestParam String planSatisfyNum,@RequestParam int status) {
		return satisfyPlanService.updateSatisfyStatus(planSatisfyNum, status);
	}
	
	@PostMapping("addSatisfyPlan")
	public DataOutResponse addSatisfyPlan(@RequestBody SatisfyPlanVo satisfyPlan) {
		return satisfyPlanService.addSatisfyPlan(satisfyPlan);
	}
	
	@GetMapping("findSatisfyPlanById")
	public DataOutResponse findSatisfyPlanById(@RequestParam String planSatisfyNum) {
		return satisfyPlanService.findSatisfyPlanById(planSatisfyNum);
	}
	
	@PostMapping("updateSatisfyPlan")
	public DataOutResponse updateSatisfyPlan(@RequestBody SatisfyPlanVo satisfyPlan) {
		return satisfyPlanService.updateSatisfyPlan(satisfyPlan);
	}
}
