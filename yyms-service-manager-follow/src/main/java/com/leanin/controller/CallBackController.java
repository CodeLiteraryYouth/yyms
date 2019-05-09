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


	/**
	 * 分页条件查询
	 * @param page  当前页
	 * @param pageSize	每页条数
	 * @param beginDate	开始时间
	 * @param endDate	结束时间
	 * @param dealStatus 处理状态（0：待处理 1：处理中 2：未处理）
	 * @param accuseWard 科室编码
	 * @param backNum 单号  模糊查询
	 * @param status  类型  （1：投诉 2：表扬）
	 * @return
	 */
//	@PreAuthorize("hasAnyAuthority('root','findConfig')")
	@GetMapping("findBackList")
	public DataOutResponse findBackList(@RequestParam int page, @RequestParam int pageSize, @RequestParam(required=false) String beginDate,
										@RequestParam(required=false) String endDate, @RequestParam(required=false) Integer dealStatus,
										@RequestParam(required=false) String accuseWard, @RequestParam(required=false) String backNum, @RequestParam int status) {
		return callBackService.findBackList(page, pageSize, beginDate, endDate, dealStatus, accuseWard, backNum,status);
	}

	/**
	 * 根据单号查询投诉表扬信息
	 * @param backNum  单号
	 * @param status 类型  （1：投诉 2：表扬）
	 * @return
	 */
//	@PreAuthorize("hasAnyAuthority('root','findConfig')")
	@GetMapping("findBackById")
	public DataOutResponse findBackById(@RequestParam String backNum,@RequestParam int status) {
		return callBackService.findBackById(backNum,status);
	}

	/**
	 * 添加投诉表扬信息
	 * @param callBack
	 * @return
	 */
//	@PreAuthorize("hasAnyAuthority('root','addConfig')")
	@PostMapping("addCallBack")
	public DataOutResponse addCallBack(@RequestBody CallBack callBack) {
//		LyOauth2Util.UserJwt user = getUser(request);
		return callBackService.addCallBack(callBack);
	}

	/**
	 *	修改投诉表扬处理信息
	 * @param callBackDeal
	 * @return
	 */
//	@PreAuthorize("hasAnyAuthority('root','addConfig')")
	@PostMapping("addCallBackDeal")
	public DataOutResponse addCallBackDeal(@RequestBody CallBackDeal callBackDeal) {
		return callBackService.updateCallBackDeal(callBackDeal);
	}

	/**
	 * 根据单号查询投诉表扬处理信息
	 * @param backNum
	 * @return
	 */
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
