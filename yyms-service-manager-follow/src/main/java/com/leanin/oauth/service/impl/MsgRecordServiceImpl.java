package com.leanin.oauth.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.response.ReturnFomart;
import com.leanin.domain.vo.MessageRecord;
import com.leanin.mapper.MsgRecordMapper;
import com.leanin.oauth.service.MsgRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class MsgRecordServiceImpl implements MsgRecordService {

    @Autowired
    MsgRecordMapper msgRecordMapper;

    @Override
    public DataOutResponse findMsgRecordList(Integer currentPage, Integer pageSize, Integer planType, String msgThem, Integer sendType) {

        if (currentPage == null || currentPage <= 0){
            currentPage = 1;
        }
        if (pageSize == null || pageSize <= 0){
            pageSize = 10;
        }
        Map dataMap =new HashMap();
        PageHelper.startPage(currentPage,pageSize);
        Page<MessageRecord> page = (Page<MessageRecord>) msgRecordMapper.findMsgRecordList(planType,msgThem,sendType);
        dataMap.put("totalCount",page.getTotal());
        dataMap.put("list",page.getResult());
        return ReturnFomart.retParam(200, dataMap);
    }
}
