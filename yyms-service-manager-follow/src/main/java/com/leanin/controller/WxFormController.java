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
    public DataOutResponse getWxForm(@RequestParam("planType") Integer planType, @RequestParam("formNum") String formNum,@RequestParam("planPatientId") Long planPatientId){
        return wxFormService.getWxForm(planType,formNum,planPatientId);

    }

    /**
     * 手机端提交随访表单接口
     * @param formRecordVo
     * @return
     */
    @PostMapping("/addFollowForm")
    public DataOutResponse addFollowForm(@RequestBody FormRecordVo formRecordVo){
        formRecordVo.setCreateTime(new Date());
        return wxFormService.addFollowForm(formRecordVo);
    }

    /**
     * 手机端提交满意度表单
     * @param styInfoRecordVo
     * @return
     */
    @PostMapping("/addStyForm")
    public DataOutResponse addStyForm(@RequestBody StyInfoRecordVo styInfoRecordVo){
        styInfoRecordVo.setCreateTime(new Date());
        return wxFormService.addStyForm(styInfoRecordVo);
    }
}
