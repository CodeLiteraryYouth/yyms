package com.leanin.wx.controller;

import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.vo.FormRecordVo;
import com.leanin.domain.vo.StyInfoRecordVo;
import com.leanin.wx.service.WxFormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wxForm")
public class WxFormController {

    @Autowired
    WxFormService wxFormService;

    @GetMapping("/getWxForm")
    public DataOutResponse getWxForm(@RequestParam Integer planType, @RequestParam String formNum){
        return wxFormService.getWxForm(planType,formNum);

    }

    @PostMapping("/addFollowForm")
    public DataOutResponse addFollowForm(@RequestBody FormRecordVo formRecordVo){
        return wxFormService.addFollowForm(formRecordVo);
    }

    @PostMapping("/addStyForm")
    public DataOutResponse addStyForm(StyInfoRecordVo styInfoRecordVo){
        return wxFormService.addStyForm(styInfoRecordVo);
    }
}
