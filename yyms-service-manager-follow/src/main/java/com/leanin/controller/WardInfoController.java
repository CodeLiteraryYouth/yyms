package com.leanin.controller;

import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.vo.WardInfoVo;
import com.leanin.service.WardInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("wardInfo")
public class WardInfoController {

	@Autowired
	private WardInfoService wardInfoService;
	
	@GetMapping("findWardList")
	public DataOutResponse findWardList(@RequestParam int page, @RequestParam int pageSize, @RequestParam(required=false) String wardName) {
		return wardInfoService.findWardList(page,pageSize,wardName);
	}
	
	@GetMapping("deleteById")
	public DataOutResponse deleteById(@RequestParam Long wardId, @RequestParam int status) {
		return wardInfoService.deleteById(wardId,status);
	}
	
	@PostMapping("addWardInfo")
	public DataOutResponse addWardInfo(@RequestBody WardInfoVo wardInfo) {
		return wardInfoService.insertWardInfo(wardInfo);
	}
	
	@GetMapping("selectById")
	public DataOutResponse selectById(@RequestParam Long wardId) {
		return wardInfoService.selectById(wardId);
	}
	
	@PostMapping("updateWardInfo")
	public DataOutResponse updateWardInfo(@RequestBody WardInfoVo wardInfo) {
		return wardInfoService.updateWardInfo(wardInfo);
	}
}
