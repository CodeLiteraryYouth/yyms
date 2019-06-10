package com.leanin.service.impl;

import com.alibaba.fastjson.JSON;
import com.leanin.client.ManagerPatientClient;
import com.leanin.domain.dao.PatientWxDao;
import com.leanin.domain.request.BindPat;
import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.response.ReturnFomart;
import com.leanin.mapper.PatientWxMapper;
import com.leanin.repository.PatientWxRepository;
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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class WeChatServiceImpl implements WeChatService {


    @Value("${wx.gzappId}")
    private String appId;

    @Value("${wx.gzappSecret}")
    private String appSecret;

    private String redirectUrl;

    @Autowired
    PatientWxMapper patientWxMapper;

    @Autowired
    PatientWxRepository patientWxRepository;

    @Autowired
    ManagerPatientClient managerPatientClient;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public DataOutResponse bindPatient(BindPat bindPat) {
        log.info("微信绑定患者信息:{}",JSON.toJSONString(bindPat));
        if (bindPat.getCode() == null) {
            return ReturnFomart.retParam(300, "授权码为空");
        }
        DataOutResponse dataOutResponse = managerPatientClient.findByIdCard(bindPat.getIdCard(),bindPat.getPatientName());
        int status = dataOutResponse.getStatus();
        String data = (String) dataOutResponse.getData();
        //判断his中是否存在患者信息
        if (status != 200 || "信息不存在".equals(data)){//his中不存在患者信息
            log.info("患者信息不存在");
            return ReturnFomart.retParam(5004,"信息不存在");
        }
//        Map dataMap = JSON.parseObject(data, Map.class);
        //获取openid

        String openId = getOpenId(bindPat.getCode());
        if (openId ==null){
            log.info("获取openid失败");
            return ReturnFomart.retParam(3401,"绑定患者失败");
        }

//        bindPat.setOpenId(openid);
        PatientWxDao patientWxDao = patientWxRepository.findByIdCard(bindPat.getIdCard());
        if (patientWxDao == null){//根据微信唯一标识查询
            patientWxDao =new PatientWxDao();
        }
        patientWxDao.setIdCard(bindPat.getIdCard());
        patientWxDao.setOpenId(openId);
        patientWxDao.setPatientName(bindPat.getPatientName());
        patientWxDao.setPhoneNum(bindPat.getPhoneNum());

        PatientWxDao save = patientWxRepository.save(patientWxDao);
        log.info("绑定患者信息为:{}",JSON.toJSONString(save));
//        patientWxMapper.addPatientWx(bindPat);

        return ReturnFomart.retParam(200, save);
    }

    @Override
    public DataOutResponse updatePatientWx(PatientWxDao patientWxDao) {
        log.info("修改微信绑定患者信息:{}",JSON.toJSONString(patientWxDao));
        DataOutResponse dataOutResponse = managerPatientClient.findByIdCard(patientWxDao.getIdCard(),patientWxDao.getPatientName());
        int status = dataOutResponse.getStatus();
        String data = (String) dataOutResponse.getData();
        //判断his中是否存在患者信息
        if (status != 200 || "信息不存在".equals(data)){//his中不存在患者信息
            log.info("his数据中不存在患者信息");
            return ReturnFomart.retParam(5004,"信息不存在");
        }
        PatientWxDao patient = patientWxRepository.findByIdCard(patientWxDao.getIdCard());
        if (patient == null){//根据微信唯一标识查询
            log.info("新添加微信绑定患者信息");
            patient =new PatientWxDao();
        }else {
            log.info("身份证号已被其他用户绑定，修改openid");
        }
        patient.setIdCard(patientWxDao.getIdCard());
        patient.setOpenId(patientWxDao.getOpenId());
        patientWxDao.setPatientName(patientWxDao.getPatientName());
        patient.setPhoneNum(patientWxDao.getPhoneNum());

        PatientWxDao save = patientWxRepository.save(patient);
        log.info("修改后的微信绑定患者信息:{}",JSON.toJSONString(save));
        return ReturnFomart.retParam(200,save);
    }


    private String getOpenId(String code){
        String openid = null;
        try {
            String url = "https://api.weixin.qq.com/sns/oauth2/access_token?" +
                    "appid=" +appId+"&"+
                    "secret=" +appSecret+"&"+
                    "code=" +code+"&"+
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
                return null;
//                return ReturnFomart.retParam(3401,"绑定用户信息失败");
            }

        } catch (Exception e) {
            log.info("微信推送模板消息异常:{}",e.getMessage());
//            e.printStackTrace();
        }
        return openid;
    }

    @Override
    public DataOutResponse findHosList(String patientId) {
        log.info("patient的ID为:"+patientId);
        List<String> listId= Arrays.asList(patientId.split(","));
        List<PatientWxDao> patientList=patientWxMapper.findHosList(listId);
        return ReturnFomart.retParam(200,patientList);
    }
}
