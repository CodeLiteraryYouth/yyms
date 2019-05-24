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

	/**
	 * 查询科室  下拉框
	 * @param page
	 * @param pageSize
	 * @param wardName
	 * @return
	 */
	@GetMapping("findWardList")
	public DataOutResponse findWardList(@RequestParam int page, @RequestParam int pageSize, @RequestParam(required=false) String wardName) {
		return wardInfoService.findWardList(page,pageSize,wardName);
	}

	/**
	 * 科室管理
	 * @param page
	 * @param pageSize
	 * @param wardName
	 * @return
	 */
	@GetMapping("findList")
	public DataOutResponse findList(@RequestParam int page, @RequestParam int pageSize, @RequestParam(required=false) String wardName) {
		return wardInfoService.findlist(page,pageSize,wardName);
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
