package com.leanin.controller;

import com.leanin.api.schedule.ScheduleApi;
import com.leanin.domain.dto.ScheduleJobDto;
import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.response.ReturnFomart;
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
public class ScheduleController implements ScheduleApi{

    @Autowired
    private ScheduleService scheduleService;

    @RequestMapping(value="findSecheduleList")
    public DataOutResponse findSecheduleList(@RequestParam(required = false) String scheduleName,@RequestParam(required = false) Integer jobGroup) {
        return ReturnFomart.retParam(200,scheduleService.findScheduleList(scheduleName,jobGroup));
    }

    @RequestMapping(value = "addSchedule")
    public DataOutResponse addSchedule(@RequestBody ScheduleJobDto scheduleJobDto) {
        return ReturnFomart.retParam(200,scheduleService.addScheduleJob(scheduleJobDto));
    }

    @RequestMapping(value = "updateSchedule")
    public DataOutResponse updateSchedule(@RequestBody ScheduleJobDto scheduleJobDto) {
        return ReturnFomart.retParam(200,scheduleService.updateScheduleJob(scheduleJobDto));
    }
}
