package com.leanin.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.leanin.domain.request.MyFollowReq;
import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.response.ReturnFomart;
import com.leanin.domain.vo.PlanPatientVo;
import com.leanin.domain.vo.SatisfyPatientVo;
import com.leanin.mapper.PlanPatientMapper;
import com.leanin.mapper.SatisfyPatientMapper;
import com.leanin.service.MWorkBenchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MWorkBenchServiceImpl implements MWorkBenchService {

    @Autowired
    PlanPatientMapper planPatientMapper;

    @Autowired
    SatisfyPatientMapper satisfyPatientMapper;

    @Override
    public DataOutResponse findPats(MyFollowReq myFollowReq) {
        if (myFollowReq.getCurrentPage() == null || myFollowReq.getCurrentPage() <=0){
            myFollowReq.setCurrentPage(1);
        }
        if (myFollowReq.getPageSize() == null || myFollowReq.getPageSize() <= 0){
            myFollowReq.setPageSize(10);
        }
        PageHelper.startPage(myFollowReq.getCurrentPage(),myFollowReq.getPageSize());
        Page<PlanPatientVo> page = (Page<PlanPatientVo>) planPatientMapper.findPatsByParam(myFollowReq);

        Map dataMap =new HashMap();
        dataMap.put("totalCount",page.getTotal());
        dataMap.put("list",page.getResult());
        return ReturnFomart.retParam(200,dataMap);
    }

    @Override
    public DataOutResponse findStyPats(MyFollowReq myFollowReq) {
        if (myFollowReq.getCurrentPage() == null || myFollowReq.getCurrentPage() <=0){
            myFollowReq.setCurrentPage(1);
        }
        if (myFollowReq.getPageSize() == null || myFollowReq.getPageSize() <= 0){
            myFollowReq.setPageSize(10);
        }
        PageHelper.startPage(myFollowReq.getCurrentPage(),myFollowReq.getPageSize());
        Page<SatisfyPatientVo> page= (Page<SatisfyPatientVo>) satisfyPatientMapper.findPatsByParam(myFollowReq);
        Map dataMap = new HashMap();
        dataMap.put("totalCount",page.getTotal());
        dataMap.put("list",page.getResult());
        return ReturnFomart.retParam(200,dataMap);
    }
}
