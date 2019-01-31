package com.leanin.controller;

import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.vo.MsgInfoVo;
import com.leanin.service.MsgInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value="msg")
public class MsgInfoController {

	@Autowired
	private MsgInfoService msgInfoService;
	
	@GetMapping("findMsgListByTypeId")
	public DataOutResponse findMsgListByTypeId(@RequestParam Integer page, @RequestParam Integer pageSize,
											   @RequestParam(required=false) Long typeId, @RequestParam(required=false) String msgName) {
		return msgInfoService.findMsgListByTypeId(page, pageSize, typeId, msgName);
	}
	
	@GetMapping("logoutMsg")
	public DataOutResponse logoutMsg(@RequestParam String msgId, @RequestParam int status) {
		return msgInfoService.updateMsgStatus(msgId, status);
	}
	
	@PostMapping("addMsgInfo")
	public DataOutResponse addMsgInfo(@RequestBody MsgInfoVo msgInfo) {
		return msgInfoService.addMsgInfo(msgInfo);
	}
	
	@PostMapping("updateMsgInfo")
	public DataOutResponse updateMsgInfo(@RequestBody MsgInfoVo msgInfo) {
		return msgInfoService.updateMsgInfo(msgInfo);
	}
	
	@GetMapping("findMsgInfoById")
	public DataOutResponse findMsgInfoById(@RequestParam String msgId) {
		return msgInfoService.findMsgInfoById(msgId);
	}
}