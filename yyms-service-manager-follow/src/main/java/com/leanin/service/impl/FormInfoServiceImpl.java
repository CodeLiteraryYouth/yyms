package com.leanin.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leanin.domain.dto.CommonFormInfoDto;
import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.response.ReturnFomart;
import com.leanin.domain.vo.FormInfoVo;
import com.leanin.mapper.FormInfoMapper;
import com.leanin.service.FormInfoService;
import com.leanin.utils.CompareUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class FormInfoServiceImpl implements FormInfoService {

	@Autowired
	private FormInfoMapper formInfoMapper;
	
	@Override
	public DataOutResponse findFormList(Integer page, Integer pageSize, String formName, Integer formType) {
		log.info("表单的单名为:"+formName+"-"+"表单的类型为:"+formType);
		PageHelper.startPage(page, pageSize);
		List<FormInfoVo> formList=formInfoMapper.findFormList(formName, formType);
		PageInfo pageInfo=new PageInfo<>(formList);
		return ReturnFomart.retParam(200, pageInfo);
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public DataOutResponse updateFormStatus(String formNum, Integer formStatus) {
		log.info("表单的单号为:"+formNum+"-"+"表单的状态为:"+formStatus);
		FormInfoVo formInfo=formInfoMapper.findFormInfoById(formNum);
		log.info("修改表单状态的信息为:"+ JSON.toJSONString(formInfo));
		//修改表单状态
		formInfoMapper.updateFormStatus(formNum, formStatus);
		return ReturnFomart.retParam(200, formInfo);
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public DataOutResponse addFormInfo(FormInfoVo record) {
		log.info("新增的表单信息为:"+ JSON.toJSONString(record));
		FormInfoVo formInfo=formInfoMapper.findFormInfoByName(record.getFormName());
		if(CompareUtil.isNotEmpty(formInfo)) {
			return ReturnFomart.retParam(4000, formInfo);
		}
		//新增加表单信息数据
		formInfoMapper.addFormInfo(record);
		return ReturnFomart.retParam(200, record);
	}

	@Override
	public DataOutResponse findFormInfoById(String formNum) {
		FormInfoVo formInfo=formInfoMapper.findFormInfoById(formNum);
		log.info("表单的信息为:"+ JSON.toJSONString(formInfo));
		return ReturnFomart.retParam(200, formInfo);
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public DataOutResponse updateFormInfo(FormInfoVo record) {
		log.info("修改的表单信息为:"+ JSON.toJSONString(record));
		//修改表单数据
		formInfoMapper.updateFormInfo(record);
		return ReturnFomart.retParam(200, record);
	}

	@Override
	public DataOutResponse findCommonForm(Integer page,Integer pageSize,Integer formType, String formName) {
		log.info("表单的类型为:"+formType+"-"+"搜索的表单名为:"+formName);
		PageHelper.startPage(page, pageSize);
		List<CommonFormInfoDto> commonForm=formInfoMapper.findCommonForm(formType, formName);
		PageInfo pageInfo=new PageInfo<>(commonForm);
		return ReturnFomart.retParam(200, pageInfo);
	}

	@Override
	public DataOutResponse findFormListByTypeId(Integer page,Integer pageSize,Long formTypeId, String formName) {
		log.info("分类ID:"+formTypeId+"表单搜索名字:"+formName);
		if(page>0) {
			PageHelper.startPage(page, pageSize);
			List<FormInfoVo> formList=formInfoMapper.findFormListByTypeId(formTypeId, formName);
			PageInfo pageInfo=new PageInfo<>(formList);
			return ReturnFomart.retParam(200, pageInfo);
		} else {
			List<FormInfoVo> formList=formInfoMapper.findFormListByTypeId(formTypeId, formName);
			return ReturnFomart.retParam(200, formList);
		}
	}

	@Override
	public DataOutResponse findFormListByWardCode(Integer page,Integer pageSize,String wardCode, String formName, Integer formType) {
		log.info("病区编码为:"+wardCode+"表单搜索名字:"+formName+"表单类型:"+formType);
		PageHelper.startPage(page, pageSize);
		List<FormInfoVo> formList=formInfoMapper.findFormListByWardCode(wardCode, formName, formType);
		PageInfo pageInfo=new PageInfo<>(formList);
		return ReturnFomart.retParam(200, pageInfo);
	}

}
