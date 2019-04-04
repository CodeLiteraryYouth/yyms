package com.leanin.controller;

import com.alibaba.fastjson.JSON;
import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.vo.AdminUserVo;
import com.leanin.service.AdminUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 
 * @author zd
 * @date 2018/12/24
 */
//@RestController
//@RequestMapping("adminUser")
//@Slf4j
public class AdminUserController {

	/*@Autowired
	private AdminUserService adminUserService;
	
	@GetMapping("findAdminUserInfo")
	public DataOutResponse findAdminUserInfo(@RequestParam(required=false) String userCode) {
		log.info("用户的工号为:"+userCode);
		return adminUserService.findUserinfo(userCode);
	}
	
	@PostMapping("addAdminUser")
	public DataOutResponse addAdminUser(@RequestBody AdminUserVo adminUser) {
		log.info("要新增的管理员用户信息为:"+ JSON.toJSONString(adminUser));
		return adminUserService.insertAdminUser(adminUser);

	}
	
	@PostMapping("updateAdminUser")
	public DataOutResponse updateAdminUser(@RequestBody AdminUserVo adminUser) {
		log.info("要修改的管理员用户信息为:"+ JSON.toJSONString(adminUser));
		return adminUserService.updateAdminUser(adminUser);
	}
	
	@GetMapping("updateUserState")
	public DataOutResponse updateUserState(@RequestParam Long adminId) {
		log.info("注销的管理ID为:"+adminId);
		return adminUserService.updateUserState(adminId);
	}*/
}
