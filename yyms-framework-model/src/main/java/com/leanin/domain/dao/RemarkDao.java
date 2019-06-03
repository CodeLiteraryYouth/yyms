package com.leanin.domain.dao;

import lombok.Data;

import javax.persistence.*;

/**
 * @author CPJ.
 * @date 2019/6/3.
 * @time 13:32.
 */
@Data
@Entity
@Table(name = "leanin_remark")
public class RemarkDao {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;    //评论主键

    @Column(name = "remark")
    private String remark; //评论

    @Column(name = "form_num")
    private String fromNum; //表单号

    @Column(name = "plan_num")
    private String planNum; //计划号

    @Column(name = "plan_type")
    private Integer planType; //计划类型 1 随访 2 宣教 3 满意度

    @Column(name = "question_id")
    private String questionId;//题目id
}
