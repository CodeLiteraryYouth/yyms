package com.leanin.oauth.service.impl;

import com.alibaba.fastjson.JSON;
import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.response.ReturnFomart;
import com.leanin.domain.vo.FormTypeVo;
import com.leanin.mapper.FormTypeMapper;
import com.leanin.oauth.service.FormTypeService;
import com.leanin.utils.CompareUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class FormTypeServiceImpl implements FormTypeService {

	@Autowired
	private FormTypeMapper formTypeMapper;
	
	
	@Override
	public DataOutResponse findTypeList(Integer typeStatus) {
		log.info("表单的类型为:"+typeStatus);
		List<FormTypeVo> formType=formTypeMapper.findTypeList(typeStatus);
		return ReturnFomart.retParam(200, formType);
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public DataOutResponse addFormType(FormTypeVo record) {
		log.info("增加的表单类型数据为:"+ JSON.toJSONString(record));
		FormTypeVo formType=formTypeMapper.findFormType(record.getFormTypeName());
		if(CompareUtil.isNotEmpty(formType)) {
			return ReturnFomart.retParam(4000, formType);
		}
		formTypeMapper.addFormType(record);
		return ReturnFomart.retParam(200,record);
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public DataOutResponse updateTypeStatus(Long typeId) {
		log.info("表单类型主键为:"+typeId);
		return ReturnFomart.retParam(200, formTypeMapper.updateTypeStatus(typeId));
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public DataOutResponse updateFormType(FormTypeVo record) {
		log.info("修改的表单类型数据为:"+ JSON.toJSONString(record));
		formTypeMapper.updateFormType(record);
		return ReturnFomart.retParam(200, record);
	}

}
