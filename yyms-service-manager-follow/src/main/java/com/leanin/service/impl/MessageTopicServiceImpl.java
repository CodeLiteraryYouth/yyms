package com.leanin.service.impl;

import java.util.List;

import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.response.ReturnFomart;
import com.leanin.domain.vo.MessageTopicVo;
import com.leanin.utils.CompareUtil;
import org.springframework.beans.factory.annotation.Autowired;
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
	private MessageTopicMapper messageTopicMapper;
	
	@Override
	public DataOutResponse findMsgTopicList(int page, int pageSize, String msgTopicName) {
		log.info("短信主题名称为:"+msgTopicName);
		PageHelper.startPage(page, pageSize);
		List<MessageTopicVo> messageList=messageTopicMapper.findMsgTopicList(msgTopicName);
		PageInfo pageInfo=new PageInfo<>(messageList);
		return ReturnFomart.retParam(200, pageInfo);
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public DataOutResponse updateTopicStatus(String msgTopicId, int status) {
		MessageTopicVo messageTopic=messageTopicMapper.findMsgTopicById(msgTopicId);
		log.info("改变状态的短信主题信息为:"+JSON.toJSONString(messageTopic));
		messageTopicMapper.updateTopicStatus(msgTopicId, status);
		return ReturnFomart.retParam(200, messageTopic);
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public DataOutResponse addMsgTopic(MessageTopicVo record) {
		log.info("增加的短信主题信息为:"+JSON.toJSONString(record));
		MessageTopicVo messageTopic=messageTopicMapper.findMsgTopicByName(record.getMsgTopicTitle());
		if(CompareUtil.isNotEmpty(messageTopic)) {
			return ReturnFomart.retParam(4000, messageTopic);
		}
		messageTopicMapper.addMsgTopic(record);
		return ReturnFomart.retParam(200, record);
	}

	@Override
	public DataOutResponse findMsgTopicById(String msgTopicId) {
		MessageTopicVo messageTopic=messageTopicMapper.findMsgTopicById(msgTopicId);
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
