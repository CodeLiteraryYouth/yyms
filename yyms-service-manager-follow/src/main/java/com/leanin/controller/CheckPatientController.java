package com.leanin.controller;

import com.leanin.domain.response.DataOutResponse;
import com.leanin.service.CheckPatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/checkPatient")
public class CheckPatientController {

    @Autowired
    CheckPatientService checkPatientService;

    @GetMapping("/findList")
    public DataOutResponse findList(@RequestParam Integer currentPage,@RequestParam Integer pageSize,
                                    @RequestParam String checkNUm, String patientName, Integer finishStatus){
        return checkPatientService.findList(currentPage,pageSize,patientName,finishStatus,checkNUm);
    }

    @GetMapping("/findById")
    public DataOutResponse findById(@RequestParam Long checkPatientId){
        return checkPatientService.findById(checkPatientId);
    }

}
