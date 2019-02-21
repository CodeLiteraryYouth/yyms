package com.leanin.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 发送信息主题的病人
 * @author Administrator
 *
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MessagePatientVo {
	
    private Long patientMsgId;	//病人信息主键

    private String msgTopicId;	//短信主题单号

    private Long patientId;	//病人唯一标识

    private String patientName;	//病人姓名

    private Integer patientSex;	//病人性别

    private Integer patientAge;	//病人年龄

    private String patientPhone;	//病人手机号码

    private String patientIdCard;	//病人身份证号码

    private Integer patientType;	//病人来源

    private String areaCode;	//院区编码

    private Integer sendType;  //发送状态

}