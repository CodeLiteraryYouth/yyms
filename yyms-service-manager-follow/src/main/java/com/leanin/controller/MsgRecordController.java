package com.leanin.controller;

import com.leanin.domain.response.DataOutResponse;
import com.leanin.service.MsgRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/msgRecord")
public class MsgRecordController {

    @Autowired
    MsgRecordService msgRecordService;

//    @PreAuthorize("hasAnyAuthority('root','findMsgRecord')")
    @GetMapping("/findMsgRecordList")
    public DataOutResponse findMsgRecordList(@RequestParam("currentPage") Integer currentPage,@RequestParam("pageSize") Integer pageSize,
                                             Integer planType,String msgThem,Integer sendType,String patientId){
        return msgRecordService.findMsgRecordList(currentPage,pageSize,planType,msgThem,sendType,patientId);

    }




}
