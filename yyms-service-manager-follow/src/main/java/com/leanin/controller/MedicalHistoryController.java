package com.leanin.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.leanin.domain.LeaninMedicalHistoryDao;
import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.response.ReturnFomart;
import com.leanin.service.MedicalHistoryService;
import com.leanin.web.BaseController;

/**
 * 疾病记录
 * @author zliu
 *
 */
@RestController
@RequestMapping("medicalHistory")
public class MedicalHistoryController extends  BaseController{
	@Autowired
	private MedicalHistoryService medicalHistoryService;
	/**
	 * 查询
	 * @param request
	 * @param patientId
	 * @return
	 */
	@RequestMapping("/listPage")
	public DataOutResponse listPage(HttpServletRequest request,@RequestParam("patientId")String patientId){
		try{
			return medicalHistoryService.listPage(patientId);
		}catch(Exception e){
			 return ReturnFomart.retParam(404, e.getMessage());	
		}
	}
	/**
	 * 根据类型新增
	 * @param request
	 * @param leaninMedicalHistoryDao
	 * @return
	 */
	@RequestMapping("/add")
	public DataOutResponse addByType(HttpServletRequest request,
			@ModelAttribute LeaninMedicalHistoryDao leaninMedicalHistoryDao){
		try{
			return medicalHistoryService.addByType(leaninMedicalHistoryDao);
		}catch(Exception e){
			 return ReturnFomart.retParam(404, e.getMessage());	
		}
	}
	/**
	 * 根据id查看
	 * @param id
	 * @return
	 */
	@RequestMapping("/findByid")
	public DataOutResponse findByid(@RequestParam("id") Long id){
		try{
			return medicalHistoryService.selectByid(id);
		}catch(Exception e){
			 return ReturnFomart.retParam(404, e.getMessage());	
		}
	}
	/**
	 * 更新
	 * @param leaninMedicalHistoryDao
	 * @return
	 */
	@RequestMapping("/update")
	public DataOutResponse updateEntity(@ModelAttribute LeaninMedicalHistoryDao leaninMedicalHistoryDao){
		try{
			return medicalHistoryService.updateEntity(leaninMedicalHistoryDao);
		}catch(Exception e){
			 return ReturnFomart.retParam(404, e.getMessage());	
		}
	}
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@RequestMapping("/deleteByIds")
	public DataOutResponse deleteByIds(@RequestParam("ids") String ids){
		try{
			return medicalHistoryService.deleteByIds(ids);
		}catch(Exception e){
			 return ReturnFomart.retParam(404, e.getMessage());	
		}
	}
}
