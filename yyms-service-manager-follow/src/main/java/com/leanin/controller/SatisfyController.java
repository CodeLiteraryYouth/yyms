package com.leanin.controller;

import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.vo.SatisfyInfoVo;
import com.leanin.service.SatisfyService;
import com.leanin.utils.LyOauth2Util;
import com.leanin.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@RestController
@RequestMapping(value="satisfy")
public class SatisfyController extends BaseController {

	@Autowired
	private SatisfyService satisfyService;

//	@PreAuthorize("hasAnyAuthority('root','findSatisfy')")
	@GetMapping("findSatisfyList")
	public DataOutResponse findSatisfyList(@RequestParam Integer page, @RequestParam Integer pageSize,
										   @RequestParam(required=false) Long typeId, @RequestParam(required=false) String satisfyName,
											Integer shareStatus) {
		return satisfyService.findSatisfyList(page, pageSize, typeId, satisfyName,shareStatus);
	}

//	@PreAuthorize("hasAnyAuthority('root','findSatisfy')")
	@GetMapping("findSatisfyById")
	public DataOutResponse findSatisfyById(@RequestParam String satisfyNum) {
		return satisfyService.findSatisfyById(satisfyNum);
	}

//	@PreAuthorize("hasAnyAuthority('root','delSatisfy')")
	@GetMapping("logoutSatisfyInfo")
	public DataOutResponse logoutSatisfyInfo(@RequestParam String satisfyNum) {
		return satisfyService.logoutSatisfyInfo(satisfyNum);
	}

//	@PreAuthorize("hasAnyAuthority('root','addSatisfy')")
	@PostMapping("addSatisfyInfo")
	public DataOutResponse addSatisfyInfo(@RequestBody SatisfyInfoVo record) {
//		LyOauth2Util.UserJwt user = getUser(request);
//		record.setCreater(user.getId());
		return satisfyService.addSatisfyInfo(record);
	}

//	@PreAuthorize("hasAnyAuthority('root','updateSatisfy')")
	@PostMapping("updateSatisfyInfo")
	public DataOutResponse updateSatisfyInfo(@RequestBody SatisfyInfoVo record) {
		return satisfyService.updateSatisfyInfo(record);
	}


	@GetMapping("/findStyInfoByOpenId")
	public DataOutResponse findStyInfoByOpenId(@RequestParam String openId,@RequestParam Integer finishType,Integer formStatus){
		return satisfyService.findStyInfoByOpenId(openId,finishType,formStatus);
	}

	/**
	 * 共享满意度表单
	 * @param satisfyNum 满意度单号
	 * @param status	1不共享  2 共享
	 * @return
	 */
	@GetMapping("shareSatisfy")
	public DataOutResponse shareSatisfy(@RequestParam("satisfyNum") String satisfyNum,@RequestParam("status")Integer status){
		return satisfyService.shareSatisfy(satisfyNum,status);
	}

	private LyOauth2Util.UserJwt getUser(HttpServletRequest httpServletRequest){
		LyOauth2Util lyOauth2Util = new LyOauth2Util();
		LyOauth2Util.UserJwt userJwt= lyOauth2Util.getUserJwtFromHeader(httpServletRequest);
		return userJwt;
	}
}
