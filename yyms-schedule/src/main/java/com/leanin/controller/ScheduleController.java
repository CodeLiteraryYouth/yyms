package com.leanin.controller;

import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.response.ReturnFomart;
import com.leanin.domain.vo.ScheduleJobVo;
import com.leanin.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 定时任务的维护接口
 * @author Administrator
 */
@RestController
@RequestMapping(value = "schedule")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @RequestMapping(value="findSecheduleList")
    public DataOutResponse findSecheduleList(@RequestParam(required = false) String scheduleName,@RequestParam(required = false) Integer jobGroup) {
        return ReturnFomart.retParam(200,scheduleService.findScheduleList(scheduleName,jobGroup));
    }

    @RequestMapping(value = "addSchedule")
    public DataOutResponse addSchedule(@RequestBody ScheduleJobVo scheduleJobVo) {
        return ReturnFomart.retParam(200,scheduleService.addScheduleJob(scheduleJobVo));
    }

    @RequestMapping(value = "updateSchedule")
    public DataOutResponse updateSchedule(@RequestBody ScheduleJobVo scheduleJobVo) {
        return ReturnFomart.retParam(200,scheduleService.updateScheduleJob(scheduleJobVo));
    }
}
