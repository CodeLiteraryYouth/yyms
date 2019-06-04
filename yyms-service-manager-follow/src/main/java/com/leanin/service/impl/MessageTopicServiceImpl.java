package com.leanin.service.impl;

import java.util.*;

import com.leanin.client.ManagerPatientClient;
import com.leanin.config.RabbitMQConfig;
import com.leanin.domain.planpatient.response.PlanPatCode;
import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.response.ReturnFomart;
import com.leanin.domain.vo.MessagePatientVo;
import com.leanin.domain.vo.MessageTopicVo;
import com.leanin.exception.ExceptionCast;
import com.leanin.mapper.MessagePatientMapper;
import com.leanin.model.response.CommonCode;
import com.leanin.utils.CompareUtil;
import com.leanin.utils.UUIDUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leanin.mapper.MessageTopicMapper;
import com.leanin.service.MessageTopicService;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MessageTopicServiceImpl implements MessageTopicService {

	@Autowired
	MessageTopicMapper messageTopicMapper;

	@Autowired
	ManagerPatientClient managerPatientClient;

	@Autowired
	MessagePatientMapper messagePatientMapper;

	@Autowired
	RedisTemplate redisTemplate;

	@Autowired
	RabbitTemplate rabbitTemplate;
	
	@Override
	public DataOutResponse findMsgTopicList(int page, int pageSize, String msgTopicName,Long uid) {
		log.info("短信主题名称为:"+msgTopicName);
		PageHelper.startPage(page, pageSize);
		List<MessageTopicVo> messageList=messageTopicMapper.findMsgTopicList(msgTopicName,uid);
		PageInfo pageInfo=new PageInfo<>(messageList);
		return ReturnFomart.retParam(200, pageInfo);
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public DataOutResponse updateTopicStatus(String msgTopicId, int status) {
		log.info("修改的短信主题主键为："+msgTopicId+"");
		if(status == -1){
			//删除短信主题时查看短信主题的患者是否已经发送过短信内容  已经发送则不能删除
			MessagePatientVo messagePatientVo = messagePatientMapper.findBymsgTopicIdAndSendState(msgTopicId,1);
			if (null != messagePatientVo ){
				return ReturnFomart.retParam(5601,msgTopicId);
			}
		}
		MessageTopicVo messageTopic=messageTopicMapper.findMsgTopicById(msgTopicId);
		if (messageTopic == null){//短信主题不存在
			log.info("短信主题不存在"+msgTopicId);
			return ReturnFomart.retParam(5100,msgTopicId);
		}
		messageTopicMapper.updateTopicStatus(msgTopicId, status);
		return ReturnFomart.retParam(200, messageTopic);
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public DataOutResponse addMsgTopic(MessageTopicVo record) {
		log.info("增加的短信主题信息为:"+JSON.toJSONString(record));
		String uuid = UUIDUtils.getUUID();
		record.setMsgTopicId(uuid);
		MessageTopicVo messageTopic=messageTopicMapper.findMsgTopicById(record.getMsgTopicId());
		if(CompareUtil.isNotEmpty(messageTopic)) {
			ExceptionCast.cast(PlanPatCode.DATA_ERROR);
		}
		/*if (record.getMsgEndDate().before( record.getMsgStartDate())){

		}*/
		record.setCreateDate(new Date());
		messageTopicMapper.addMsgTopic(record);
		MessageTopicVo messageTopicVo = messageTopicMapper.findMsgTopicById(uuid);
		log.info("添加的短信主题信息"+JSON.toJSONString(messageTopicVo));
		if (messageTopicVo == null){
			ExceptionCast.cast(PlanPatCode.FAIL);
		}
		// 封装参数
		Map paramMap = new HashMap();
		//科室集合
//		String patsWardCode = messageTopicVo.getPatsWardCode();
//		List<String> patsWardCodeList = JSON.parseArray(patsWardCode, String.class);
		//疾病集合
		String diseaseCode = messageTopicVo.getDiseaseName();
		if (diseaseCode != null && !"[]".equals(diseaseCode)){
			List<String> diseaseCodeList = JSON.parseArray(diseaseCode, String.class);
			paramMap.put("diseaseCode", diseaseCodeList);//疾病编码
		}
//		paramMap.put("patsWardCode", patsWardCodeList);// 患者随访科室编码 可能是集合
//		paramMap.put("planSex", 1);//病人性别 1男 2女
		paramMap.put("beginDate", messageTopicVo.getMsgStartDate());//开始区间
		paramMap.put("endDate", messageTopicVo.getMsgEndDate());//结束区间
//		String planAgeInterval = planResult.getPlanAgeInterval();//年龄区间
//		if (planAgeInterval != null) {//年龄区间
//			String[] split = planAgeInterval.split(",");
//			paramMap.put("startAge", split[0]);
//			paramMap.put("endAge", split[1]);
//		}
//		paramMap.put("planExisPhone", planResult.getPlanExisPhone());//有无联系方式 1有 2无
		//判断病人来源 (1:出院 2：门诊 3：在院 4：体检 5：建档)
		switch (messageTopicVo.getPatientType()) {
			case 1: {//出院
				paramMap.put("inOut", 2);// 1在院  2出院
				Map datamap = managerPatientClient.findInHosPatientByParamToSF(paramMap);
				//调用服务发生异常
				Object error = datamap.get("error");
				if ((!"".equals(error) && error !=null) || datamap == null){
					ExceptionCast.cast(CommonCode.FEIGN_ERROR);
				}
				if (datamap != null) {
					List<Map> list = (List<Map>) datamap.get("list");
					redisTemplate.boundHashOps("msgPlan").put(messageTopicVo.getMsgTopicId(), list);
				}
			}
			break;
			case 2: {//门诊
				ExceptionCast.cast(CommonCode.DATA_ERROR);
				Map datamap = null;
				datamap = managerPatientClient.findOutHosPatientByParamToSF(paramMap);
				//调用服务发生异常
				Object error = datamap.get("error");
				if (!"".equals(error) || error !=null){
					ExceptionCast.cast(CommonCode.FEIGN_ERROR);
				}
				if (datamap != null) {
					List<Map> list = (List<Map>) datamap.get("list");
					redisTemplate.boundHashOps("msgPlan").put(messageTopicVo.getMsgTopicId(), list);
				}
			}
			break;
			case 3: {//在院
				Map datamap = null;
				paramMap.put("inOut", 1);// 1在院  2出院
				datamap = managerPatientClient.findInHosPatientByParamToSF(paramMap);
				//调用服务发生异常
				Object error = datamap.get("error");
				if ((!"".equals(error) && error !=null) || datamap == null){
					ExceptionCast.cast(CommonCode.FEIGN_ERROR);
				}
				if (datamap != null) {
					List<Map> list = (List<Map>) datamap.get("list");
					redisTemplate.boundHashOps("msgPlan").put(messageTopicVo.getMsgTopicId(), list);
				}
			}
			break;
			case 4: {//体检
				ExceptionCast.cast(CommonCode.DATA_ERROR);
			}
			break;
			case 5: {//建档
				ExceptionCast.cast(CommonCode.DATA_ERROR);
			}
			break;
			default: {
				ExceptionCast.cast(CommonCode.DATA_ERROR);
			}
			break;
		}
		//发送消息   参数1 交换机  参数2 路由地址  参数3 需要发送的数据
		rabbitTemplate.convertAndSend(RabbitMQConfig.Exchange_NAME_SEND,RabbitMQConfig.EX_ROUTING_SEND,messageTopicVo.getMsgTopicId());
		log.info("rabbitMQ 传递的参数：{}",messageTopicVo.getMsgTopicId());

		return ReturnFomart.retParam(200, record);
	}

	@Override
	public DataOutResponse findMsgTopicById(String msgTopicId) {
		MessageTopicVo messageTopic=messageTopicMapper.findMsgTopicById(msgTopicId);
		if (messageTopic == null){
			log.info("短信主题为空:"+msgTopicId);
			return ReturnFomart.retParam(5100,msgTopicId);
		}
		log.info("短信主题信息为:"+JSON.toJSONString(messageTopic));
		return ReturnFomart.retParam(200, messageTopic);
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public DataOutResponse updateMsgTopic(MessageTopicVo record) {
		log.info("修改的短信主题信息为:"+JSON.toJSONString(record));
		if(CompareUtil.isEmpty(record.getMsgTopicId())) {
			//修改的短信主题单号不能为空
			return ReturnFomart.retParam(96, record.getMsgTopicId());
		}
		messageTopicMapper.updateMsgTopic(record);
		return ReturnFomart.retParam(200, record);
	}

}
