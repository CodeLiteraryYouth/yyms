package com.leanin.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.response.ReturnFomart;
import com.leanin.domain.vo.*;
import com.leanin.mapper.MsgInfoMapper;
import com.leanin.mapper.OnlineEduMapper;
import com.leanin.mapper.PatientInfoMapper;
import com.leanin.repository.OnlineEduRepository;
import com.leanin.service.OnlineEduService;
import com.leanin.utils.LyOauth2Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
@Slf4j
public class OnlineEduServiceImpl implements OnlineEduService {

    @Autowired
    OnlineEduMapper onlineEduMapper;

    @Autowired
    OnlineEduRepository onlineEduRepository;

    @Autowired
    PatientInfoMapper patientInfoMapper;

    @Autowired
    MsgInfoMapper msgInfoMapper;

    @Override
    public DataOutResponse findListByParam(Integer currentPage, Integer pageSize, String dept, Integer formStatus, Integer sendStatus, String patientName, String sendName,String patientId) {
        if (currentPage == null || currentPage < 1) {
            currentPage = 1;
        }
        if (pageSize == null || pageSize < 1) {
            pageSize = 10;
        }
        PageHelper.startPage(currentPage, pageSize);
        Page<OnlineEduVo> page = (Page<OnlineEduVo>) onlineEduMapper.findListByParam(dept, formStatus, sendStatus, patientName, sendName, patientId);
        Map dataMap = new HashMap();
        dataMap.put("totalCount", page.getTotal());
        dataMap.put("list", page.getResult());
        return ReturnFomart.retParam(200, dataMap);
    }

    @Override
    public DataOutResponse updateFormStatus(String eduId, Integer formStatus) {
        OnlineEdu onlineEdu = onlineEduRepository.findByEduId(eduId);
        if (onlineEdu == null){
            return ReturnFomart.retParam(2011,"数据不存在");
        }
        onlineEdu.setFormStatus(formStatus);
        OnlineEdu save = onlineEduRepository.save(onlineEdu);
        return ReturnFomart.retParam(200,save);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DataOutResponse sendOnlineEdu(List<PatientInfoEduVo> patientInfoEduVos, HttpServletRequest request) {
        log.info("在线宣教发送的参数--->"+ JSON.toJSONString(patientInfoEduVos));
        LyOauth2Util.UserJwt user = getUser(request);
        for (PatientInfoEduVo patientInfoEduVo : patientInfoEduVos) {
            //查询患者档案中是否有相同的患者
            PatientInfoVo patientInfoVo = patientInfoMapper.findByPatientIdAndSource(patientInfoEduVo.getPatientInfoId(), patientInfoEduVo.getPatientSource());
            if (patientInfoVo == null ){
                patientInfoVo = new PatientInfoVo();
                BeanUtils.copyProperties(patientInfoEduVo,patientInfoVo);
                patientInfoMapper.addPatientInfo(patientInfoVo);
            }
            //发送宣教
            MsgInfoVo msgInfo = msgInfoMapper.findMsgInfoById(patientInfoEduVo.getMsgId());
            if (msgInfo == null){
                return ReturnFomart.retParam(3501,patientInfoEduVo.getMsgId());
            }
            OnlineEdu edu = new OnlineEdu();
            edu.setPatientId(patientInfoEduVo.getPatientInfoId());//患者主键
            edu.setPatientName(patientInfoEduVo.getPatientInfoName());//患者姓名
            edu.setFormId(patientInfoEduVo.getFormId());        //表单主键
            edu.setSendUser(user.getId());          //发送人id
            edu.setBedNo(patientInfoEduVo.getBedNo());  //床位号
            edu.setInhosNo(patientInfoEduVo.getHosNo());//住院号
            edu.setInhosDate(patientInfoEduVo.getInhosDate());//住院日期
            edu.setMsgId(patientInfoEduVo.getMsgId());     //短信主键
            edu.setPhoneNum(patientInfoEduVo.getPatientInfoPhone());//患者手机号
            edu.setPatientDeptId(patientInfoEduVo.getPatientDeptId());//患者科室id
            edu.setPatientDeptName(patientInfoEduVo.getPatientDeptName());//患者科室名称
            edu.setPatientDiagnosisIcd(patientInfoEduVo.getPatientDiagnosisIcd());//患者诊断icd
            edu.setPatientDiagnosisName(patientInfoEduVo.getPatientDiagnosisName());//患者诊断名称
            OnlineEdu onlineEdu = onlineEduRepository.save(edu);
            String param = "http://sf-system.leanin.com.cn/education?planPatientId=" + onlineEdu.getEduId() + "&planType=4&formNum=" + onlineEdu.getFormId();
//			Map map = CSMSUtils.sendMessage(msgInfo.getMsgText() + param,onlineEdu.getPhoneNum() );
//			String msgStatus = (String) map.get("msg");
            String msgStatus = "true";
            edu.setSendTime(new Date());//发送时间
            if (msgStatus.equals("true")){//发送成功
                onlineEdu.setSendStatus(2);//已发送短信
                onlineEdu.setFormStatus(2);//短信发送宣教即已读
            }else{//发送失败
                onlineEdu.setSendStatus(3);//发送失败
                onlineEdu.setFormStatus(1);//短信发送失败未读
                OnlineEdu save = onlineEduRepository.save(onlineEdu);
                return ReturnFomart.retParam(3306,save);
            }
            OnlineEdu save = onlineEduRepository.save(onlineEdu);
        }
        return ReturnFomart.retParam(200,"发送成功");
    }


    private LyOauth2Util.UserJwt getUser(HttpServletRequest httpServletRequest){
        LyOauth2Util lyOauth2Util = new LyOauth2Util();
        LyOauth2Util.UserJwt userJwt= lyOauth2Util.getUserJwtFromHeader(httpServletRequest);
        return userJwt;
    }
}
