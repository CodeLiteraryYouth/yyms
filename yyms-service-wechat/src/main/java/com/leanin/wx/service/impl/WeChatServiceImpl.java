package com.leanin.wx.service.impl;

import com.alibaba.fastjson.JSON;
import com.leanin.domain.request.BindPat;
import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.response.ReturnFomart;
import com.leanin.wx.service.WeChatService;
import com.leanin.utils.HttpClient;
import com.leanin.wx.mapper.PatientWxMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
public class WeChatServiceImpl implements WeChatService {

    private static final Logger LOGGER = LoggerFactory.getLogger(WeChatServiceImpl.class);

    @Value("wx.appId")
    private String appId;

    @Value("wx.appSecret")
    private String appSecret;

    @Autowired
    PatientWxMapper patientWxMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DataOutResponse bindPatient(BindPat bindPat) {
        if (bindPat.getCode() == null){
            return ReturnFomart.retParam(300,"授权码为空");
        }
        BindPat pat = patientWxMapper.findByIdCard(bindPat.getIdCard());
        if (pat != null){
            return ReturnFomart.retParam(400,"用户已经绑定信息，无需重复绑定");
        }
        //获取openid
        String openid = null;
        try {
            Map<String,String> param = new HashMap<>();
            param.put("appid", appId);			//开发者设置中的appId
            param.put("secret", appSecret);		//开发者设置中的appSecret
            param.put("js_code", bindPat.getCode());					//小程序调用wx.login返回的code
            param.put("grant_type","authorization_code");	//默认参数
            HttpClient client =new HttpClient("https://api.weixin.qq.com/sns/jscode2session");
            client.setParameter(param);
            client.setHttps(false);
            client.get();

            String content = client.getContent();
            Map map = JSON.parseObject(content, Map.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        bindPat.setOpenId(openid);

        patientWxMapper.addPatientWx(bindPat);

        return ReturnFomart.retParam(200,"用户信息绑定成功");
    }
}
