package com.leanin.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "leanin_msg_send_record")
public class MessageRecord {

    @Id
    @Column(name = "msg_send_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long msgSendId;         //主键

    @Column(name = "msg_send_name")
    private Long msgSendName;       //发送人

    @Column(name = "msg_send_ward")
    private String msgSendWard;     //发送人科室

    @Column(name = "msg_send_date")
    private Date msgSendDate;       //发送时间

    @Column(name = "msg_send_num")
    private String msgSendNum;      //被发送人手机号

    @Column(name = "msg_text")
    private String msgText;         //短信内容

    @Column(name = "msg_send_status")
    private Integer msgSendStatus;  //发送状态

    @Column(name = "msg_them")
    private String msgThem;         //短信主题

    @Column(name = "plan_type")
    private Integer planType;       //计划类型

    @Column(name = "plan_patient_id")
    private Long planPatientId;     //计划患者id

    @Column(name = "patient_id")
    private String patientId;       //his患者唯一标记

    @Column(name = "form_id")
    private String formId;          //表单id

    @Column(name = "plan_num")
    private String planNum;         //计划号

    @Column(name = "patient_ward")
    private String patientWard;     //患者科室

    @Column(name = "next_date")
    private Date nextDate;          //计划发送时间

    @Column(name = "patient_source")
    private Integer patientSource;  //患者来源





}
