package com.leanin.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

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

    private String patientId;	//病人唯一标识

    private String patientName;	//病人姓名

    private Integer patientSex;	//病人性别  1 男 2 女

    private Integer patientAge;	//病人年龄

    private String patientPhone;	//病人手机号码

    private String patientIdCard;	//病人身份证号码

    private Integer patientType;	//病人来源

    private String areaCode;	//病区编码

    private Integer sendType;  //发送状态

    private String idCard;      //身份证号

    private String inhosNo;     //住院号

    private String openId;      //openid

    private Integer patientStatus;  //-1 删除  1 使用

    private String patientWard;     //患者his科室中文名

    private String patientWardId;   //his科室码

    private Date patientTime;       //患者时间

    private String illnessId;       //his疾病码

    private String illnessName;     //his疾病名称




}