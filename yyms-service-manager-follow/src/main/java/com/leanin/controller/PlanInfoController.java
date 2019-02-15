package com.leanin.controller;

import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.response.ReturnFomart;
import com.leanin.domain.vo.PlanInfoVo;
import com.leanin.model.response.ResponseResult;
import com.leanin.service.PlanInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value="plan")
public class PlanInfoController {

	@Autowired
	private PlanInfoService planInfoService;


	
	@GetMapping("findPlanList")
	public DataOutResponse findPlanList(@RequestParam(required=false) int page, @RequestParam(required=false) int pageSize,
										@RequestParam(required=false) String planName, @RequestParam(required=false) int planType) {
		return planInfoService.findPlanList(page, pageSize, planName,planType);
	}
	
	@GetMapping("updatePlanStatus")
	public DataOutResponse updatePlanStatus(@RequestParam String planNum, @RequestParam int status) {
		return planInfoService.updatePlanStatus(planNum, status);
	}
	
	@PostMapping("addPlanInfo")
	public ResponseResult addPlanInfo(@RequestBody PlanInfoVo planInfo) {
		return planInfoService.addPlanInfo(planInfo);
	}


	
	@GetMapping("findPlanInfoById")
	public DataOutResponse findPlanInfoById(@RequestParam String planNum) {
		return planInfoService.findPlanInfoById(planNum);
	}
	
	@PostMapping("updatePlanInfo")
	public DataOutResponse updatePlanInfo(@RequestBody PlanInfoVo planInfo) {
		return planInfoService.updatePlanInfo(planInfo);
	}

	/**
	 * 根据计划名称查询计划
	 * @return
	 */
	@GetMapping("findPlanInfoByPlanName")
	public DataOutResponse findPlanInfoByPlanName(@RequestParam String planName,@RequestParam Integer currentPage,@RequestParam Integer pageSize){
		if (planName ==null || "".equals(planName)){
			return ReturnFomart.retParam(2010,"请输入查询条件后再进行查询");
		}
		return planInfoService.findPlanInfoByPlanName(planName,currentPage,pageSize);
	}


	/**
	 * 根据计划类型 查询计划信息
	 * @return
	 */
	@GetMapping("findPlanListByType")
	public DataOutResponse findPlanListByType(@RequestParam Integer planType){
		return planInfoService.findPlanListByType(planType);
	}


}
