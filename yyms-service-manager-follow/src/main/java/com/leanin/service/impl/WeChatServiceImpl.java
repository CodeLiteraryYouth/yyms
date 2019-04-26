package com.leanin.service.impl;

import com.alibaba.fastjson.JSON;
import com.leanin.domain.dao.PatientWxDao;
import com.leanin.domain.request.BindPat;
import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.response.ReturnFomart;
import com.leanin.mapper.PatientWxMapper;
import com.leanin.service.WeChatService;
import com.leanin.utils.HttpClient;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class WeChatServiceImpl implements WeChatService {

    private static final Logger LOGGER = LoggerFactory.getLogger(WeChatServiceImpl.class);

    @Value("${wx.gzappId}")
    private String appId;

    @Value("${wx.gzappSecret}")
    private String appSecret;

    private String redirectUrl;

    @Autowired
    PatientWxMapper patientWxMapper;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public DataOutResponse bindPatient(BindPat bindPat) {
        if (bindPat.getCode() == null) {
            return ReturnFomart.retParam(300, "授权码为空");
        }
        PatientWxDao pat = patientWxMapper.findByIdCard(bindPat.getIdCard());
        if (pat != null) {
            return ReturnFomart.retParam(200, pat.getOpenId());
        }
        //获取openid
        String openid = null;
        try {
            String url = "https://api.weixin.qq.com/sns/oauth2/access_token?" +
                    "appid=" +appId+"&"+
                    "secret=" +appSecret+"&"+
                    "code=" +bindPat.getCode()+"&"+
                    "grant_type=authorization_code";
            HttpClient client = new HttpClient(url);//"https://api.weixin.qq.com/sns/jscode2session"
            client.setHttps(true);
            client.get();

            String content = client.getContent();
            log.info("获取openiId是返回的值",content);
            Map map = JSON.parseObject(content, Map.class);
            Object object = map.get("openid");

            if (object != null){
                openid =(String) object;
            }else{
                return ReturnFomart.retParam(3401,"绑定用户信息失败");
            }

        } catch (Exception e) {
            log.info("微信推送模板消息异常:{}",e.getMessage());
//            e.printStackTrace();
        }
        bindPat.setOpenId(openid);

        patientWxMapper.addPatientWx(bindPat);

        return ReturnFomart.retParam(200, openid);
    }
}
