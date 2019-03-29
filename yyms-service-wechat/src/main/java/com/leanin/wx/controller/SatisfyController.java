package com.leanin.wx.controller;

import com.leanin.domain.response.DataOutResponse;
import com.leanin.wx.service.SatisfyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value="satisfy")
public class SatisfyController {

	@Autowired
	private SatisfyService satisfyService;


	@GetMapping("/findStyInfoByOpenId")
	public DataOutResponse findStyInfoByOpenId(@RequestParam String openId,@RequestParam Integer finishType){
		return satisfyService.findStyInfoByOpenId(openId,finishType);
	}
}
