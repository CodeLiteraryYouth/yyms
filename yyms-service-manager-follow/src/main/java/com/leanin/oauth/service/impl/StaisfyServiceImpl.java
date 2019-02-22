package com.leanin.oauth.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.response.ReturnFomart;
import com.leanin.domain.vo.SatisfyInfoVo;
import com.leanin.mapper.SatisfyInfoMapper;
import com.leanin.oauth.service.SatisfyService;
import com.leanin.utils.CompareUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class StaisfyServiceImpl implements SatisfyService {

	@Autowired
	private SatisfyInfoMapper satisfyInfoMapper;
	
	@Override
	public DataOutResponse findSatisfyList(Integer page, Integer pageSize, Long typeId, String satisfyName) {
		log.info("类型ID"+typeId+"满意度表单名称:"+satisfyName);
		PageHelper.startPage(page, pageSize);
		List<SatisfyInfoVo> satisfyList=satisfyInfoMapper.findSatisfyList(typeId, satisfyName);
		PageInfo pageInfo=new PageInfo<>(satisfyList);
		return ReturnFomart.retParam(200, pageInfo);
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public DataOutResponse logoutSatisfyInfo(String satisfyNum) {
		SatisfyInfoVo satisfyInfo=satisfyInfoMapper.findSatisfyById(satisfyNum);
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
		satisfyInfoMapper.addSatisfyInfo(record);
		return ReturnFomart.retParam(200, record);
	}

	@Override
	public DataOutResponse findSatisfyById(String satisfyNum) {
		SatisfyInfoVo satisfyInfo=satisfyInfoMapper.findSatisfyById(satisfyNum);
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

}
