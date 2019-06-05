package com.leanin.controller;

import com.leanin.domain.request.FormQuest;
import com.leanin.domain.response.DataOutResponse;
import com.leanin.service.FollowFormStatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author CPJ.
 * @date 2019/6/3.
 * @time 13:58.
 */
@RestController
@RequestMapping("/followFormStat")
public class FollowFormStatController {

    @Autowired
    FollowFormStatService followFormStatService;

    /**
     * 添加随访表单统计
     * @param formQuest
     * @return
     */
    @PostMapping("/add")
    public DataOutResponse add(@RequestBody FormQuest formQuest){
        return followFormStatService.add(formQuest);
    }

    /**
     * 根据计划号和表单号查询 随访表单选项统计
     * @param planNum
     * @param formId
     * @return
     */
    @GetMapping("/findByFormIdAndPlanId")
    public DataOutResponse findByFormIdAndPlanId(@RequestParam("planNum")String planNum,@RequestParam("formId") String formId){
        return followFormStatService.findByFormIdAndPlanId(planNum,formId);
    }
}
