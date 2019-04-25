package com.leanin.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@ToString
@Entity
@Table(name = "leanin_online_edu")
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
public class OnlineEdu implements Serializable {

    @Id
    @Column(name = "edu_id")
    @GeneratedValue(generator = "jpa-uuid")
//    @GenericGenerator(name = "system-identity",strategy = "identity")
    private String eduId;            //主键

    @Column(name = "patient_id")
    private String patientId;      //患者主键

    @Column(name = "patient_name")
    private String patientName;    //患者名称

    @Column(name = "form_id")
    private String formId;         //表单主键

    @Column(name = "form_status")
    private Integer formStatus;      //完成状态 1 未完成 2 已完成

    @Column(name = "send_user")
    private Long sendUser;      //发送人主键

    @Column(name = "send_time")
    private Date sendTime;          //发送时间

    @Column(name = "bed_no")
    private String bedNo;           //患者床号

    @Column(name = "inhos_no")
    private String inhosNo;         //住院号

    @Column(name = "inhos_date")
    private Date inhosDate;         //住院日期

    @Column(name = "send_status")
    private Integer sendStatus;     //发送状态

    @Column(name = "msg_id")
    private String msgId;     //短信主键

    @Column(name = "phone_num")
    private String phoneNum;     //手机号



}
