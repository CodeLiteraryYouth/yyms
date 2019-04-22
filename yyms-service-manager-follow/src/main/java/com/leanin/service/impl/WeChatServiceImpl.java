package com.leanin.service.impl;

import com.alibaba.fastjson.JSON;
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
    public DataOutResponse login(String code) {
        if (code == null ){
            return ReturnFomart.retParam(300,"授权失败");
        }
        try {
            String url = "https://api.weixin.qq.com/sns/oauth2/access_token?" +
                    "appid=&" +appId+
                    "secret=&" +appSecret+
                    "code=&" +code+
                    "grant_type=authorization_code";
//            String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + appId + "&redirect_uri=" + redirectUrl + "&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect";
            HttpClient httpClient = new HttpClient(url);
            httpClient.setHttps(true);
            httpClient.get();

            String content = httpClient.getContent();
            Map map = JSON.parseObject(content, Map.class);
            return ReturnFomart.retParam(200,map);
        } catch (Exception e) {
            e.printStackTrace();
            return ReturnFomart.retParam(300,"获取用户openid失败");
        }

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DataOutResponse bindPatient(BindPat bindPat) {
        if (bindPat.getCode() == null) {
            return ReturnFomart.retParam(300, "授权码为空");
        }
        BindPat pat = patientWxMapper.findByIdCard(bindPat.getIdCard());
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
//            Map<String, String> param = new HashMap<>();
//            param.put("appid", appId);              //开发者设置中的appId
//            param.put("secret", appSecret);         //开发者设置中的appSecret
//            param.put("code", bindPat.getCode()); //小程序调用wx.login返回的code
//            param.put("grant_type", "authorization_code");    //默认参数
            HttpClient client = new HttpClient(url);//"https://api.weixin.qq.com/sns/jscode2session"
//            client.setParameter(param);
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
            e.printStackTrace();
        }
        bindPat.setOpenId(openid);

        patientWxMapper.addPatientWx(bindPat);

        return ReturnFomart.retParam(200, openid);
    }
}
