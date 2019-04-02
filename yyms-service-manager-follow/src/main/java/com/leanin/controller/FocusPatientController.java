package com.leanin.controller;


import com.alibaba.fastjson.JSON;
import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.vo.FocusPatientVo;
import com.leanin.service.FocusPatientService;
import com.leanin.utils.LyOauth2Util;
import com.leanin.utils.UUIDUtils;
import com.leanin.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 关注病人Controller
 * @author Administrator
 *
 */
@RestController
@RequestMapping(value="focus")
public class FocusPatientController extends BaseController {

	@Autowired
	private FocusPatientService focusPatientService;
	
	@GetMapping("findPatientList")
	public DataOutResponse findPatientList(@RequestParam(required=false) String patientName,Integer page,Integer pageSize) {
		LyOauth2Util.UserJwt user = getUser(request);
		return focusPatientService.findPatientList(patientName,user.getId(),page,pageSize);
	}
	
	@PostMapping("addFocusPatient")
	public DataOutResponse addFocusPatient(@RequestBody FocusPatientVo focusPatient) {
		LyOauth2Util.UserJwt user = getUser(request);
		focusPatient.setUserId(user.getId());
		return focusPatientService.insertFocusPatient(focusPatient);
	}
	
	@GetMapping("updatePatientStatus")
	public DataOutResponse updatePatientStatus(@RequestParam String focusId, @RequestParam Integer status) {
        String[] longs = focusId.split(",");
		return focusPatientService.updatePatientStatus(longs, status);
	}
	
	@GetMapping("selectFocusPatientById")
	public DataOutResponse selectFocusPatientById(@RequestParam String patientId) {
		LyOauth2Util.UserJwt user = getUser(request);
		return focusPatientService.selectFocusPatientById(patientId,user.getId());
	}

	private LyOauth2Util.UserJwt getUser(HttpServletRequest httpServletRequest){
		LyOauth2Util lyOauth2Util = new LyOauth2Util();
		LyOauth2Util.UserJwt userJwt= lyOauth2Util.getUserJwtFromHeader(httpServletRequest);
		return userJwt;
	}

}
