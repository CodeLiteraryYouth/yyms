package com.leanin.controller;

import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.vo.FormRecordVo;
import com.leanin.service.FormRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/formRecord")
public class FormRecordController {

    @Autowired
    FormRecordService formRecordService;


    @PostMapping("/addFormRecord")
    public DataOutResponse addFormRecord(@RequestBody FormRecordVo formRecordVo){
        return formRecordService.addFormRecord(formRecordVo);
    }

    @GetMapping("/findRecordById")
    public DataOutResponse findFormRecordByPid(@RequestParam Long patientPlanId){
        return formRecordService.findFormRecordByPid(patientPlanId);
    }

}
