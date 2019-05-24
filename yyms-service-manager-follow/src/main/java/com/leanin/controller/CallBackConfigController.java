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

import com.leanin.domain.CallBackConfig;
import com.leanin.service.CallBackConfigService;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@RestController
@RequestMapping("config")
public class CallBackConfigController extends BaseController {

	@Autowired
	private CallBackConfigService callBackConfigService;

	/**
	 *
	 * @param configType
	 * @return
	 */
//	@PreAuthorize("hasAnyAuthority('root','findConfig')")
	@GetMapping("findConfigListByType")
	public DataOutResponse findConfigListByType(Integer configType) {
		return callBackConfigService.findConfigListByType(configType);
	}

//	@PreAuthorize("hasAnyAuthority('root','findConfig')")
	@GetMapping("findConfigList")
	public DataOutResponse findConfigList(@RequestParam Integer page, @RequestParam Integer pageSize,Integer configType){
		return callBackConfigService.findConfigList(page,pageSize,configType);
	}


//	@PreAuthorize("hasAnyAuthority('root','findConfig')")
	@GetMapping("findConfigById")
	public DataOutResponse findConfigById(@RequestParam String configNum) {
		return callBackConfigService.findConfigById(configNum);
	}

//	@PreAuthorize("hasAnyAuthority('root','delConfig')")
	@GetMapping("updateConfigStatus")
	public DataOutResponse updateConfigStatus(@RequestParam String configNum,@RequestParam int status) {
		return callBackConfigService.updateConfigStatus(configNum, status);
	}

//	@PreAuthorize("hasAnyAuthority('root','addConfig')")
	@PostMapping("addConfig")
	public DataOutResponse addConfig(@RequestBody CallBackConfig record) {
		LyOauth2Util.UserJwt user = getUser(request);
		record.setConfigCreater(user.getId());//创建人id
		record.setConfigTime(new Date());//创建时间
		return callBackConfigService.addConfig(record);
	}

//	@PreAuthorize("hasAnyAuthority('root','updateConfig')")
	@PostMapping("updateConfig")
	public DataOutResponse updateConfig(@RequestBody CallBackConfig record) {
		return callBackConfigService.updateConfig(record);
	}

	@GetMapping("findDealNameByType")
	public DataOutResponse findDealNameByType(@RequestParam("type") Integer type,@RequestParam("status")Integer status){
		return callBackConfigService.findDealNameByType(type,status);
	}

	/**
	 * 批量删除投诉表扬设置
	 * @param configNums  投诉表扬设置
	 * @return
	 */
	@GetMapping("delByConfigNums")
	public DataOutResponse delByConfigNums(@RequestParam("configNums") String configNums){
		return callBackConfigService.delByConfigNums(configNums);
	}

	private LyOauth2Util.UserJwt getUser(HttpServletRequest httpServletRequest){
		LyOauth2Util lyOauth2Util = new LyOauth2Util();
		LyOauth2Util.UserJwt userJwt= lyOauth2Util.getUserJwtFromHeader(httpServletRequest);
		return userJwt;
	}

}
