package com.leanin.service.impl;

import com.alibaba.fastjson.JSON;
import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.response.ReturnFomart;
import com.leanin.domain.vo.PatientRelaVo;
import com.leanin.mapper.PatientRelaMapper;
import com.leanin.service.PatientRelaService;
import com.leanin.utils.CompareUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class PatientRelaServiceImpl implements PatientRelaService {

	@Autowired
	private PatientRelaMapper patientRelaMapper;
	
	@Override
	public DataOutResponse findRelaListByPatientId(String patientId) {
		log.info("病人的唯一标识为:"+patientId);
		List<PatientRelaVo> patientRela=patientRelaMapper.findRelaListByPatientId(patientId);
		return ReturnFomart.retParam(200, patientRela);
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public DataOutResponse updateRelaState(Long patientRelaId, int status) {
		log.info("联系人唯一标识:"+patientRelaId+"状态:"+status);
		patientRelaMapper.updateRelaState(patientRelaId, status);
		return ReturnFomart.retParam(200, null);
	}

	@Override
	@Transactional
	public DataOutResponse addPatientRela(PatientRelaVo record) {
		log.info("增加的联系人信息为:"+ JSON.toJSONString(record));
		PatientRelaVo patientRela=patientRelaMapper.findPatientRelaByName(record.getPatientRelaName());
		if(CompareUtil.isNotEmpty(patientRela)) {
			return ReturnFomart.retParam(4000, patientRela);
		}
		patientRelaMapper.addPatientRela(record);
		return ReturnFomart.retParam(200, record);
	}

}
