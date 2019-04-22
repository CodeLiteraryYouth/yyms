package com.leanin.controller;

import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.response.ReturnFomart;
import com.leanin.domain.vo.PlanInfoVo;
import com.leanin.model.response.ResponseResult;
import com.leanin.service.PlanInfoService;
import com.leanin.utils.LyOauth2Util;
import com.leanin.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@RestController
@RequestMapping(value="plan")
public class PlanInfoController extends BaseController {

	@Autowired
	private PlanInfoService planInfoService;


	@PreAuthorize("hasAnyAuthority('root','findFlwPlan')")
	@GetMapping("findPlanList")
	public DataOutResponse findPlanList(@RequestParam(required=false) int page, @RequestParam(required=false) int pageSize,
										@RequestParam(required=false) String planName, @RequestParam(required=true) int planType) {
		return planInfoService.findPlanList(page, pageSize, planName,planType);
	}

	//查询所有计划信息
	@PreAuthorize("hasAnyAuthority('root','findFlwPlan')")
	@GetMapping("findAllPlan")
	public DataOutResponse findAllPlan(@RequestParam Integer planType,@RequestParam Long userId){
		LyOauth2Util.UserJwt user = getUser(request);
		return planInfoService.findAllPlan(planType,user.getId());
	}

	@PreAuthorize("hasAnyAuthority('root','delFlwPlan')")
	@GetMapping("updatePlanStatus")
	public DataOutResponse updatePlanStatus(@RequestParam String planNum, @RequestParam int status) {
		return planInfoService.updatePlanStatus(planNum, status);
	}

//	@PreAuthorize("hasAnyAuthority('root','addFlwPlan')")
	@PostMapping("addPlanInfo")
	public ResponseResult addPlanInfo(@RequestBody PlanInfoVo planInfo) {
//		LyOauth2Util.UserJwt user = getUser(request);
		planInfo.setCreateDate(new Date());
//		planInfo.setPlanCreater(user.getId());
//		if (planInfo.getPlanType() == 2){//宣教计划负责人为自己
//			planInfo.setPlanDutyPer(user.getId());
//		}
		return planInfoService.addPlanInfo(planInfo);
	}


	@PreAuthorize("hasAnyAuthority('root','findFlwPlan')")
	@GetMapping("findPlanInfoById")
	public DataOutResponse findPlanInfoById(@RequestParam String planNum) {
		return planInfoService.findPlanInfoById(planNum);
	}

	@PreAuthorize("hasAnyAuthority('root','updateFlwPlan')")
	@PostMapping("updatePlanInfo")
	public DataOutResponse updatePlanInfo(@RequestBody PlanInfoVo planInfo) {
		return planInfoService.updatePlanInfo(planInfo);
	}

	/**
	 * 根据计划名称查询计划
	 * @return
	 */
	@PreAuthorize("hasAnyAuthority('root','findFlwPlan')")
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
	@PreAuthorize("hasAnyAuthority('root','findFlwPlan')")
	@GetMapping("findPlanListByType")
	public DataOutResponse findPlanListByType(@RequestParam Integer planType){
		return planInfoService.findPlanListByType(planType);
	}

	//根据病人科室查询计划
	@PreAuthorize("hasAnyAuthority('root','findFlwPlan')")
	@GetMapping("findByWard")
	public DataOutResponse findByWard(@RequestParam("patientWard") String patientWard){
		return planInfoService.findByWard(patientWard);
	}


	private LyOauth2Util.UserJwt getUser(HttpServletRequest httpServletRequest){
		LyOauth2Util lyOauth2Util = new LyOauth2Util();
		LyOauth2Util.UserJwt userJwt= lyOauth2Util.getUserJwtFromHeader(httpServletRequest);
		return userJwt;
	}


}
