package com.leanin.service.impl;

import java.util.List;

import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.response.ReturnFomart;
import com.leanin.domain.vo.SatisfyPlanVo;
import com.leanin.utils.CompareUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leanin.mapper.SatisfyPlanMapper;
import com.leanin.service.SatisfyPlanService;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Administrator
 */
@Service
@Slf4j
public class SatisfyPlanServiceImpl implements SatisfyPlanService {

	@Autowired
	private SatisfyPlanMapper satisfyPlanMapper;
	
	@Override
	public DataOutResponse findSatisfyPlanList(int page, int pageSize, String satisfyPlanName) {
		log.info("搜索的满意度计划名称为:"+satisfyPlanName);
		PageHelper.startPage(page, pageSize);
		List<SatisfyPlanVo> satisfyPlanList=satisfyPlanMapper.findSatisfyPlanList(satisfyPlanName);
		PageInfo pageInfo=new PageInfo<>(satisfyPlanList);
		return ReturnFomart.retParam(200, pageInfo);
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public DataOutResponse updateSatisfyStatus(String planSatisfyNum, int status) {
		log.info("满意度表单号为:"+planSatisfyNum+"-"+"状态为:"+status);
		satisfyPlanMapper.updateSatisfyStatus(planSatisfyNum, status);
		SatisfyPlanVo satisfyPlan=satisfyPlanMapper.findSatisfyPlanById(planSatisfyNum);
		log.info("修改满意度计划的信息为:"+JSON.toJSONString(satisfyPlan));
		return ReturnFomart.retParam(200, satisfyPlan);
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public DataOutResponse addSatisfyPlan(SatisfyPlanVo record) {
		log.info("增加的满意度计划信息为:"+JSON.toJSONString(record));
		SatisfyPlanVo satisfyPlan=satisfyPlanMapper.findSatisfyPlanByName(record.getPlanSatisfyName());
		if(CompareUtil.isNotEmpty(satisfyPlan)) {
			//如果已经存在该信息,请勿重复添加
			return ReturnFomart.retParam(4000, satisfyPlan);
		}
		satisfyPlanMapper.addSatisfyPlan(record);
		return ReturnFomart.retParam(200, record);
	}

	@Override
	public DataOutResponse findSatisfyPlanById(String planSatisfyNum) {
		SatisfyPlanVo satisfyPlan=satisfyPlanMapper.findSatisfyPlanById(planSatisfyNum);
		log.info("满意度计划的信息为:"+JSON.toJSONString(satisfyPlan));
		return ReturnFomart.retParam(200, satisfyPlan);
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public DataOutResponse updateSatisfyPlan(SatisfyPlanVo record) {
		log.info("修改的满意度计划信息为:"+JSON.toJSONString(record));
		SatisfyPlanVo satisfyPlan=satisfyPlanMapper.findSatisfyPlanById(record.getPlanSatisfyNum());
		if(CompareUtil.isEmpty(satisfyPlan) || satisfyPlan.getPlanStatus()==1) {
			return ReturnFomart.retParam(96, satisfyPlan);
		}
		satisfyPlanMapper.updateSatisfyPlan(record);
		return ReturnFomart.retParam(200, record);
	}

}
