package com.leanin.controller;


import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.vo.StyInfoRecordVo;
import com.leanin.service.StyInfoRecordService;
import com.leanin.utils.LyOauth2Util;
import com.leanin.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/styInfoRecord")
public class StyInfoRecordController extends BaseController {

    @Autowired
    StyInfoRecordService styInfoRecordService;

    //增加满意度表单记录
    @PostMapping("/addStyInfoRecord")
    public DataOutResponse addStyInfoRecord(@RequestBody StyInfoRecordVo styInfoRecordVo){
        return styInfoRecordService.addStyInfoRecord(styInfoRecordVo);
    }

    /**
     * 满意度表单记录
     * @param planPatientId  满意度计划患者主键
     * @return
     */
    @GetMapping("/findStyInfoRecordByPid")
    public DataOutResponse findStyInfoRecordByPid(@RequestParam("planPatientId") Long planPatientId){
        return styInfoRecordService.findStyInfoRecordByPid(planPatientId);
    }

    /**
     * 根据满意度历史表单主键查询满意度历史表单
     * @param satisfyRecordId  满意度历史表单主键
     * @return
     */
    @GetMapping("/findById")
    public DataOutResponse findById(@RequestParam("satisfyRecordId")String satisfyRecordId){
        return styInfoRecordService.findById(satisfyRecordId);
    }


    private LyOauth2Util.UserJwt getUser(HttpServletRequest httpServletRequest){
        LyOauth2Util lyOauth2Util = new LyOauth2Util();
        LyOauth2Util.UserJwt userJwt= lyOauth2Util.getUserJwtFromHeader(httpServletRequest);
        return userJwt;
    }

}
