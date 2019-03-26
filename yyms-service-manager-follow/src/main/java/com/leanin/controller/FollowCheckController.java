package com.leanin.controller;

import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.vo.FollowCheckVo;
import com.leanin.service.FollowCheckService;
import com.leanin.utils.LyOauth2Util;
import com.leanin.utils.UUIDUtils;
import com.leanin.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@RestController
@RequestMapping(value="check")
public class FollowCheckController extends BaseController {

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
		String uuid = UUIDUtils.getUUID();
		followCheck.setCheckNum(uuid);//抽查计划号
		LyOauth2Util.UserJwt user = getUser(request);
		followCheck.setCreater(user.getId());//创建人id
		followCheck.setCreateDate(new Date());//创建日期
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

	private LyOauth2Util.UserJwt getUser(HttpServletRequest httpServletRequest){
		LyOauth2Util lyOauth2Util = new LyOauth2Util();
		LyOauth2Util.UserJwt userJwt= lyOauth2Util.getUserJwtFromHeader(httpServletRequest);
		return userJwt;
	}
}
