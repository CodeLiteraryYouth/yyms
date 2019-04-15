package com.leanin.controller;

import com.leanin.domain.response.DataOutResponse;
import com.leanin.service.FollowRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/followRecord")
public class FollowRecordController {

    @Autowired
    FollowRecordService followRecordService;

    //条件查询 失访和已完成随访的记录
    @PreAuthorize("hasAnyAuthority('root','findFollwRecord')")
    @GetMapping("/findList")
    public DataOutResponse findList(@RequestParam Integer currentPage,@RequestParam Integer pageSize,
                                    String patientName,Integer planPatsStatus){

        return followRecordService.findList(currentPage,pageSize,patientName,planPatsStatus);
    }



}
