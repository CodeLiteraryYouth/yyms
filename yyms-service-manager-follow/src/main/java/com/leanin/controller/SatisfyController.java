package com.leanin.controller;

import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.vo.SatisfyInfoVo;
import com.leanin.service.SatisfyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value="satisfy")
public class SatisfyController {

	@Autowired
	private SatisfyService satisfyService;
	
	
	@GetMapping("findSatisfyList")
	public DataOutResponse findSatisfyList(@RequestParam Integer page, @RequestParam Integer pageSize,
										   @RequestParam(required=false) Long typeId, @RequestParam(required=false) String satisfyName) {
		return satisfyService.findSatisfyList(page, pageSize, typeId, satisfyName);
	}
	
	@GetMapping("findSatisfyById")
	public DataOutResponse findSatisfyById(@RequestParam String satisfyNum) {
		return satisfyService.findSatisfyById(satisfyNum);
	}
	
	@GetMapping("logoutSatisfyInfo")
	public DataOutResponse logoutSatisfyInfo(@RequestParam String satisfyNum) {
		return satisfyService.logoutSatisfyInfo(satisfyNum);
	}
	
	@PostMapping("addSatisfyInfo")
	public DataOutResponse addSatisfyInfo(@RequestBody SatisfyInfoVo record) {
		return satisfyService.addSatisfyInfo(record);
	}

	
	@PostMapping("updateSatisfyInfo")
	public DataOutResponse updateSatisfyInfo(@RequestBody SatisfyInfoVo record) {
		return satisfyService.updateSatisfyInfo(record);
	}
}
