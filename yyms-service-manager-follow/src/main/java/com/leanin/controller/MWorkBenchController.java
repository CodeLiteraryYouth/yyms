package com.leanin.controller;

import com.leanin.domain.request.MyFollowReq;
import com.leanin.domain.response.DataOutResponse;
import com.leanin.service.MWorkBenchService;
import com.leanin.utils.LyOauth2Util;
import com.leanin.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/myWorkBench")
public class MWorkBenchController extends BaseController {

    @Autowired
    MWorkBenchService mWorkBenchService;

    //我的随访和我的宣教
    //获取患者信息
    @PostMapping("/findPats")
    public DataOutResponse findPats(@RequestBody MyFollowReq myFollowReq){
        LyOauth2Util.UserJwt user = getUser(request);
        myFollowReq.setUserId(user.getId());
        return mWorkBenchService.findPats(myFollowReq);
    }

    //我的满意度
    @PostMapping("/findStyPats")
    public DataOutResponse findStyPats(@RequestBody MyFollowReq myFollowReq){
        LyOauth2Util.UserJwt user = getUser(request);
        myFollowReq.setUserId(user.getId());
        return mWorkBenchService.findStyPats(myFollowReq);
    }

    private LyOauth2Util.UserJwt getUser(HttpServletRequest httpServletRequest){
        LyOauth2Util lyOauth2Util = new LyOauth2Util();
        LyOauth2Util.UserJwt userJwt= lyOauth2Util.getUserJwtFromHeader(httpServletRequest);
        return userJwt;
    }




}
