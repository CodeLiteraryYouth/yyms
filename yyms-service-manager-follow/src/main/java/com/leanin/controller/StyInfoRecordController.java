package com.leanin.controller;


import com.leanin.domain.response.DataOutResponse;
import com.leanin.service.StyInfoRecordService;
import com.leanin.utils.LyOauth2Util;
import com.leanin.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/StyInfoRecord")
public class StyInfoRecordController extends BaseController {

    @Autowired
    StyInfoRecordService styInfoRecordService;

    @PostMapping("/addStyInfoRecord")
    public DataOutResponse addStyInfoRecord(){
        return null;
    }

    @GetMapping("/findStyInfoRecordByPid")
    public DataOutResponse findStyInfoRecordByPid(@RequestParam Long planPatientId){
        return null;
    }


    private LyOauth2Util.UserJwt getUser(HttpServletRequest httpServletRequest){
        LyOauth2Util lyOauth2Util = new LyOauth2Util();
        LyOauth2Util.UserJwt userJwt= lyOauth2Util.getUserJwtFromHeader(httpServletRequest);
        return userJwt;
    }

}
