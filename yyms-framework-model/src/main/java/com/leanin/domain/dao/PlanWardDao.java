package com.leanin.domain.dao;


import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

/**
 * 计划与科室关联表
 */
@Data
@ToString
@Entity
@Table(name = "leanin_plan_ward")
public class PlanWardDao {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;            //主键


    @Column(name = "plan_id")
    private String planId;      //计划主键

    @Column(name = "ward_id")
    private Long wardId;        //科室主键

    @Column(name = "plan_type")
    private Integer planType;   //计划类型  1 随访 2 宣教 3 满意度 4 短信主题

    @Column(name = "create_time")
    private Date createTime;    //创建时间

    @Column(name = "creater")
    private Long creater;       //创建者

    @Column(name = "ex1")
    private String ex1;         //扩展字段

    @Column(name = "ex2")
    private String ex2;         //扩展字段

    @Column(name = "ex3")
    private String ex3;         //扩展字段

    @Column(name = "ex4")
    private String ex4;         //扩展字段

    @Column(name = "ex5")
    private String ex5;         //扩展字段
}
