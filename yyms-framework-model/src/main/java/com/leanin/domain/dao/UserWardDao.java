package com.leanin.domain.dao;


import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 用户与科室关联表
 */
@Data
@ToString
@Entity
@Table(name = "leanin_user_ward")
public class UserWardDao implements Serializable {

    @Id
    @Column(name = "id",nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;            //主键

    @Column(name = "user_id",nullable = false)
    private Long userId;        //用户主键

    @Column(name = "ward_id",nullable = false)
    private Long wardId;        //科室主键

    @Column(name = "create_time",nullable = true)
    private Date createTime;    //创建时间

    @Column(name = "creater",nullable = true)
    private Long create;        //创建者

    @Column(name = "ex1",nullable = true)
    private String ex1;         //扩展字段

    @Column(name = "ex2",nullable = true)
    private String ex2;         //扩展字段

    @Column(name = "ex3",nullable = true)
    private String ex3;         //扩展字段

    @Column(name = "ex4",nullable = true)
    private String ex4;         //扩展字段

    @Column(name = "ex5",nullable = true)
    private String ex5;         //扩展字段



}
