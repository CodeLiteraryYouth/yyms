package com.leanin.controller;

import com.leanin.domain.dao.PatientWxDao;
import com.leanin.domain.request.BindPat;
import com.leanin.domain.response.DataOutResponse;
import com.leanin.service.WeChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/wx")
public class WeChatController {

    @Autowired
    WeChatService weChatService;

    /**
     * 微信公众号绑定患者信息
     * @param bindPat
     * @return
     */
    @PostMapping("/bindPatient")
    public DataOutResponse bindPatient(@RequestBody BindPat bindPat){
        return weChatService.bindPatient(bindPat);
    }

    /**
     * 微信公众号修改患者信息
     * @param patientWxDao
     * @return
     */
    @PutMapping("/updatePatientWx")
    public DataOutResponse updatePatientWx(@RequestBody PatientWxDao patientWxDao){
        return weChatService.updatePatientWx(patientWxDao);
    }



}
