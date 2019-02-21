package com.leanin.controller;

import com.leanin.domain.response.DataOutResponse;
import com.leanin.service.MessagePatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/msgPatient")
public class MessagePatientController {

    @Autowired
    MessagePatientService messagePatientService;

    @GetMapping("/findList")
    public DataOutResponse findList(@RequestParam Integer currentPage, @RequestParam Integer pageSize,
                                    String patientName,Integer sendType){
        return messagePatientService.findList(currentPage,pageSize,patientName,sendType);
    }


}
