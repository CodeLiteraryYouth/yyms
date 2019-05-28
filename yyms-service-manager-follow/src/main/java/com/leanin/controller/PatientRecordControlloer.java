package com.leanin.controller;

import com.leanin.domain.response.DataOutResponse;
import com.leanin.service.PatientRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 患者档案中对的 记录管理  （宣教 随访 满意度 短信  电话）
 * @author CPJ.
 * @date 2019/5/27.
 * @time 13:08.
 */
@RestController
@RequestMapping("/patientRecord")
public class PatientRecordControlloer {

    @Autowired
    PatientRecordService patientRecordService;

    /**
     * 分页查询宣教记录
     * @param page 当前页  默认第一页
     * @param pageSize 每页展示条件  默认十条
     * @param patientId his患者主键
     * @return
     */
    @GetMapping("/findPageEduRecord")
    public DataOutResponse findPageEduRecord(Integer page,Integer pageSize,@RequestParam("patientId") Long patientId){
        return patientRecordService.findPageEduRecord(page,pageSize,patientId);
    }

    /**
     * 分页查询患者随访记录
     * @param page 当前页
     * @param pageSize 每页展示条数
     * @param patientId his患者主键
     * @return
     */
    @GetMapping("/findPageFollowRecord")
    public DataOutResponse findPageFollowRecord(Integer page,Integer pageSize,@RequestParam("patientId") Long patientId){
        return patientRecordService.findPageFollowRecord(page,pageSize,patientId);
    }

}
