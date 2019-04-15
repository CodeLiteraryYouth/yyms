package com.leanin.controller;

import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.vo.RulesInfoVo;
import com.leanin.service.RulesInfoService;
import com.leanin.utils.LyOauth2Util;
import com.leanin.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@RestController
@RequestMapping("rules")
public class RulesInfoController extends BaseController {

	@Autowired
	private RulesInfoService rulesInfoService;

	@PreAuthorize("hasAnyAuthority('root','findRules')")
	@GetMapping("findCommonRules")
	public DataOutResponse findCommonRules(@RequestParam Integer rulesType, @RequestParam(required=false) String rulesName) {
		LyOauth2Util.UserJwt user = getUser(request);
		return rulesInfoService.findCommonRules(rulesType, rulesName);
	}

	@PreAuthorize("hasAnyAuthority('root','findRules')")
	@GetMapping("findRulesList")
	public DataOutResponse findRulesList(@RequestParam Integer page, @RequestParam Integer pageSize, @RequestParam(required=false) String rulesName,
                                         @RequestParam Integer type, @RequestParam(required=false) Long typeId) {
		return rulesInfoService.findRulesList(page, pageSize, rulesName, type, typeId);	
	}

	@PreAuthorize("hasAnyAuthority('root','findRules')")
	@GetMapping("findRulesByWardCode")
	public DataOutResponse findRulesByWardCode(@RequestParam Integer page, @RequestParam Integer pageSize, @RequestParam String wardCode,
                                               @RequestParam Integer rulesType, @RequestParam(required=false) String rulesName) {
		return rulesInfoService.findRulesByWardCode(page, pageSize, wardCode, rulesType, rulesName);
	}

	@PreAuthorize("hasAnyAuthority('root','delRules')")
	@GetMapping("updateRulesState")
	public DataOutResponse updateRulesState(@RequestParam Long rulesInfoId, @RequestParam Integer status) {
		return rulesInfoService.updateRulesState(rulesInfoId, status);
	}

	@PreAuthorize("hasAnyAuthority('root','addRules')")
	@PostMapping("addRulesInfo")
	public DataOutResponse addRulesInfo(@RequestBody RulesInfoVo record) {
		LyOauth2Util.UserJwt user = getUser(request);
		record.setRulesCreater(user.getId());
		record.setRulesCreateTime(new Date());
		return rulesInfoService.addRulesInfo(record);
	}

	@PreAuthorize("hasAnyAuthority('root','updateRules')")
	@PostMapping("updateRulesInfo")
	public DataOutResponse updateRulesInfo(@RequestBody RulesInfoVo record) {
		return rulesInfoService.updateRulesInfo(record);
	}

	@PreAuthorize("hasAnyAuthority('root','findRules')")
	@GetMapping("findRulesById")
	public DataOutResponse findRulesById(@RequestParam Long rulesInfoId) {
		return rulesInfoService.findRulesById(rulesInfoId);
	}

	private LyOauth2Util.UserJwt getUser(HttpServletRequest httpServletRequest){
		LyOauth2Util lyOauth2Util = new LyOauth2Util();
		LyOauth2Util.UserJwt userJwt= lyOauth2Util.getUserJwtFromHeader(httpServletRequest);
		return userJwt;
	}
}
