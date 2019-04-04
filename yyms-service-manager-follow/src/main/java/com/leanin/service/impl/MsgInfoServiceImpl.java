package com.leanin.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.response.ReturnFomart;
import com.leanin.domain.vo.*;
import com.leanin.mapper.*;
import com.leanin.service.MsgInfoService;
import com.leanin.utils.CSMSUtils;
import com.leanin.utils.CompareUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class MsgInfoServiceImpl implements MsgInfoService {

	@Autowired
	MsgInfoMapper msgInfoMapper;

	@Autowired
	MsgRecordMapper msgRecordMapper;

	@Autowired
	PlanPatientMapper planPatientMapper;

	@Autowired
	PlanInfoMapper planInfoMapper;

	@Autowired
	SatisfyPlanMapper satisfyPlanMapper;

	@Autowired
	SatisfyPatientMapper satisfyPatientMapper;

	@Autowired
	MessageTopicMapper messageTopicMapper;

	@Autowired
	MessagePatientMapper messagePatientMapper;

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
	public DataOutResponse sendMessage(String[] longs,Integer type,String formId) {
		switch (type){
			case 1://随访
				followPlan(longs,formId);
				break;
			case 2://宣教
				followPlan(longs,formId);
				break;
			case 3:// 满意度
				styPlan(longs,formId);
				break;
			case 4://短信主题
				msgPlan(longs,formId);
				break;
		}

		return ReturnFomart.retParam(200, "发送成功");
	}

	private void  followPlan(String[] longs,String formId){
		for (String Longstr : longs) {
			long aLong = Long.parseLong(Longstr);
			PlanPatientVo planPatient =planPatientMapper.findPlanPatientById(aLong);
			PlanInfoVo planInfoVo = planInfoMapper.findPlanInfoById(planPatient.getPlanNum());

			MsgInfoVo msgInfo = msgInfoMapper.findMsgInfoById(planInfoVo.getMsgId());
			Map map = new HashMap();
			String param = "";
			if (planInfoVo.getPlanType() == 1){//随访计划
				param = "http://192.168.0.123:8081/login#/postlist?planPatientId="+planPatient.getPatientPlanId()+"&palnType=1&formNum="+planInfoVo.getFollowFormNum();
				map = CSMSUtils.sendMessage(msgInfo.getMsgText()+param, "18556531536");
			}else {//宣教
				if (formId != null){
					param = "http://192.168.0.123:8081/login#/education?planPatientId="+planPatient.getPatientPlanId()+"&palnType=2&formNum="+planInfoVo.getFollowFormNum();
				}else {
					param = "http://192.168.0.123:8081/login#/education?planPatientId=" + planPatient.getPatientPlanId() + "&palnType=2&formNum=" + formId;
				}
				map = CSMSUtils.sendMessage(msgInfo.getMsgText()+param, "13675853622");
			}
			String msgStatus = (String) map.get("msg");
			if (msgStatus.equals("true")){
				planPatient.setSendType(2); 		//发送成功
				planPatient.setFormStatus(1);		//设置成表单未完成状态
				planPatient.setPlanPatsStatus(1); 	//修改成带随访状态
			}else {
				planPatient.setSendType(3); //发送失败
			}
			planPatientMapper.updatePlanPatient(planPatient);
			msgRecordMapper.addMsgRecord(new MessageRecord(null,planInfoVo.getPlanDutyPer(),planInfoVo.getPlanWardCode(),new Date(),
					planPatient.getPatientPhone(),msgInfo.getMsgText(),planPatient.getSendType(),null,planInfoVo.getPlanType(),planPatient.getPatientPlanId()));
		}
	}

	private void styPlan(String[] longs,String formId){
		for (String LongStr : longs) {
			long aLong = Long.parseLong(LongStr);
			SatisfyPatientVo satisfyPatientVo = satisfyPatientMapper.selectByPrimaryKey(aLong);
			SatisfyPlanVo satisfyPlan = satisfyPlanMapper.findSatisfyPlanById(satisfyPatientVo.getSatisfyPlanNum());
			MsgInfoVo msgInfo = msgInfoMapper.findMsgInfoById(satisfyPlan.getMsgId());
			String param = "http://192.168.0.123:8081/login#/satisfied?planPatientId="+satisfyPatientVo.getPatientSatisfyId()+"&palnType=3&formNum="+satisfyPlan.getSatisfyNum();
			Map map = CSMSUtils.sendMessage(msgInfo.getMsgText()+param,"13675853622" /*satisfyPatientVo.getPatientPhone()*/);
			String msgStatus = (String) map.get("msg");
			if (msgStatus.equals("true")){
				satisfyPatientVo.setSendType(2); //发送成功
				satisfyPatientVo.setFormStatus(1);//表单未完成
			}else {
				satisfyPatientVo.setSendType(3); //发送失败
			}
			satisfyPatientMapper.updateByPrimaryKeySelective(satisfyPatientVo);
			msgRecordMapper.addMsgRecord(new MessageRecord(null,satisfyPlan.getDiscoverPerson(),satisfyPlan.getSatisfyPlanWard(),new Date(),
					satisfyPatientVo.getPatientPhone(),msgInfo.getMsgText(),satisfyPatientVo.getSendType(),null,3,satisfyPatientVo.getPatientSatisfyId()));
		}
	}

	private void msgPlan(String[] longs,String formId){
		for (String LongStr : longs) {
			long aLong = Long.parseLong(LongStr);
			MessagePatientVo messagePatientVo=messagePatientMapper.findById(aLong);
			MessageTopicVo msgTopic = new MessageTopicVo();
			if (formId != null ){
				msgTopic = messageTopicMapper.findMsgTopicById(formId);
			}else{
				msgTopic = messageTopicMapper.findMsgTopicById(messagePatientVo.getMsgTopicId());
			}
			String content =msgTopic.getMsgTopicHead()+msgTopic.getMsgContent();
			Map map = CSMSUtils.sendMessage(content,"13675853622"
					/*messagePatientVo.getPatientPhone()*/);
			String msgStatus = (String) map.get("msg");
			if (msgStatus.equals("true")){
				messagePatientVo.setSendType(2);//发送成功
			}else{
				messagePatientVo.setSendType(3);//发送失败
			}
			messagePatientMapper.updateByPrimaryKeySelective(messagePatientVo);
			msgRecordMapper.addMsgRecord(new MessageRecord(null,msgTopic.getMsgTopicCreater(),msgTopic.getMsgTopicCreaterWard(),
					new Date(),messagePatientVo.getPatientPhone(),content,messagePatientVo.getSendType(),msgTopic.getMsgTopicTitle(),4,messagePatientVo.getPatientMsgId()));
		}
	}

}
