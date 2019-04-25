package com.leanin.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MessageRecord {

    private Long msgSendId;         //主键
    private Long msgSendName;       //发送人
    private String msgSendWard;     //发送人科室
    private Date msgSendDate;       //发送时间
    private String msgSendNum;      //被发送人手机号
    private String msgText;         //短信内容
    private Integer msgSendStatus;  //发送状态
    private String msgThem;         //短信主题
    private Integer planType;       //计划类型
    private Long planPatientId;     //计划患者id
    private String patientId;       //患者唯一标记
    private String formId;          //表单id
    private String planNum;         //计划号

}
