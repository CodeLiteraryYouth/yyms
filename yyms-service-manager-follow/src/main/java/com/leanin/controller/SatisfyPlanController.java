package com.leanin.controller;

import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.vo.SatisfyPlanVo;
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
import com.leanin.service.SatisfyPlanService;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 *
 * @author Administrator
 */
@RestController
@RequestMapping("satisfyPlan")
public class SatisfyPlanController extends BaseController {

	@Autowired
	private SatisfyPlanService satisfyPlanService;

//	@PreAuthorize("hasAnyAuthority('root','findStyPlan')")
	@GetMapping("findSatisfyPlanList")
	public DataOutResponse findSatisfyPlanList(@RequestParam int page, @RequestParam int pageSize, @RequestParam(required=false) String satisfyPlanName) {
		LyOauth2Util.UserJwt user = getUser(request);
		return satisfyPlanService.findSatisfyPlanList(page, pageSize, satisfyPlanName,user.getId());
	}

	/**
	 * 修改满意度计划状态
	 * @param planSatisfyNum
	 * @param status
	 * @return
	 */
//	@PreAuthorize("hasAnyAuthority('root','delStyPlan')")
	@GetMapping("updateSatisfyStatus")
	public DataOutResponse updateSatisfyStatus(@RequestParam String planSatisfyNum,@RequestParam int status) {
		return satisfyPlanService.updateSatisfyStatus(planSatisfyNum, status);
	}

//	@PreAuthorize("hasAnyAuthority('root','addStyPlan')")
	@PostMapping("addSatisfyPlan")
	public DataOutResponse addSatisfyPlan(@RequestBody SatisfyPlanVo satisfyPlan) {
		LyOauth2Util.UserJwt user = getUser(request);
		satisfyPlan.setCreater(user.getId());
		satisfyPlan.setCreateDate(new Date());
		return satisfyPlanService.addSatisfyPlan(satisfyPlan);
	}

//	@PreAuthorize("hasAnyAuthority('root','findStyPlan')")
	@GetMapping("findSatisfyPlanById")
	public DataOutResponse findSatisfyPlanById(@RequestParam String planSatisfyNum) {
		return satisfyPlanService.findSatisfyPlanById(planSatisfyNum);
	}

//	@PreAuthorize("hasAnyAuthority('root','updateStyPlan')")
	@PostMapping("updateSatisfyPlan")
	public DataOutResponse updateSatisfyPlan(@RequestBody SatisfyPlanVo satisfyPlan) {
		return satisfyPlanService.updateSatisfyPlan(satisfyPlan);
	}

	//查询所有满意度计划
//	@PreAuthorize("hasAnyAuthority('root','findStyPlan')")
	@GetMapping("/findAll")
	public DataOutResponse findAll(){
		LyOauth2Util.UserJwt user = getUser(request);
		return satisfyPlanService.findAll(user.getId());
	}

//	@PreAuthorize("hasAnyAuthority('root','findStyPlan')")
	@GetMapping("findByWard")
	public DataOutResponse findByWard(@RequestParam String patientWard){
		return satisfyPlanService.findByWard(patientWard);
	}

	/**
	 * 条件查询满意度计划信息
	 * @param planName 计划名称
	 * @param deptId 科室id
	 * @param userId 负责人id
	 * @param startDate 开始时间 yyyy-MM-dd HH:mm:ss
	 * @param endDate 结束时间 yyyy-MM-dd HH:mm:ss
	 * @param page 当前页 默认第一页
	 * @param pageSize 每页展示条数
	 * @return
	 */
	@GetMapping("/findSatisfyPlanByParam")
	public DataOutResponse findSatisfyPlanByParam(String planName,String deptId,Long userId,String startDate,String endDate,Integer page,Integer pageSize){
		return satisfyPlanService.findSatisfyPlanByParam(planName,deptId,userId,startDate,endDate,page,pageSize);
	}


	private LyOauth2Util.UserJwt getUser(HttpServletRequest httpServletRequest){
		LyOauth2Util lyOauth2Util = new LyOauth2Util();
		LyOauth2Util.UserJwt userJwt= lyOauth2Util.getUserJwtFromHeader(httpServletRequest);
		return userJwt;
	}
}
