package com.leanin.controller;

import com.leanin.domain.request.MyFollowReq;
import com.leanin.domain.response.DataOutResponse;
import com.leanin.service.MWorkBenchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/myWorkBench")
public class MWorkBenchController {

    @Autowired
    MWorkBenchService mWorkBenchService;

    //我的随访和我的宣教
    //获取患者信息
    @PostMapping("/findPats")
    public DataOutResponse findPats(@RequestBody MyFollowReq myFollowReq){
        return mWorkBenchService.findPats(myFollowReq);
    }

    //我的满意度
    @PostMapping("/findStyPats")
    public DataOutResponse findStyPats(@RequestBody MyFollowReq myFollowReq){
        return mWorkBenchService.findStyPats(myFollowReq);
    }




}
