package com.leanin.oauth.controller;

import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.vo.FormTypeVo;
import com.leanin.oauth.service.FormTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 表单类型的Controller
 * @author Administrator
 *
 */
@RestController
@RequestMapping("type")
public class FormTypeController {

	@Autowired
	private FormTypeService formTypeService;
	
	@GetMapping("findTypeList")
	public DataOutResponse findTypeList(@RequestParam Integer type) {
		return formTypeService.findTypeList(type);
	}
	
	
	@PostMapping("addFormType")
	public DataOutResponse addFormType(@RequestBody FormTypeVo formType) {
		return formTypeService.addFormType(formType);
	}
	
	@GetMapping("logoutFormType")
	public DataOutResponse logoutFormType(@RequestParam Long typeId) {
		return formTypeService.updateTypeStatus(typeId);
	}
	
	@PostMapping("updateFormType")
	public DataOutResponse updateFormType(@RequestBody FormTypeVo formType) {
		return formTypeService.updateFormType(formType);
	}
}
