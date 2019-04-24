package com.leanin.service.impl;

import com.alibaba.fastjson.JSON;
import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.response.ReturnFomart;
import com.leanin.domain.vo.PatientInfoVo;
import com.leanin.exception.CustomException;
import com.leanin.exception.ExceptionCast;
import com.leanin.mapper.PatientInfoMapper;
import com.leanin.model.response.CommonCode;
import com.leanin.service.PatientInfoService;
import com.leanin.utils.CompareUtil;
import com.leanin.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 
 * @author Administrator
 *
 */
@Service
@Slf4j
public class PatientInfoServiceImpl implements PatientInfoService {

	@Autowired
	private PatientInfoMapper patientInfoMapper;
	
	@Autowired
	private StringRedisTemplate stringRedisTemplate;


	@Override
	@Transactional(rollbackFor=Exception.class)
	public DataOutResponse addPatientInfo(PatientInfoVo patientInfo) {
		log.info("新增的病人信息为:"+ JSON.toJSONString(patientInfo));
		if(patientInfo.getPatientInfoId() == null){
			return ReturnFomart.retParam(4000, "patientInfoId参数为空！");
		}
		/*String areaCode=stringRedisTemplate.opsForValue().get("areaCode");
		log.info("院区编码为:"+areaCode);*/
		PatientInfoVo patient=patientInfoMapper.findPatientById(patientInfo.getPatientInfoId(), null);
		if(CompareUtil.isNotEmpty(patient)) {
			//更新
			patientInfoMapper.updatePatientInfo(patientInfo);
		}else{
			//插入
		//插入创建时间
		patientInfo.setCreateTime(new Date());
		patientInfoMapper.addPatientInfo(patientInfo);
		}
		return ReturnFomart.retParam(200, patientInfo);
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public DataOutResponse updatePatientInfo(PatientInfoVo patientInfo) {
		log.info("修改的病人信息为:"+ JSON.toJSONString(patientInfo));
		if(patientInfo.getPatientInfoId() == null || "".equals(patientInfo.getPatientInfoId())){
			ExceptionCast.cast(CommonCode.INVALID_PARAM);
		}
		PatientInfoVo patientById = patientInfoMapper.findPatientById(patientInfo.getPatientInfoId(), null);
		if(patientById == null){//判断本地数据库中是否有 患者信息 有则修改  无则添加
			patientInfoMapper.addPatientInfo(patientInfo);
		}else {
			patientInfoMapper.updatePatientInfo(patientInfo);
		}

		return ReturnFomart.retParam(200, patientInfo);
	}

	@Override
	public DataOutResponse findPatientInfoList(String patientName, String patientId, String beginDate, String endDate) {
		log.info("病人的姓名为:"+patientName+"-"+"建档编号为:"+patientId+"-"+"建档起始时间:"+beginDate+"-"+"建档结束时间:"+endDate);
		String areaCode=stringRedisTemplate.opsForValue().get("areaCode");
		log.info("院区编码为:"+areaCode);
		List<PatientInfoVo> patientInfo=patientInfoMapper.findPatientInfoList(patientName, areaCode, patientId, beginDate, endDate);
		return ReturnFomart.retParam(200, patientInfo);
	}

	@Override
	public DataOutResponse findPatientById(String patientId) {
		String patientInfo=stringRedisTemplate.opsForValue().get("patient_"+patientId);
		PatientInfoVo patient=null;
		if(StringUtils.isEmpty(patientInfo)) {
			String areaCode=stringRedisTemplate.opsForValue().get("areaCode");
			log.info("院区编码为:"+areaCode);
			patient=patientInfoMapper.findPatientById(patientId, areaCode);
			log.info("查询的病人信息为:"+ JSON.toJSONString(patient));
			stringRedisTemplate.opsForValue().set("patient_"+patientId, JSON.toJSONString(patient));
		} else {
			patient= JsonUtil.json2Obj(patientInfo, PatientInfoVo.class);
		}
		return ReturnFomart.retParam(200, patient);
	}

}
