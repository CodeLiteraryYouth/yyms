package com.leanin.domain.analysis;

import lombok.Data;

/**
 * 随访部门统计对象
 * @author CPJ.
 * @date 2019/5/22.
 * @time 11:35.
 */
@Data
public class FollowDept {

    private Integer state;      //状态

    private Long dutyPerId;     //负责人id

    private Long dutyPerName;   //负责人名称

    private Integer hitCount;   //个数

}
