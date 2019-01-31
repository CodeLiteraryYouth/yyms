package com.leanin.service.impl;

import com.alibaba.fastjson.JSON;
import com.leanin.domain.dto.ScheduleJobDto;
import com.leanin.domain.dto.TaskCronDto;
import com.leanin.domain.vo.ScheduleJobVo;
import com.leanin.mapper.ScheduleMapper;
import com.leanin.service.ScheduleService;
import com.leanin.utils.CronUtil;
import com.leanin.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 定时任务列表实现类
 * @author Administrator
 */
@Service
@Slf4j
public class ScheduleServiceImpl implements ScheduleService {

    @Autowired
    private ScheduleMapper scheduleMapper;

    @Override
    public List<ScheduleJobVo> findDelJobList() {
        return scheduleMapper.findDelJobList();
    }

    @Override
    public List<ScheduleJobVo> findLegalJobList() {
        return scheduleMapper.findLegalJobList();
    }

    @Override
    public List<ScheduleJobVo> findScheduleList(String scheduleName, Integer jobStatus) {
        log.info("任务搜索名称为:"+scheduleName+"任务分组类型为:"+jobStatus);
        return scheduleMapper.findScheduleList(scheduleName,jobStatus);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int addScheduleJob(ScheduleJobDto scheduleJobDto) {
        log.info("存储的定时任务信息为:"+ JSON.toJSONString(scheduleJobDto));
        TaskCronDto taskCronDto= JsonUtil.json2Obj(scheduleJobDto.getCronDto(),TaskCronDto.class);
        //获取Cron表达式
        String cronExpression= CronUtil.createCronExpression(taskCronDto);
        ScheduleJobVo scheduleJobVo=new ScheduleJobVo();
        scheduleJobVo.setCronExpression(cronExpression);
        scheduleJobVo.setDescription(scheduleJobDto.getDescription());
        scheduleJobVo.setJobGroup(scheduleJobDto.getJobGroup());
        scheduleJobVo.setJobName(scheduleJobDto.getJobName());
        scheduleJobVo.setJobStatus(0);
        scheduleJobVo.setQuartzClass(scheduleJobDto.getQuartzClass());
       return scheduleMapper.addScheduleJob(scheduleJobVo);
    }

    @Override
    public int updateScheduleJob(ScheduleJobDto scheduleJobDto) {
        log.info("修改的定时任务信息为:"+JSON.toJSONString(scheduleJobDto));
        TaskCronDto taskCronDto= JsonUtil.json2Obj(scheduleJobDto.getCronDto(),TaskCronDto.class);
        /* 获取Cron表达式 */
        String cronExpression= CronUtil.createCronExpression(taskCronDto);
        ScheduleJobVo scheduleJobVo=new ScheduleJobVo();
        scheduleJobVo.setJobId(scheduleJobDto.getJobId());
        scheduleJobVo.setCronExpression(cronExpression);
        scheduleJobVo.setDescription(scheduleJobDto.getDescription());
        scheduleJobVo.setJobGroup(scheduleJobDto.getJobGroup());
        scheduleJobVo.setJobName(scheduleJobDto.getJobName());
        scheduleJobVo.setJobStatus(scheduleJobDto.getJobStatus());
        scheduleJobVo.setQuartzClass(scheduleJobDto.getQuartzClass());
        return scheduleMapper.updateScheduleJob(scheduleJobVo);
    }
}
