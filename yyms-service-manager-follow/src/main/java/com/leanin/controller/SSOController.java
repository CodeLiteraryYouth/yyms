package com.leanin.controller;

import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.response.ReturnFomart;
import com.leanin.utils.HttpUtil;
import com.leanin.utils.JsonUtil;
import com.leanin.utils.SSOClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * 登录和注销接口
 * @return
 */
@RestController
@RequestMapping("/sso")
@Slf4j
public class SSOController {

	@Autowired
    StringRedisTemplate stringRedisTemplate;
	
	/**
	 * 登陆
	 * @param userName
	 * @param password
	 * @param session
	 * @return
	 */
	@GetMapping(value="login")
	public DataOutResponse login(@RequestParam String userName, @RequestParam String password, @RequestParam String areaCode, HttpSession session) {
		log.info("登录的用户名为:"+userName+"密码为:"+password);
		Map<String,String> map=new HashMap<>();
		map.put("userName", userName);
		map.put("password", password);
		map.put("redirectUrl", SSOClientUtil.CLIENT_HOST_URL+"/sso/login");
		map.put("areaCode", areaCode);
		log.info("院区编码为:"+areaCode);
		//存入Reids缓存当中用于存取
		stringRedisTemplate.opsForValue().set("area_code", areaCode);
		String result=null;
		try {
			result= HttpUtil.sendHttpRequest(SSOClientUtil.SERVER_URL_PREFIX+"/login", map);
			log.info("返回的登录信息为:"+result);
		} catch (Exception e) {
			e.printStackTrace();
			return ReturnFomart.retParam(1000, null);
		}
		DataOutResponse response= JsonUtil.json2Obj(result, DataOutResponse.class);
		return response;
	}
	
	/**
	 * 登录注销退出
	 * @return
	 */
	@GetMapping(value="logout")
	public DataOutResponse logOut() {
		Map<String,String> map=new HashMap<>();
		String result=null;
		try {
			result=HttpUtil.sendHttpRequest(SSOClientUtil.SERVER_URL_PREFIX+"/logout", map);
			log.info("返回的注销信息为:"+result);
		} catch (Exception e) {
			e.printStackTrace();
			return ReturnFomart.retParam(1000, null);		
		}
		DataOutResponse response=JsonUtil.json2Obj(result, DataOutResponse.class);
		return response;
	}
}
