package com.leanin.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leanin.domain.dto.CommonRulesInfoDto;
import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.response.ReturnFomart;
import com.leanin.domain.vo.RulesInfoVo;
import com.leanin.mapper.RulesInfoMapper;
import com.leanin.service.RulesInfoService;
import com.leanin.utils.CompareUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class RulesInfoServiceImpl implements RulesInfoService {

	@Autowired
	private RulesInfoMapper rulesInfoMapper;
	
	@Override
	public DataOutResponse findCommonRules(Integer rulesType, String rulesName) {
		log.info("规则类型为:"+rulesType+"-"+"规则搜索名称:"+rulesName);
		//查询共享规则信息
		List<CommonRulesInfoDto>  commonRules=rulesInfoMapper.findCommonRules(rulesType, rulesName);
		return ReturnFomart.retParam(200, commonRules);
	}

	@Override
	public DataOutResponse findRulesList(Integer page,Integer pageSize,String rulesName, Integer type,Long typeId) {
		log.info("搜索的规则名称为:"+rulesName+"-"+"表单库的类型为:"+type);
		//查询规则列表信息
		PageHelper.startPage(page, pageSize);
		List<RulesInfoVo> rulesInfo=rulesInfoMapper.findRulesList(rulesName, type,typeId);
		PageInfo pageInfo=new PageInfo<>(rulesInfo);
		return ReturnFomart.retParam(200, pageInfo);
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public DataOutResponse updateRulesState(Long rulesInfoId, Integer status) {
		log.info("修改状态的ID为:"+rulesInfoId+"修改的状态为:"+status);
		rulesInfoMapper.updateRulesState(rulesInfoId, status);
		RulesInfoVo rules=rulesInfoMapper.findRulesById(rulesInfoId);
		return ReturnFomart.retParam(200, rules);
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public DataOutResponse addRulesInfo(RulesInfoVo record) {
		log.info("新增的规则信息为:"+ JSON.toJSONString(record));
		//查询是否已存在
		RulesInfoVo rules=rulesInfoMapper.findRulesByName(record.getRulesInfoName());
		if(CompareUtil.isNotEmpty(rules)) {
			return ReturnFomart.retParam(4000, rules);
		}
		rulesInfoMapper.addRulesInfo(record);
		RulesInfoVo result=rulesInfoMapper.findRulesByName(record.getRulesInfoName());
		log.info("返回的规则信息为:"+ JSON.toJSONString(result));
		return ReturnFomart.retParam(200, result);
	}

	@Override
	public DataOutResponse findRulesById(Long rulesInfoId) {
		RulesInfoVo rules=rulesInfoMapper.findRulesById(rulesInfoId);
		log.info("查询的规则信息为:"+ JSON.toJSONString(rules));
		return ReturnFomart.retParam(200, rules);
	}


	@Override
	@Transactional(rollbackFor=Exception.class)
	public DataOutResponse updateRulesInfo(RulesInfoVo record) {
		log.info("修改的规则信息为:"+ JSON.toJSONString(record));
		RulesInfoVo rules=rulesInfoMapper.findRulesById(record.getRulesInfoId());
		if(CompareUtil.isEmpty(rules) || rules.getRulesInfoStatus()==2) {
			//禁用状态下无法编辑
			return ReturnFomart.retParam(96, rules);
		}
		rulesInfoMapper.updateRulesInfo(record);
		return ReturnFomart.retParam(200, record);
	}

	@Override
	public DataOutResponse findRulesByWardCode(Integer page, Integer pageSize, String wardCode, Integer rulesType,
			String rulesName) {
		log.info("院区编码为:"+wardCode+"-"+"规则类型为:"+rulesType+"-"+"规则名称为:"+rulesName);
		PageHelper.startPage(page, pageSize);
		List<RulesInfoVo> rulesInfo=rulesInfoMapper.findRulesByWardCode(wardCode, rulesType, rulesName);
		PageInfo pageInfo=new PageInfo<>(rulesInfo);
		return ReturnFomart.retParam(200,pageInfo);
	}

}
