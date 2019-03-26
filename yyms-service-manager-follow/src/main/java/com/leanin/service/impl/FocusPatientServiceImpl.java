package com.leanin.service.impl;

import com.alibaba.fastjson.JSON;
import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.response.ReturnFomart;
import com.leanin.domain.vo.FocusPatientVo;
import com.leanin.mapper.FocusPatientMapper;
import com.leanin.service.FocusPatientService;
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
public class FocusPatientServiceImpl implements FocusPatientService {

	@Autowired
	private FocusPatientMapper focusPatientMapper;
	
	@Autowired
	private StringRedisTemplate redisTemplate;
	
	@Override
	public DataOutResponse findPatientList(String patientName,Long userId) {
		log.info("查询的病人姓名为:"+patientName);
//		String patientJson=redisTemplate.opsForValue().get("patient_"+patientName);
		List<FocusPatientVo> patientList=null;
//		if(StringUtils.isEmpty(patientJson)) {
			patientList=focusPatientMapper.findPatientList(patientName);
			log.info("查询的关注病人列表信息为:"+ JSON.toJSONString(patientList));
//			redisTemplate.opsForValue().set("patient_"+patientName, JSON.toJSONString(patientList));
//		} else {
//			patientList= JsonUtil.json2list(patientJson, FocusPatientVo.class);
//		}
		return ReturnFomart.retParam(200, patientList);
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public DataOutResponse updatePatientStatus(Long focusId, Integer status) {
		log.info("关注的病人主键为:"+focusId+"-"+"病人状态为:"+status);
		FocusPatientVo focusPatientVo = focusPatientMapper.findPatientByFocusId(focusId);
		if (focusPatientVo == null ){
			return ReturnFomart.retParam(300,"数据不存在");
		}
		focusPatientMapper.updatePatientStatus(focusId, status);
		return ReturnFomart.retParam(200, "操作成功");
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public DataOutResponse insertFocusPatient(FocusPatientVo record) {
		log.info("新增的关注病人信息为:"+ JSON.toJSONString(record));
		FocusPatientVo patient=focusPatientMapper.selectFocusPatientById(record.getPatientId(),record.getUserId());
		if(CompareUtil.isNotEmpty(patient)) {
			return ReturnFomart.retParam(4000, "已经关注请勿重新添加");
		}
		focusPatientMapper.insertFocusPatient(record);
		return ReturnFomart.retParam(200, record);
	}

	@Override
	public DataOutResponse selectFocusPatientById(String patientId,Long userId) {
		String patientJson=redisTemplate.opsForValue().get("patient_"+patientId);
		FocusPatientVo patient=null;
		if(StringUtils.isEmpty(patientJson)) {
			patient=focusPatientMapper.selectFocusPatientById(patientId,userId);
			log.info("查询的关注病人信息为:"+ JSON.toJSONString(patient));
			redisTemplate.opsForValue().set("patient_"+patientId, JSON.toJSONString(patient));
		} else {
			patient=JsonUtil.json2Obj(patientJson, FocusPatientVo.class);
		}
		return ReturnFomart.retParam(200, patient);
	}

}
