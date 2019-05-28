package com.leanin.manager.patient.controller;

import com.leanin.domain.response.DataOutResponse;
import com.leanin.manager.patient.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 通用类接口
 * @author CPJ.
 * @date 2019/5/28.
 * @time 20:07.
 */
@RestController
@RequestMapping("/common")
public class CommonController  {

    @Autowired
    CommonService commonService;

    /**
     * 查询患者科室
     * @return
     */
    @GetMapping("/getDept")
    public DataOutResponse getDept(){
        return commonService.getDept();
    }
}
