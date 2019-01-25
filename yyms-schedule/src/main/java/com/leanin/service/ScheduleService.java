package com.leanin.service;

import com.leanin.domain.vo.ScheduleJobVo;
import java.util.List;

/**
 * @author Administrator
 */
public interface ScheduleService {
    /**
     * 查询禁用的任务列表
     * @return
     */
    List<ScheduleJobVo> findDelJobList();
    /**
     * 查询已启用的任务列表
     * @return
     */
    List<ScheduleJobVo> findLegalJobList();
    /**
     * 根据任务名称或者类别来查询任务列表
     * @param scheduleName
     * @param jobStatus
     * @return
     */
    List<ScheduleJobVo> findScheduleList( String scheduleName,Integer jobStatus);
    /**
     * 添加定时任务
     * @param scheduleJob
     * @return
     */
    int addScheduleJob(ScheduleJobVo scheduleJob);

    /**
     * 修改定时任务
     * @param scheduleJob
     * @return
     */
    int updateScheduleJob(ScheduleJobVo scheduleJob);
}
