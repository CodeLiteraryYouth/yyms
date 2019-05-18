package com.leanin.domain.dao;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Data
@ToString
@Entity
@Table(name = "leanin_wx_send")
public class WxSendDao {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;          //主键

    @Column(name = "plan_patient_id")
    private Long planPatientId; //计划患者主键

    @Column(name = "patient_id")
    private String patientId;  //his患者主键

    @Column(name = "open_id")
    private String openId;      //openid

    @Column(name = "patient_name")
    private String patientName; //患者名称

    @Column(name = "error_code")
    private Integer errorCode;  //错误代码

    @Column(name = "errmsg")
    private String errmsg;      //错误信息

    @Column(name = "msg_id")
    private Long msgId;         //发送成功返回的msgId

    @Column(name = "area_code")
    private String areaCode;    //院区

    @Column(name = "ward_code")
    private String wardCode;    //科室

    @Column(name = "msg_title")
    private String msgTitle;    //消息头

    @Column(name = "msg_remark")
    private String msgRemark;   //消息评论

    @Column(name = "send_status")
    private Integer sendStatus; //发送状态

    @Column(name = "form_id")
    private String formId;      //表单主键

    @Column(name = "plan_type")
    private Integer planType;   //计划类型

    @Column(name = "template_id")
    private String templateId;  //微信模板id

    @Column(name = "form_url")
    private String formUrl;     //表单url

    @Column(name = "create_time")
    private Date createTime;    //创建时间

    @Column(name = "patient_source")
    private Integer patientSource;  //患者来源

    @Column(name = "plan_num")
    private String planNum;         //计划主键

    @Column(name = "next_date")
    private Date nextDate;         //计划发送时间

    @Column(name = "patient_ward")
    private String patientWard;         //患者科室




}
