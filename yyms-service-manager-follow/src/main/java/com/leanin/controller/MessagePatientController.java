package com.leanin.controller;

import com.leanin.domain.response.DataOutResponse;
import com.leanin.service.MessagePatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/msgPatient")
public class MessagePatientController {

    @Autowired
    MessagePatientService messagePatientService;

    @PreAuthorize("hasAnyAuthority('root','findMsgPat')")
    @GetMapping("/findList")
    public DataOutResponse findList(@RequestParam Integer currentPage, @RequestParam Integer pageSize,
                                    String patientName,Integer sendType){
        return messagePatientService.findList(currentPage,pageSize,patientName,sendType);
    }

    /**
     *
     * @param planPatientId 计划患者主键
     * @param status 状态
     * @return
     */
    @GetMapping("updateStatus")
    public DataOutResponse updateStatus(@RequestParam("planPatientId")Long planPatientId,@RequestParam("status")Integer status){
        return messagePatientService.updateStatus(planPatientId,status);
    }


}
