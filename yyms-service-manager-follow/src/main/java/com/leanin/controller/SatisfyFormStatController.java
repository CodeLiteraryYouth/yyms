package com.leanin.controller;

import com.leanin.domain.request.FormQuest;
import com.leanin.domain.response.DataOutResponse;
import com.leanin.service.SatisfyFormStatService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author CPJ.
 * @date 2019/6/3.
 * @time 16:55.
 */
@RestController
@RequestMapping("/satisfyFormStat")
public class SatisfyFormStatController {

    @Autowired
    SatisfyFormStatService satisfyFormStatService;

    /**
     *
     * @param formQuest
     * @return
     */
    @PostMapping("/add")
    public DataOutResponse add(@RequestBody FormQuest formQuest){
        return satisfyFormStatService.add(formQuest);
    }

    /**
     * 根据满意度计划号和满意度表单号 查询满意度表单选项统计
     * @param planNum 满意度计划号
     * @param formId 满意度表单号
     * @return
     */
    @GetMapping("/findByPlanNumAndformId")
    public DataOutResponse findByPlanNumAndformId(@RequestParam("planNum") String planNum,@RequestParam("formId") String formId){
        return satisfyFormStatService.findByPlanNumAndformId(planNum,formId);
    }

}
