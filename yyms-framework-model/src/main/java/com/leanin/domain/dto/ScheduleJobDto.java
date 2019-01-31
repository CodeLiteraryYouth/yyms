package com.leanin.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 定时任务传输类
 * @author Administrator
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleJobDto {
    /**
     * 任务ID
     */
    private Long jobId;
    /**
     * 任务名称
     */
    private String jobName;
    /**
     * 任务分组
     */
    private String jobGroup;
    /**
     * 任务状态 0禁用 1启用
     */
    private Integer jobStatus;

    /**
     * 定时任务处理类
     */
    private String quartzClass;
    /**
     * 描述信息
     */
    private String description;

    /**
     * 触发频次的JSON串
     */
    private String cronDto;
}
