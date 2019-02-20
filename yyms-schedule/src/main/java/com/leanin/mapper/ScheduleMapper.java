package com.leanin.mapper;

import com.leanin.domain.vo.ScheduleJobVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 定时任务调度Mapper
 * @author Administrator
 */
@Mapper
public interface ScheduleMapper {

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
    List<ScheduleJobVo> findScheduleList(@Param("scheduleName") String scheduleName,@Param("jobStatus") Integer jobStatus);
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
