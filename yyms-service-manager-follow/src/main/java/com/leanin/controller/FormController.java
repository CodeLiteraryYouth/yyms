package com.leanin.controller;

import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.vo.FormInfoVo;
import com.leanin.service.FormInfoService;
import com.leanin.utils.LyOauth2Util;
import com.leanin.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value="form")
public class FormController extends BaseController {

	@Autowired
	private FormInfoService formInfoService;
	
	@GetMapping("findCommonForm")
	public DataOutResponse findCommonForm(@RequestParam Integer page, @RequestParam Integer pageSize,
										  @RequestParam Integer formType, @RequestParam(required=false) String formName,
											Integer shareStatus) {
		return formInfoService.findCommonForm(page,pageSize,formType, formName,shareStatus);
	}
	
	@GetMapping("findFormList")
	public DataOutResponse findFormList(@RequestParam Integer page, @RequestParam Integer pageSize,
                                        @RequestParam(required=false) String formName, @RequestParam Integer formType,
										Integer shareStatus) {
		return formInfoService.findFormList(page, pageSize, formName, formType,shareStatus);
	}
	
	@GetMapping("updateFormStatus")
	public DataOutResponse updateFormStatus(@RequestParam String formNum, @RequestParam Integer formStatus) {
		return formInfoService.updateFormStatus(formNum, formStatus);
	}
	
	@PostMapping("addFormInfo")
	public DataOutResponse addFormInfo(@RequestBody FormInfoVo formInfo) {
		LyOauth2Util.UserJwt user = getUser(request);
		formInfo.setFormCreater(user.getId());
		return formInfoService.addFormInfo(formInfo);
	}
	
	@PostMapping("updateFormInfo")
	public DataOutResponse updateFormInfo(@RequestBody FormInfoVo formInfo) {
		return formInfoService.updateFormInfo(formInfo);
	}
	
	@GetMapping("findFormInfoById")
	public DataOutResponse findFormInfoById(@RequestParam String formNum) {
		return formInfoService.findFormInfoById(formNum);
	}
	
	@GetMapping("findFormListByTypeId")
	public DataOutResponse findFormListByTypeId(@RequestParam Integer page, @RequestParam Integer pageSize,
                                                @RequestParam Long formTypeId, @RequestParam(required=false) String formName) {
		return formInfoService.findFormListByTypeId(page, pageSize, formTypeId, formName);
	}
	
	@GetMapping("findFormListByWardCode")
	public DataOutResponse findFormListByWardCode(@RequestParam Integer page, @RequestParam Integer pageSize,
                                                  @RequestParam String wardCode, @RequestParam(required=false) String formName,
                                                  @RequestParam Integer formType) {
		return formInfoService.findFormListByWardCode(page, pageSize, wardCode, formName, formType);
	}

	@GetMapping("/findFormListByOpenid")
	public DataOutResponse findFormListByOpenid(@RequestParam String openid, @RequestParam Integer followStatus,
												@RequestParam Integer planType){
		return formInfoService.findFormListByOpenid(openid,followStatus,planType);
	}

	private LyOauth2Util.UserJwt getUser(HttpServletRequest httpServletRequest){
		LyOauth2Util lyOauth2Util = new LyOauth2Util();
		LyOauth2Util.UserJwt userJwt= lyOauth2Util.getUserJwtFromHeader(httpServletRequest);
		return userJwt;
	}
}
