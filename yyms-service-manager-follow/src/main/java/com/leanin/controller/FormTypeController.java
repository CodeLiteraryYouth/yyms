package com.leanin.controller;

import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.vo.FormTypeVo;
import com.leanin.service.FormTypeService;
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

	/**
	 * 分页展示表单分类
	 * @param page  当前页
	 * @param pageSize 每页展示条数
	 * @param type 类型
	 * @return
	 */
	@GetMapping("findTypeList")
	public DataOutResponse findTypeList(@RequestParam("page")Integer page,@RequestParam("pageSize")Integer pageSize,@RequestParam("type") Integer type) {
		return formTypeService.findTypeList(page,pageSize,type);
	}
	
	
	@PostMapping("addFormType")
	public DataOutResponse addFormType(@RequestBody FormTypeVo formType) {
		return formTypeService.addFormType(formType);
	}

	/**
	 * 删除分类
	 * @param typeId 分类主键
	 * @return
	 */
	@GetMapping("logoutFormType")
	public DataOutResponse logoutFormType(@RequestParam Long typeId) {
		return formTypeService.updateTypeStatus(typeId);
	}
	
	@PostMapping("updateFormType")
	public DataOutResponse updateFormType(@RequestBody FormTypeVo formType) {
		return formTypeService.updateFormType(formType);
	}
}
