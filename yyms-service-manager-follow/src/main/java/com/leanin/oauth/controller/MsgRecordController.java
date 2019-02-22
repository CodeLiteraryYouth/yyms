package com.leanin.oauth.controller;

import com.leanin.domain.response.DataOutResponse;
import com.leanin.oauth.service.MsgRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/msgRecord")
public class MsgRecordController {

    @Autowired
    MsgRecordService msgRecordService;

    @GetMapping("/findMsgRecordList")
    public DataOutResponse findMsgRecordList(@RequestParam Integer currentPage,@RequestParam Integer pageSize,
                                             Integer planType,String msgThem,Integer sendType){
        return msgRecordService.findMsgRecordList(currentPage,pageSize,planType,msgThem,sendType);

    }


}
