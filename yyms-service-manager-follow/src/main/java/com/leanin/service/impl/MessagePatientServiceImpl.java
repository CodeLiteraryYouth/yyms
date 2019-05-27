package com.leanin.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.response.ReturnFomart;
import com.leanin.domain.vo.MessagePatientVo;
import com.leanin.mapper.MessagePatientMapper;
import com.leanin.service.MessagePatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MessagePatientServiceImpl implements MessagePatientService {

    @Autowired
    MessagePatientMapper messagePatientMapper;

    @Override
    public DataOutResponse findList(Integer currentPage, Integer pageSize, String patientName, Integer sendType,String msgTopicId) {
        if (currentPage <0 || currentPage == null ){
            currentPage=1;
        }
        if (pageSize <0 || pageSize == null ){
            pageSize=10;
        }
        Map dataMap =new HashMap();
        PageHelper.startPage(currentPage,pageSize);
        Page<MessagePatientVo> page = (Page<MessagePatientVo>) messagePatientMapper.findList(patientName,sendType,msgTopicId);
        dataMap.put("totalCount",page.getTotal());
        dataMap.put("list",page.getResult());
        return ReturnFomart.retParam(200, dataMap);
    }

    @Override
    public DataOutResponse updateStatus(Long planPatientId, Integer status) {
        MessagePatientVo messagePatientVo = messagePatientMapper.findById(planPatientId);
        messagePatientVo.setPatientStatus(status);
        messagePatientMapper.updateByPrimaryKeySelective(messagePatientVo);
        return ReturnFomart.retParam(200,messagePatientVo);
    }

    @Override
    public DataOutResponse findByMsgTopicId(Integer currentPage, Integer pageSize, String msgTopicId) {
        if (null == currentPage || currentPage < 0){
            currentPage = 1;
        }
        if (null == pageSize || pageSize < 0){
            pageSize = 10;
        }
        PageHelper.startPage(currentPage,pageSize);
        Page<MessagePatientVo> page = (Page<MessagePatientVo>) messagePatientMapper.findByMsgTopicId(msgTopicId);
        Map dataMap = new HashMap();
        dataMap.put("totalCount",page.getTotal());
        dataMap.put("list",page.getResult());
        return ReturnFomart.retParam(200, dataMap);
    }
}
