package com.leanin.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 定时任务调度类
 * @author Administrator
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleJobVo {

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
     * 审核状态 0 已创建 1 审核通过 2 审核驳回
     */
    private Integer auditStatus;
    /**
     * 任务运行时间表达式
     */
    private String cronExpression;
    /**
     * 定时任务处理类
     */
    private String quartzClass;
    /**
     * 描述信息
     */
    private String description;
}
