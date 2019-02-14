package com.leanin.api.schedule;

import com.leanin.domain.dto.ScheduleJobDto;
import com.leanin.domain.response.DataOutResponse;
import io.swagger.annotations.Api;

@Api(value="定时任务接口",description = "管理任务")
public interface ScheduleApi {

    public DataOutResponse findSecheduleList(String scheduleName, Integer jobGroup);

    public DataOutResponse addSchedule(ScheduleJobDto scheduleJobDto);

    public DataOutResponse updateSchedule(ScheduleJobDto scheduleJobDto);
}
