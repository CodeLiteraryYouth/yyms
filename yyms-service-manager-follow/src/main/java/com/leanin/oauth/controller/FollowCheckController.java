package com.leanin.oauth.controller;

import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.vo.FollowCheckVo;
import com.leanin.oauth.service.FollowCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value="check")
public class FollowCheckController {

	@Autowired
	private FollowCheckService followCheckService;
	
	@GetMapping("findCheckList")
	public DataOutResponse findCheckList(@RequestParam int page, @RequestParam int pageSize, @RequestParam(required=false) String checkName) {
		return followCheckService.findCheckList(page, pageSize, checkName);
	}
	
	@GetMapping("updateCheckStatus")
	public DataOutResponse updateCheckStatus(@RequestParam String checkNum, @RequestParam int status) {
		return followCheckService.updateCheckStatus(checkNum, status);
	}
	
	@PostMapping("addCheckInfo")
	public DataOutResponse addCheckInfo(@RequestBody FollowCheckVo followCheck) {
		return followCheckService.addCheckInfo(followCheck);
	}
	
	@GetMapping("findCheckById")
	public DataOutResponse findCheckById(@RequestParam String checkNum) {
		return followCheckService.findCheckById(checkNum);
	}
	
	@PostMapping("updateCheckInfo")
	public DataOutResponse updateCheckInfo(@RequestBody FollowCheckVo followCheck) {
		return followCheckService.updateCheckInfo(followCheck);
	}
}
