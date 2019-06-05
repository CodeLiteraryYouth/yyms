package com.leanin.domain.dao;

import lombok.Data;

import javax.persistence.*;

/**
 * 满意度表单 选项统计
 * @author CPJ.
 * @date 2019/6/3.
 * @time 13:24.
 */
@Data
@Entity
@Table(name = "satisfy_form_stat")
public class SatisfyFormStatDao {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  //主键

    @Column(name = "satisfy_form_num")
    private String satisfyFormNum; //满意度表单号

    @Column(name = "satisfy_form_content")
    private String satisfyFormContent; //满意度表单内容

    @Column(name = "satisfy_plan_num")
    private String satisfyPlanNum; //满意度计划号

    @Column(name = "request_count")
    private Long requestCount; //请求次数

    @Column(name = "satisfy_title")
    private String satisfyTitle;    //满意度表单头

    @Column(name = "satisfy_bottom")
    private String satisfyBottom;   //满意度表单尾

}
