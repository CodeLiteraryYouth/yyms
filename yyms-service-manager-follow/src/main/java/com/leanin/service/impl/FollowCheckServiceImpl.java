package com.leanin.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.response.ReturnFomart;
import com.leanin.domain.vo.FollowCheckVo;
import com.leanin.mapper.FollowCheckMapper;
import com.leanin.service.FollowCheckService;
import com.leanin.utils.CompareUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class FollowCheckServiceImpl implements FollowCheckService {

	@Autowired
	private FollowCheckMapper followCheckMapper;
	
	@Override
	public DataOutResponse findCheckList(int page, int pageSize, String checkName) {
		log.info("搜索的抽查名称为:"+checkName);
		PageHelper.startPage(page, pageSize);
		List<FollowCheckVo> checkList=followCheckMapper.findCheckList(checkName);
		PageInfo pageInfo=new PageInfo<>(checkList);
		return ReturnFomart.retParam(200, pageInfo);
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public DataOutResponse updateCheckStatus(String checkNum, int status) {
		followCheckMapper.updateCheckStatus(checkNum, status);
		FollowCheckVo followCheck=followCheckMapper.findCheckById(checkNum);
		log.info("修改状态的抽查信息为:"+ JSON.toJSONString(followCheck));
		return ReturnFomart.retParam(200, followCheck);
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public DataOutResponse addCheckInfo(FollowCheckVo record) {
		log.info("增加的抽查信息为:"+ JSON.toJSONString(record));
		FollowCheckVo followCheck=followCheckMapper.findCheckByName(record.getCheckName());
		if(CompareUtil.isNotEmpty(followCheck)) {
			//该抽查计划已经存在,请勿重复添加
			return ReturnFomart.retParam(4000, followCheck);
		}
		followCheckMapper.addCheckInfo(record);
		return ReturnFomart.retParam(200, record);
	}

	@Override
	public DataOutResponse findCheckById(String checkNum) {
		FollowCheckVo followCheck=followCheckMapper.findCheckById(checkNum);
		log.info("修改状态的抽查信息为:"+ JSON.toJSONString(followCheck));
		return ReturnFomart.retParam(200, followCheck);
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public DataOutResponse updateCheckInfo(FollowCheckVo record) {
		log.info("修改的抽查信息为:"+ JSON.toJSONString(record));
		followCheckMapper.updateCheckInfo(record);
		return ReturnFomart.retParam(200, record);
	}

}
