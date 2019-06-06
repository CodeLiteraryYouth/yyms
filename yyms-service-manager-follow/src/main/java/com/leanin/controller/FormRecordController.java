package com.leanin.controller;

import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.vo.FormRecordVo;
import com.leanin.service.FormRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
//随访表单记录
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

    /**
     * 根据表单记录主键ID查询随访表单记录
     * @param formRecordId 随访表单记录主键
     * @return
     */
    @GetMapping("/findById")
    public DataOutResponse findById(@RequestParam("formRecordId") Long formRecordId){
        return formRecordService.findById(formRecordId);
    }

    /**
     * 根据计划号查询提交表单的信息
     * @param planNum 计划号
     * @param count 数量
     * @return
     */
    @GetMapping("/findByPlanNumAndCount")
    public DataOutResponse findByPlanNumAndCount(@RequestParam("planNum") String planNum,Integer count){
        return formRecordService.findByPlanNUmAndCount(planNum,count);
    }

    /*//根据患者id查询表单信息
    @GetMapping("/findFormByplanPatientId")
    public DataOutResponse findFormByplanPatientId(@RequestParam Long planPatientId){
        return formRecordService.findFormByplanPatientId(planPatientId);
    }*/

}
