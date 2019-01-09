package com.leanin.controller;

import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.vo.RulesInfoVo;
import com.leanin.service.RulesInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("rules")
public class RulesInfoController {

	@Autowired
	private RulesInfoService rulesInfoService;
	
	@GetMapping("findCommonRules")
	public DataOutResponse findCommonRules(@RequestParam Integer rulesType, @RequestParam(required=false) String rulesName) {
		return rulesInfoService.findCommonRules(rulesType, rulesName);
	}
	
	@GetMapping("findRulesList")
	public DataOutResponse findRulesList(@RequestParam Integer page, @RequestParam Integer pageSize, @RequestParam(required=false) String rulesName,
                                         @RequestParam Integer type, @RequestParam(required=false) Long typeId) {
		return rulesInfoService.findRulesList(page, pageSize, rulesName, type, typeId);	
	}
	
	@GetMapping("findRulesByWardCode")
	public DataOutResponse findRulesByWardCode(@RequestParam Integer page, @RequestParam Integer pageSize, @RequestParam String wardCode,
                                               @RequestParam Integer rulesType, @RequestParam(required=false) String rulesName) {
		return rulesInfoService.findRulesByWardCode(page, pageSize, wardCode, rulesType, rulesName);
	}
	
	@GetMapping("updateRulesState")
	public DataOutResponse updateRulesState(@RequestParam Long rulesInfoId, @RequestParam Integer status) {
		return rulesInfoService.updateRulesState(rulesInfoId, status);
	}
	
	@PostMapping("addRulesInfo")
	public DataOutResponse addRulesInfo(@RequestBody RulesInfoVo record) {
		return rulesInfoService.addRulesInfo(record);
	}
	
	@PostMapping("updateRulesInfo")
	public DataOutResponse updateRulesInfo(@RequestBody RulesInfoVo record) {
		return rulesInfoService.updateRulesInfo(record);
	}
	
	@GetMapping("findRulesById")
	public DataOutResponse findRulesById(@RequestParam Long rulesInfoId) {
		return rulesInfoService.findRulesById(rulesInfoId);
	}
}
