package com.leanin.service;


import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.vo.FormTypeVo;

public interface FormTypeService {

	/**
	 * 查询表单列表
	 * @return
	 */
	DataOutResponse findTypeList(Integer typeStatus);
	/**
	 * 增加表单类型
	 * @param record
	 * @return
	 */
	DataOutResponse addFormType(FormTypeVo record);
    
    /**
     * 注销表单分类
     * @param typeId
     * @return
     */
	DataOutResponse updateTypeStatus(Long typeId);

    /**
     * 修改表单类型
     * @param record
     * @return
     */
	DataOutResponse updateFormType(FormTypeVo record);
}
