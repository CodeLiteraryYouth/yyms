package com.leanin.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.response.ReturnFomart;
import com.leanin.domain.vo.WardInfoVo;
import com.leanin.mapper.WardInfoMapper;
import com.leanin.service.WardInfoService;
import com.leanin.utils.CompareUtil;
import com.leanin.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class WardInfoServiceImpl implements WardInfoService {

	@Autowired
	private WardInfoMapper wardInfoMapper;

	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	
	@Override
	public DataOutResponse findWardList(int page, int pageSize, String wardName) {
		log.info("输入的科室名称为:"+wardName);
		PageHelper.startPage(page, pageSize);
		List<WardInfoVo> wardInfo= wardInfoMapper.findWardList(wardName);
		PageInfo pageInfo=new PageInfo<>(wardInfo);
		return ReturnFomart.retParam(200, pageInfo);
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public DataOutResponse deleteById(Long wardId,int status) {
		WardInfoVo wardInfo=wardInfoMapper.selectById(wardId);
		log.info("注销的科室信息为:"+ JSON.toJSONString(wardInfo));
		wardInfoMapper.deleteById(wardId,status);
		return ReturnFomart.retParam(200, wardInfo);
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public DataOutResponse insertWardInfo(WardInfoVo record) {
		log.info("新增的科室信息为:"+ JSON.toJSONString(record));
		WardInfoVo wardInfo=wardInfoMapper.selectByCode(record.getWardCode());
		//如果不为空,代表数据库中村财相同的数据,防止重复添加
		if(wardInfo != null){
			return ReturnFomart.retParam(4001,"科室码重复请勿添加相同的科室");
		}
//		if(CompareUtil.isNotEmpty(wardInfo)) {
//			return ReturnFomart.retParam(4000, null);
//		}
		wardInfoMapper.insertWardInfo(record);
		return ReturnFomart.retParam(200, record);
	}

	@Override
	public DataOutResponse selectById(Long wardId) {
		String ward=stringRedisTemplate.opsForValue().get("ward_"+wardId);
		log.info("缓存中的数据为:"+ward);
		WardInfoVo wardInfo=null;
		if(StringUtils.isEmpty(ward)) {
			wardInfo=wardInfoMapper.selectById(wardId);
			log.info("查询的科室信息为:"+ JSON.toJSONString(wardInfo));
		} else {
			wardInfo= JsonUtil.json2Obj(ward, WardInfoVo.class);
		}
		return ReturnFomart.retParam(200,wardInfo);
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public DataOutResponse updateWardInfo(WardInfoVo record) {
		log.info("修改的科室信息为:"+ JSON.toJSONString(record));
		WardInfoVo wardInfo=wardInfoMapper.selectById(record.getWardId());
		if(CompareUtil.isEmpty(wardInfo)) {
			return ReturnFomart.retParam(96, record);
		}
		wardInfoMapper.updateWardInfo(record);
		return ReturnFomart.retParam(200, record);
	}

	@Override
	public DataOutResponse findlist(int page, int pageSize, String wardName) {
		log.info("输入的科室名称为:"+wardName);
		PageHelper.startPage(page, pageSize);
		List<WardInfoVo> wardInfo= wardInfoMapper.findlist(wardName);
		PageInfo pageInfo=new PageInfo<>(wardInfo);
		return ReturnFomart.retParam(200, pageInfo);
	}

}
