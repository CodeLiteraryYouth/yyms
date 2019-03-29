package com.leanin.wx.controller;

import com.leanin.domain.request.BindPat;
import com.leanin.domain.response.DataOutResponse;
import com.leanin.wx.service.WeChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/wx")
public class WeChatController {

    @Autowired
    WeChatService weChatService;

    @PostMapping("/bindPatient")
    public DataOutResponse bindPatient(@RequestBody BindPat bindPat){
        return weChatService.bindPatient(bindPat);
    }



}
