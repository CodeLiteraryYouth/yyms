package com.leanin.controller;

import com.leanin.domain.response.DataOutResponse;
import com.leanin.service.OnlineEduService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("onlineEdu")
public class OnlineEduController {

    @Autowired
    OnlineEduService onlineEduService;

    @GetMapping("findListByParam")
    public DataOutResponse findListByParam(Integer currentPage, Integer pageSize,
                                           String dept, Integer formStatus,
                                           Integer sendStatus, String patientName,
                                           String sendName,String patientId) {
        return onlineEduService.findListByParam(currentPage,pageSize,dept,formStatus,
                                                sendStatus,patientName,sendName,patientId);

    }

    //修改阅读状态
    @GetMapping("updateFormStatus")
    public DataOutResponse updateFormStatus(@RequestParam("eduId") String eduId,@RequestParam("formStatus") Integer formStatus){
        return onlineEduService.updateFormStatus(eduId,formStatus);
    }


}
