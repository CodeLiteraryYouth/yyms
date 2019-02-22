package com.leanin.oauth.controller;

import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.vo.MessageTopicVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.leanin.oauth.service.MessageTopicService;


@RestController
@RequestMapping("msgTopic")
public class MessageTopicController {

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
}
