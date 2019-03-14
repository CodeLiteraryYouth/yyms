package com.leanin.service.impl;

import com.alibaba.fastjson.JSON;
import com.leanin.domain.request.BindPat;
import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.response.ReturnFomart;
import com.leanin.domain.vo.MessagePatientVo;
import com.leanin.domain.vo.PlanPatientVo;
import com.leanin.domain.vo.SatisfyPatientVo;
import com.leanin.mapper.MessagePatientMapper;
import com.leanin.mapper.PlanPatientMapper;
import com.leanin.mapper.SatisfyPatientMapper;
import com.leanin.service.WeChatService;
import com.leanin.utils.HttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WeChatServiceImpl implements WeChatService {

    private static final Logger LOGGER = LoggerFactory.getLogger(WeChatServiceImpl.class);

    @Value("wx.appId")
    private String appId;

    @Value("wx.appSecret")
    private String appSecret;

    @Autowired
    PlanPatientMapper planPatientMapper;

    @Autowired
    SatisfyPatientMapper satisfyPatientMapper;

    @Autowired
    MessagePatientMapper messagePatientMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DataOutResponse bindPatient(BindPat bindPat) {
        if (bindPat.getCode() == null){
            return ReturnFomart.retParam(300,"授权码为空");
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
        //随访 宣教
        List<PlanPatientVo> list=planPatientMapper.bindPatient(bindPat.getIdCard(),bindPat.getPatientName(),bindPat.getPhoneNum());
        for (PlanPatientVo planPatientVo : list) {
            //设置openid
            planPatientVo.setOpendId(openid);
            planPatientMapper.updatePlanPatient(planPatientVo);
        }

        //满意度
        List<SatisfyPatientVo> SatisfyPatientVos=satisfyPatientMapper.bindPatient(bindPat.getIdCard(),bindPat.getPatientName(),bindPat.getPhoneNum());
        for (SatisfyPatientVo satisfyPatientVo : SatisfyPatientVos) {
            satisfyPatientVo.setOpenId(openid);
            satisfyPatientMapper.updateByPrimaryKeySelective(satisfyPatientVo);
        }

        /*//短信计划
        List<MessagePatientVo> MessagePatientVos=messagePatientMapper.bindPatient(bindPat.getIdCard(),bindPat.getPatientName(),bindPat.getPhoneNum());
        for (MessagePatientVo messagePatientVo : MessagePatientVos) {
            messagePatientVo.setOpenId(openid);
            messagePatientMapper.updateByPrimaryKeySelective(messagePatientVo);
        }*/
        return ReturnFomart.retParam(200,"用户信息绑定成功");
    }
}