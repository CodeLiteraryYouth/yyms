package com.leanin.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.response.ReturnFomart;
import com.leanin.domain.vo.MsgInfoVo;
import com.leanin.domain.vo.PlanInfoVo;
import com.leanin.domain.vo.PlanPatientVo;
import com.leanin.mapper.MsgInfoMapper;
import com.leanin.mapper.PlanInfoMapper;
import com.leanin.mapper.PlanPatientMapper;
import com.leanin.service.MsgInfoService;
import com.leanin.utils.CSMSUtils;
import com.leanin.utils.CompareUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class MsgInfoServiceImpl implements MsgInfoService {

	@Autowired
	private MsgInfoMapper msgInfoMapper;

	@Autowired
	PlanPatientMapper planPatientMapper;

	@Autowired
	PlanInfoMapper planInfoMapper;

	@Override
	public DataOutResponse findMsgListByTypeId(Integer page, Integer pageSize, Long typeId, String msgName) {
		log.info("分类ID:"+typeId+"-"+"提醒名称:"+msgName);
		PageHelper.startPage(page, pageSize);
		List<MsgInfoVo> msgInfo=msgInfoMapper.findMsgListByTypeId(typeId, msgName);
		PageInfo pageInfo=new PageInfo<>(msgInfo);
		return ReturnFomart.retParam(200, pageInfo);
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public DataOutResponse updateMsgStatus(String msgId, int status) {
		log.info("提醒主键:"+msgId+"-"+"提醒状态:"+status);
		msgInfoMapper.updateMsgStatus(msgId, status);
		return ReturnFomart.retParam(200, status);
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public DataOutResponse addMsgInfo(MsgInfoVo record) {
		log.info("增加提醒库的信息为:"+ JSON.toJSONString(record));
		MsgInfoVo msg=msgInfoMapper.findMsgInfoByName(record.getMsgName());
		if(CompareUtil.isNotEmpty(msg)) {
			return ReturnFomart.retParam(4000, msg);
		}
		msgInfoMapper.addMsgInfo(record);
		return ReturnFomart.retParam(200, record);
	}

	@Override
	public DataOutResponse findMsgInfoById(String msgId) {
		MsgInfoVo msg=msgInfoMapper.findMsgInfoById(msgId);
		log.info("查询的提醒信息为:"+ JSON.toJSONString(msg));
		return ReturnFomart.retParam(200, msg);
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public DataOutResponse updateMsgInfo(MsgInfoVo record) {
		log.info("修改的提醒库信息为:"+ JSON.toJSONString(record));
		msgInfoMapper.updateMsgInfo(record);
		return ReturnFomart.retParam(200, record);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public DataOutResponse sendMessage(List<Long> longs) {
		for (Long aLong : longs) {
			PlanPatientVo planPatient =planPatientMapper.findPlanPatientById(aLong);
			PlanInfoVo planInfoVo = planInfoMapper.findPlanInfoById(planPatient.getPlanNum());

			MsgInfoVo msgInfo = msgInfoMapper.findMsgInfoById(planInfoVo.getMsgId());

			Map map = CSMSUtils.sendMessage(msgInfo.getMsgText()+"测试发送的短信内容哦", "13817165550");
			String msgStatus = (String) map.get("msg");
			if (msgStatus.equals("true")){
				planPatient.setSendType(2); //发送成功
				planPatient.setPlanPatsStatus(1); //修改成带随访状态
			}else {
				planPatient.setSendType(3); //发送失败
			}
			planPatientMapper.updatePlanPatient(planPatient);
		}
		return ReturnFomart.retParam(200, "发送成功");
	}

}
