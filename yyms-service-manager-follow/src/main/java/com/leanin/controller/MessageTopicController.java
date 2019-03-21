package com.leanin.controller;

import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.vo.MessageTopicVo;
import com.leanin.utils.LyOauth2Util;
import com.leanin.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.leanin.service.MessageTopicService;

import javax.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("msgTopic")
public class MessageTopicController extends BaseController {
	@Autowired
	private MessageTopicService messageTopicService;
	
	@GetMapping("findMsgTopicList")
	public DataOutResponse findMsgTopicList(@RequestParam int page, @RequestParam int pageSize, @RequestParam(required=false) String msgTopicName) {
		return messageTopicService.findMsgTopicList(page, pageSize, msgTopicName);
	}
	
//	@GetMapping("udpateTopicStatus")
//	public DataOutResponse udpateTopicStatus(@RequestParam String msgTopicId,@RequestParam int status) {
//		return messageTopicService.updateTopicStatus(msgTopicId, status);
//	}
	
	@PostMapping("addMsgTopic")
	public DataOutResponse addMsgTopic(@RequestBody MessageTopicVo messageTopic) {
		LyOauth2Util.UserJwt userJwt = getUser(request);
		messageTopic.setCreaterName(userJwt.getId());
		messageTopic.setMsgTopicCreaterWard(userJwt.getWardCode());
		return messageTopicService.addMsgTopic(messageTopic);
	}
	
	@GetMapping("findMsgTopicById")
	public DataOutResponse findMsgTopicById(@RequestParam String msgTopicId) {
		return messageTopicService.findMsgTopicById(msgTopicId);
	}
	
	@PostMapping("updateMsgTopic")
	public DataOutResponse updateMsgTopic(@RequestBody MessageTopicVo messageTopic) {
		return messageTopicService.updateMsgTopic(messageTopic);
	}

	private LyOauth2Util.UserJwt getUser(HttpServletRequest httpServletRequest){
		LyOauth2Util lyOauth2Util = new LyOauth2Util();
		LyOauth2Util.UserJwt userJwt= lyOauth2Util.getUserJwtFromHeader(httpServletRequest);
		return userJwt;
	}
}
