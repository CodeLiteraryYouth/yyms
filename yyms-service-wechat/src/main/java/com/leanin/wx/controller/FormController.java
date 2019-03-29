package com.leanin.wx.controller;

import com.leanin.domain.response.DataOutResponse;
import com.leanin.wx.service.FormInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value="form")
public class FormController {

	@Autowired
	private FormInfoService formInfoService;

	@GetMapping("/findFormListByOpenid")
	public DataOutResponse findFormListByOpenid(@RequestParam String openid, @RequestParam Integer followStatus,
												@RequestParam Integer planType){
		return formInfoService.findFormListByOpenid(openid,followStatus,planType);
	}

}
