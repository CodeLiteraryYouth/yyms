package com.leanin.domain.dao;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

/**
 * 随访表单 选项 统计
 * @author CPJ.
 * @date 2019/6/3.
 * @time 13:11.
 */
@Data
@Entity
@Table(name = "follow_form_stat")
public class FollowFormStatDao {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  //主键

    @Column(name = "follow_form_num")
    private String followFormNum; //随访表单号

    @Column(name = "follow_form_content")
    private String followFormContent;   //随访表单内容

    @Column(name = "plan_num")
    private String planNum;   //计划号

    @Column(name = "request_count")
    private Long requestCount;

}

