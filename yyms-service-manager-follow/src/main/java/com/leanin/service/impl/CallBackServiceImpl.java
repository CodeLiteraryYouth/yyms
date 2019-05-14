package com.leanin.service.impl;

import java.util.Date;
import java.util.List;
import com.leanin.domain.CallBack;
import com.leanin.domain.CallBackDeal;
import com.leanin.domain.dto.CallBackDto;
import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.response.ReturnFomart;
import com.leanin.mapper.CallBackMapper;
import com.leanin.utils.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.leanin.service.CallBackService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CallBackServiceImpl implements CallBackService {

    @Autowired
    private CallBackMapper callBackMapper;


    @Override
    public DataOutResponse findBackList(int page, int pageSize, String beginDate, String endDate,
                                        Integer dealStatus, String accuseWard, String backNum, int status) {
        log.info("起始时间为:"+beginDate+"结束时间为:"+endDate+"处理状态:"+dealStatus+"科室:"+accuseWard+"单号:"+backNum);
        PageHelper.startPage(page, pageSize);
        List<CallBack> backList=callBackMapper.findBackList(beginDate, endDate, dealStatus, accuseWard, backNum,status);
        PageInfo pageInfo=new PageInfo<>(backList);
        return ReturnFomart.retParam(200, pageInfo);
    }

    @Override
    public DataOutResponse findBackById(String backNum,int status) {
        log.info("单号为:"+backNum);
        CallBack callBack=callBackMapper.findBackById(backNum,status);
        if (callBack == null){
            return ReturnFomart.retParam(1,"信息不存在");
        }
        log.info("查询的投诉表扬信息为:"+JSON.toJSONString(callBack));
        return ReturnFomart.retParam(200, callBack);
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public DataOutResponse addCallBack(CallBack callBack) {
        log.info("添加的投诉表扬信息为:"+JSON.toJSONString(callBack));
//        String uuid = UUIDUtils.getUUID();
//        callBack.setCallBackNum(uuid);
        CallBack callBackDto=callBackMapper.findBackById(callBack.getCallBackNum(),callBack.getStatus());
        if(callBackDto != null) {
            return ReturnFomart.retParam(4000, callBackDto);
        }
        callBack.setPushDate(new Date());
        callBackMapper.addCallBack(callBack);
        //同时保存一份单号进入处理表单意见
        CallBackDeal callBackDeal=new CallBackDeal();
        callBackDeal.setCallBackNum(callBack.getCallBackNum());
        callBackDeal.setDealStatus(0);
        callBackMapper.addCallBackDeal(callBackDeal);
        return ReturnFomart.retParam(200, callBack);
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public DataOutResponse updateCallBackDeal(CallBackDeal callBackDeal) {
        log.info("修改的处理人信息为:"+JSON.toJSONString(callBackDeal));
        CallBackDeal record = callBackMapper.findBackDetail(callBackDeal.getCallBackNum());
        if (record == null){
            return ReturnFomart.retParam(300, "数据不存在");
        }
        callBackDeal.setDealNum(record.getDealNum());
        callBackMapper.updateCallBackDeal(callBackDeal);
        return ReturnFomart.retParam(200, callBackDeal);
    }

    @Override
    public DataOutResponse findBackDealByBackNum(String backNum) {
        CallBackDeal record = callBackMapper.findBackDetail(backNum);
        return ReturnFomart.retParam(200, record);
    }

}
