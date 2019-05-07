package com.leanin.controller;

import com.leanin.domain.response.DataOutResponse;
import com.leanin.utils.LyOauth2Util;
import com.leanin.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.leanin.domain.CallBack;
import com.leanin.domain.CallBackDeal;
import com.leanin.service.CallBackService;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("callBack")
public class CallBackController extends BaseController {

	@Autowired
	private CallBackService callBackService;


//	@PreAuthorize("hasAnyAuthority('root','findConfig')")
	@GetMapping("findBackList")
	public DataOutResponse findBackList(@RequestParam int page, @RequestParam int pageSize, @RequestParam(required=false) String beginDate,
										@RequestParam(required=false) String endDate, @RequestParam(required=false) Integer dealStatus,
										@RequestParam(required=false) String accuseWard, @RequestParam(required=false) String backNum, @RequestParam int status) {
		return callBackService.findBackList(page, pageSize, beginDate, endDate, dealStatus, accuseWard, backNum,status);
	}

//	@PreAuthorize("hasAnyAuthority('root','findConfig')")
	@GetMapping("findBackById")
	public DataOutResponse findBackById(@RequestParam String backNum,@RequestParam int status) {
		return callBackService.findBackById(backNum,status);
	}

//	@PreAuthorize("hasAnyAuthority('root','addConfig')")
	@PostMapping("addCallBack")
	public DataOutResponse addCallBack(@RequestBody CallBack callBack) {
//		LyOauth2Util.UserJwt user = getUser(request);
		return callBackService.addCallBack(callBack);
	}

//	@PreAuthorize("hasAnyAuthority('root','addConfig')")
	@PostMapping("addCallBackDeal")
	public DataOutResponse addCallBackDeal(@RequestBody CallBackDeal callBackDeal) {
		return callBackService.updateCallBackDeal(callBackDeal);
	}

//	@PreAuthorize("hasAnyAuthority('root','findConfig')")
	@GetMapping("findBackDealByBackNum")
	public DataOutResponse findBackDealByBackNum(@RequestParam String backNum){
		return callBackService.findBackDealByBackNum(backNum);
	}

//	private LyOauth2Util.UserJwt getUser(HttpServletRequest httpServletRequest){
//		LyOauth2Util lyOauth2Util = new LyOauth2Util();
//		LyOauth2Util.UserJwt userJwt= lyOauth2Util.getUserJwtFromHeader(httpServletRequest);
//		return userJwt;
//	}
}
