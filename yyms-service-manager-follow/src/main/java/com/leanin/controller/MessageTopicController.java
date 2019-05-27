package com.leanin.controller;

import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.vo.MessageTopicVo;
import com.leanin.utils.LyOauth2Util;
import com.leanin.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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

	/**
	 * 分页查询条件查询短信主题
	 * @param page 当前页
	 * @param pageSize 每页展示条数
	 * @param msgTopicName  短信主题名称
	 * @return
	 */
//	@PreAuthorize("hasAnyAuthority('root','findMsgPlan')")
	@GetMapping("findMsgTopicList")
	public DataOutResponse findMsgTopicList(@RequestParam("page") int page, @RequestParam("pageSize") int pageSize, @RequestParam(value="msgTopicName",required=false) String msgTopicName) {
		return messageTopicService.findMsgTopicList(page, pageSize, msgTopicName);
	}

	/**
	 *  修改短信主题状态
	 * @param msgTopicId  短信主题主键
	 * @param status	 状态  1 正在使用  -1 删除
	 * @return
	 */
	@GetMapping("udpateTopicStatus")
	public DataOutResponse udpateTopicStatus(@RequestParam("msgTopicId") String msgTopicId,@RequestParam("status") int status) {
		return messageTopicService.updateTopicStatus(msgTopicId, status);
	}

	/**
	 * 添加短信主题
	 * @param messageTopic
	 * @return
	 */
//	@PreAuthorize("hasAnyAuthority('root','addMsgPlan')")
	@PostMapping("addMsgTopic")
	public DataOutResponse addMsgTopic(@RequestBody MessageTopicVo messageTopic) {
		LyOauth2Util.UserJwt userJwt = getUser(request);
		messageTopic.setMsgTopicCreater(userJwt.getId());
		messageTopic.setMsgTopicState(1);//设置短信主题状态  1 正在使用 -1 删除
		return messageTopicService.addMsgTopic(messageTopic);
	}

	/**
	 * 根据主键查询短信主题
	 * @param msgTopicId  短信主题主键
	 * @return
	 */
//	@PreAuthorize("hasAnyAuthority('root','findMsgPlan')")
	@GetMapping("findMsgTopicById")
	public DataOutResponse findMsgTopicById(@RequestParam("msgTopicId") String msgTopicId) {
		return messageTopicService.findMsgTopicById(msgTopicId);
	}

	/**
	 * 修改短信主题
	 * @param messageTopic
	 * @return
	 */
//	@PreAuthorize("hasAnyAuthority('root','updateMsgPlan')")
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
