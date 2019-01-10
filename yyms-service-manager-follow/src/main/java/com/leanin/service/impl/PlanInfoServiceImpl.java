package com.leanin.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leanin.domain.dto.PlanInfoDto;
import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.response.ReturnFomart;
import com.leanin.domain.vo.PlanInfoVo;
import com.leanin.mapper.PlanInfoMapper;
import com.leanin.service.PlanInfoService;
import com.leanin.utils.CompareUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class PlanInfoServiceImpl implements PlanInfoService {

	@Autowired
	private PlanInfoMapper planInfoMapper;
	
	
	@Override
	public DataOutResponse findPlanList(int page, int pageSize, String planName, int planType) {
		log.info("搜索的计划名称为:"+planName);
		PageHelper.startPage(page, pageSize);
		List<PlanInfoDto> planList=planInfoMapper.findPlanList(planName,planType);
		PageInfo pageInfo=new PageInfo<>(planList);
		return ReturnFomart.retParam(200, pageInfo);
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public DataOutResponse updatePlanStatus(String planNum, int status) {
		log.info("计划编号为:"+planNum +"状态为:"+status);
		PlanInfoVo planInfo=planInfoMapper.findPlanInfoById(planNum);
		log.info("计划信息为:"+planNum);
		if(CompareUtil.isEmpty(planInfo) && planInfo.getPlanStatus()==2) {
			//禁止状态下禁止更改状态
			return ReturnFomart.retParam(96, planInfo);
		}
		planInfoMapper.updatePlanStatus(planNum, status);
		return ReturnFomart.retParam(200, planInfo);
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public DataOutResponse addPlanInfo(PlanInfoVo record) {
		log.info("增加的计划信息为:"+ JSON.toJSONString(record));
		PlanInfoVo planInfo=planInfoMapper.findPlanInfoByName(record.getPlanName());
		if(CompareUtil.isNotEmpty(planInfo)) {
			//如果已经存在该名称,不允许重复添加
			return ReturnFomart.retParam(4000, planInfo);
		}
		planInfoMapper.addPlanInfo(record);
		return ReturnFomart.retParam(200,record);
	}

	@Override
	public DataOutResponse findPlanInfoById(String planNum) {
		PlanInfoVo planInfo=planInfoMapper.findPlanInfoById(planNum);
		log.info("计划信息为:"+planNum);
		return ReturnFomart.retParam(200,planInfo);
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public DataOutResponse updatePlanInfo(PlanInfoVo record) {
		log.info("修改的计划信息为:"+ JSON.toJSONString(record));
		PlanInfoVo planInfo=planInfoMapper.findPlanInfoById(record.getPlanNum());
		if(CompareUtil.isEmpty(planInfo) && planInfo.getPlanStatus()==2) {
			//禁止状态下禁止修改信息
			return ReturnFomart.retParam(96, planInfo);
		}
		planInfoMapper.updatePlanInfo(record);
		return ReturnFomart.retParam(200,record);
	}

}
