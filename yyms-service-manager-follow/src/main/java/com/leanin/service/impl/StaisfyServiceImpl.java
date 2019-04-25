package com.leanin.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.response.ReturnFomart;
import com.leanin.domain.vo.PlanInfoVo;
import com.leanin.domain.vo.SatisfyInfoExt;
import com.leanin.domain.vo.SatisfyInfoVo;
import com.leanin.domain.vo.SatisfyPlanVo;
import com.leanin.mapper.PlanInfoMapper;
import com.leanin.mapper.SatisfyInfoMapper;
import com.leanin.mapper.SatisfyPlanMapper;
import com.leanin.service.SatisfyService;
import com.leanin.utils.CompareUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class StaisfyServiceImpl implements SatisfyService {

	@Autowired
	private SatisfyInfoMapper satisfyInfoMapper;

	@Autowired
	PlanInfoMapper planInfoMapper;

	@Autowired
	SatisfyPlanMapper satisfyPlanMapper;
	
	@Override
	public DataOutResponse findSatisfyList(Integer page, Integer pageSize, Long typeId, String satisfyName,Integer shareStatus) {
		log.info("类型ID"+typeId+"满意度表单名称:"+satisfyName);
		PageHelper.startPage(page, pageSize);
		List<SatisfyInfoVo> satisfyList=satisfyInfoMapper.findSatisfyList(typeId, satisfyName,shareStatus);
		PageInfo pageInfo=new PageInfo<>(satisfyList);
		return ReturnFomart.retParam(200, pageInfo);
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public DataOutResponse logoutSatisfyInfo(String satisfyNum) {
		SatisfyInfoVo satisfyInfo=satisfyInfoMapper.findSatisfyById(satisfyNum);
		if (satisfyInfo == null){
			return ReturnFomart.retParam(1,"信息不存在");
		}
		/*PlanInfoVo planInfoVo = planInfoMapper.findByParamId(msgId,null,null);
		if (planInfoVo != null ){
			return ReturnFomart.retParam(3502,"短信信息已被使用不能删除或者禁用");
		}*/
		SatisfyPlanVo satisfyPlanVo = satisfyPlanMapper.findByParamId(null,satisfyNum);
		if (satisfyPlanVo != null ){
			return ReturnFomart.retParam(3601,"满意度表单已被使用不能删除或者禁用");
		}
		log.info("满意度题型为:"+ JSON.toJSONString(satisfyInfo));
		satisfyInfoMapper.logoutSatisfyInfo(satisfyNum);
		return ReturnFomart.retParam(200, satisfyInfo);
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public DataOutResponse addSatisfyInfo(SatisfyInfoVo record) {
		log.info("新增的满意度题型为:"+ JSON.toJSONString(record));
		SatisfyInfoVo satisfyInfo=satisfyInfoMapper.findSatisfyByName(record.getSatisfyName());
		if(CompareUtil.isNotEmpty(satisfyInfo)) {
			return ReturnFomart.retParam(4000, satisfyInfo);
		}
		record.setSatisfyDate(new Date());
		satisfyInfoMapper.addSatisfyInfo(record);
		return ReturnFomart.retParam(200, record);
	}

	@Override
	public DataOutResponse findSatisfyById(String satisfyNum) {
		SatisfyInfoVo satisfyInfo=satisfyInfoMapper.findSatisfyById(satisfyNum);
		if (satisfyInfo == null){
			return ReturnFomart.retParam(201, "数据为空");
		}
		log.info("满意度题型为:"+ JSON.toJSONString(satisfyInfo));
		return ReturnFomart.retParam(200, satisfyInfo);
	}


	@Override
	@Transactional(rollbackFor=Exception.class)
	public DataOutResponse updateSatisfyInfo(SatisfyInfoVo record) {
		log.info("修改的满意度题型为:"+ JSON.toJSONString(record));
		SatisfyInfoVo satisfyInfo=satisfyInfoMapper.findSatisfyById(record.getSatisfyNum());
		if(CompareUtil.isEmpty(satisfyInfo)) {
			return ReturnFomart.retParam(96, null);
		}
		satisfyInfoMapper.updateSatisfyInfo(record);
		return ReturnFomart.retParam(200, record);
	}

	@Override
	public DataOutResponse findStyInfoByOpenId(String openId, Integer finishType) {
		List<SatisfyInfoExt> list = satisfyInfoMapper.findStyInfoByOpenId(openId,finishType);
		if ( finishType != 1){//1 未完成  2 //已完成  //3过期  //4随访异常
			List<SatisfyInfoExt> satisfyInfoExts =satisfyInfoMapper.findStyInfoByOpenIdExt(openId,finishType);
			list.addAll(satisfyInfoExts);
		}
		return ReturnFomart.retParam(200, list);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public DataOutResponse shareSatisfy(String satisfyNum, Integer status) {
		SatisfyInfoVo satisfy = satisfyInfoMapper.findSatisfyById(satisfyNum);
		if (satisfy == null){
			return ReturnFomart.retParam(1,"信息不存在");
		}
		satisfy.setShareStatus(status);
		satisfyInfoMapper.updateSatisfyInfo(satisfy);
		return ReturnFomart.retParam(200,satisfy);
	}

}
