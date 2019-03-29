package com.leanin.controller;

import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.vo.FormRecordVo;
import com.leanin.domain.vo.StyInfoRecordVo;
import com.leanin.service.WxFormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/wxForm")
public class WxFormController {

    @Autowired
    WxFormService wxFormService;

    @GetMapping("/getWxForm")
    public DataOutResponse getWxForm(@RequestParam Integer planType, @RequestParam String formNum,@RequestParam Long planPatientId){
        return wxFormService.getWxForm(planType,formNum,planPatientId);

    }

    @PostMapping("/addFollowForm")
    public DataOutResponse addFollowForm(@RequestBody FormRecordVo formRecordVo){
        formRecordVo.setCreateTime(new Date());
        return wxFormService.addFollowForm(formRecordVo);
    }

    @PostMapping("/addStyForm")
    public DataOutResponse addStyForm(@RequestBody StyInfoRecordVo styInfoRecordVo){
        return wxFormService.addStyForm(styInfoRecordVo);
    }
}
