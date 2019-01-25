package com.leanin.service.impl;

import com.leanin.domain.vo.ScheduleJobVo;
import com.leanin.mapper.ScheduleMapper;
import com.leanin.service.ScheduleService;
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
    public int addScheduleJob(ScheduleJobVo scheduleJob) {
       return scheduleMapper.addScheduleJob(scheduleJob);
    }

    @Override
    public int updateScheduleJob(ScheduleJobVo scheduleJob) {
        return scheduleMapper.updateScheduleJob(scheduleJob);
    }
}
