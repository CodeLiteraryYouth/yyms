package com.leanin.domain.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MyFollowReq implements Serializable {

    private Long userId;            //登录人id
    private Integer planType;       //计划类型  1：随访计划 2：宣教计划
    private Date startDate;       //开始时间
    private Date endDate;         //结束时间
    private String planId;            //计划id
    private Integer folStatus;      //随访状态  1 未完成 2已完成 3 随访异常
    private String patName;         //患者姓名
    private Integer currentPage;    //当前页
    private Integer pageSize;       //每页展示数据条数

}
