package com.leanin.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.leanin.domain.LeaninPatientRelaDao;
import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.response.ReturnFomart;
import com.leanin.service.LeaninPatientRelaService;
import com.leanin.web.BaseController;

/**
 * 电话信息表
 * @author zliu
 *
 */
@RestController
@RequestMapping("leaninPatienRela")
public class LeaninPatientRelaController extends BaseController{
	@Autowired
	private LeaninPatientRelaService leaninPatientRelaService;
	
	/**
	 * 查询
	 * @param request
	 * @param patientId
	 * @return
	 */
	@RequestMapping("/listPage")
	public DataOutResponse listPage(HttpServletRequest request
			,@RequestParam("patientId")String patientId){
		try{
			return leaninPatientRelaService.listPage(patientId);
		}catch(Exception e){
			 return ReturnFomart.retParam(404, e.getMessage());	
		}
	}
	/**
	 * 新增
	 * @param request
	 * @param leaninPatientRelaDao
	 * @return
	 */
	@RequestMapping("/add")
	public DataOutResponse add(HttpServletRequest request,
			@ModelAttribute LeaninPatientRelaDao leaninPatientRelaDao){
		
		try{
			return leaninPatientRelaService.add(leaninPatientRelaDao);
		}catch(Exception e){
			 return ReturnFomart.retParam(404, e.getMessage());	
		}
	}
	/**
	 * 查看详情
	 * @param id
	 * @return
	 */
	@RequestMapping("/findById")
	public DataOutResponse findById(@RequestParam("id")Long id){
		try{
			return leaninPatientRelaService.findById(id);
		}catch(Exception e){
			 return ReturnFomart.retParam(404, e.getMessage());	
		}
	}
	/**
	 * 修改
	 * @param request
	 * @param leaninPatientRelaDao
	 * @return
	 */
	@RequestMapping("/update")
	public DataOutResponse update(HttpServletRequest request,
			@ModelAttribute LeaninPatientRelaDao leaninPatientRelaDao){
		try{
			return leaninPatientRelaService.update(leaninPatientRelaDao);
		}catch(Exception e){
			 return ReturnFomart.retParam(404, e.getMessage());	
		}
	}
	/**
	 * 批量删除
	 * @param request
	 * @param ids
	 * @return
	 */
	@RequestMapping("/deleteByIds")
	public DataOutResponse deleteByIds(HttpServletRequest request,
			@RequestParam("ids") String ids){
		try{
			return leaninPatientRelaService.deleteByIds(ids);
		}catch(Exception e){
			 return ReturnFomart.retParam(404, e.getMessage());	
		}
	}
}
