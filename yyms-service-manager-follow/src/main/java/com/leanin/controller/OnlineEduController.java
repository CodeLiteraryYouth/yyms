package com.leanin.controller;

import com.leanin.domain.response.DataOutResponse;
import com.leanin.service.OnlineEduService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("onlineEdu")
public class OnlineEduController {

    @Autowired
    OnlineEduService onlineEduService;

    /**
     *
     * @param currentPage 当前页  默认第一页
     * @param pageSize 每页展示条数  默认 10条
     * @param dept  发送人科室  模糊查询
     * @param formStatus 表单完成状态  1 未完成 2 已完成
     * @param sendStatus 发送状态 1未发送 2已发送 3 发送失败
     * @param patientName 患者名称  模糊查询
     * @param sendName  发送人姓名  模糊查询
     * @param patientId  his患者主键
     * @return
     */
    @GetMapping("findListByParam")
    public DataOutResponse findListByParam(Integer currentPage, Integer pageSize,
                                           String dept, Integer formStatus,
                                           Integer sendStatus, String patientName,
                                           String sendName,String patientId) {
        return onlineEduService.findListByParam(currentPage,pageSize,dept,formStatus,
                                                sendStatus,patientName,sendName,patientId);

    }

    /**
     * 修改宣教阅读状态
     * @param eduId  在院宣教主键
     * @param formStatus 表单完成状态  1 未完成 2 已完成
     * @return
     */
    @GetMapping("updateFormStatus")
    public DataOutResponse updateFormStatus(@RequestParam("eduId") String eduId,@RequestParam("formStatus") Integer formStatus){
        return onlineEduService.updateFormStatus(eduId,formStatus);
    }


}
