package com.leanin.controller;

import com.alibaba.fastjson.JSON;
import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.vo.MsgInfoVo;
import com.leanin.domain.vo.OnlineEdu;
import com.leanin.service.MsgInfoService;
import com.leanin.utils.LyOauth2Util;
import com.leanin.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * 短信中心的Controller
 * @author Administrator
 */
@RestController
@RequestMapping(value="msg")
public class MsgInfoController extends BaseController {

	@Autowired
	private MsgInfoService msgInfoService;

	@PreAuthorize("hasAnyAuthority('root','findMsg')")
	@GetMapping("findMsgListByTypeId")
	public DataOutResponse findMsgListByTypeId(@RequestParam Integer page, @RequestParam Integer pageSize,
											   @RequestParam(required=false) Long typeId, @RequestParam(required=false) String msgName) {
		return msgInfoService.findMsgListByTypeId(page, pageSize, typeId, msgName);
	}

	@PreAuthorize("hasAnyAuthority('root','delMsg')")
	@GetMapping("logoutMsg")
	public DataOutResponse logoutMsg(@RequestParam String msgId, @RequestParam int status) {
		return msgInfoService.updateMsgStatus(msgId, status);
	}

	@PreAuthorize("hasAnyAuthority('root','addMsg')")
	@PostMapping("addMsgInfo")
	public DataOutResponse addMsgInfo(@RequestBody MsgInfoVo msgInfo) {
		LyOauth2Util.UserJwt user = getUser(request);
		msgInfo.setMsgCreater(user.getId());
		msgInfo.setMsgCreateWard(user.getWardCode());
		msgInfo.setMsgCreateTime(new Date());
		return msgInfoService.addMsgInfo(msgInfo);
	}

	@PreAuthorize("hasAnyAuthority('root','updateMsg')")
	@PostMapping("updateMsgInfo")
	public DataOutResponse updateMsgInfo(@RequestBody MsgInfoVo msgInfo) {
		return msgInfoService.updateMsgInfo(msgInfo);
	}

	@PreAuthorize("hasAnyAuthority('root','findMsg')")
	@GetMapping("findMsgInfoById")
	public DataOutResponse findMsgInfoById(@RequestParam String msgId) {
		return msgInfoService.findMsgInfoById(msgId);
	}

	//手动发送短信
	@PreAuthorize("hasAnyAuthority('root','sendMsg')")
	@GetMapping("sendMessage")
	public DataOutResponse sendMessage(@RequestParam("ids") String ids,@RequestParam("plantype") Integer type,String formId){
//		List<Long> longs = JSON.parseArray(ids, Long.class);
		String[] idList = ids.split(",");
		return msgInfoService.sendMessage(idList,type,formId);
	}

	//在线宣教
	@PreAuthorize("hasAnyAuthority('root','sendMsg')")
	@PostMapping("sendEduMessage")
	public DataOutResponse sendEduMessage(@RequestBody List<OnlineEdu> onlineEdus){
		LyOauth2Util.UserJwt user = getUser(request);
		for (OnlineEdu edus : onlineEdus) {
			edus.setSendUser(user.getId());
		}
		return msgInfoService.sendEduMessage(onlineEdus);
	}

	private LyOauth2Util.UserJwt getUser(HttpServletRequest httpServletRequest){
		LyOauth2Util lyOauth2Util = new LyOauth2Util();
		LyOauth2Util.UserJwt userJwt = lyOauth2Util.getUserJwtFromHeader(httpServletRequest);
		return userJwt;
	}
}
