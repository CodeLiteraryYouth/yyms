package com.leanin.domain.analysis;

import lombok.Data;

import java.io.Serializable;

/**
 * @author CPJ.
 * @date 2019/5/16.
 * @time 17:26.
 */
@Data
public class DeptAnalysis /*implements Serializable*/ {

//    private static final long serialVersionUID = 6492562462042738636L;

    private String deptName;        //部门名称

    private Integer state;          //状态

    private Integer hitCount;       //个数

    private Long totalSecond;       //总的时间秒数

    private Integer followUpMonth;  //随访月份


}
