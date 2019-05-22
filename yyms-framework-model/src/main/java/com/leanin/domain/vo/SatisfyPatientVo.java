package com.leanin.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * 满意度病人
 * @author Administrator
 *
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SatisfyPatientVo implements Serializable {
	
    private Long patientSatisfyId;	//满意度主键

    private String satisfyPlanNum;	//满意度号

    private SatisfyPlanVo satisfyPlanVo;        // 计划名称

    private Long patientId;	//病人唯一标识

    private String patientName;	//病人姓名

    private Integer patientSex;	//病人性别

    private Integer patientAge;	//病人年龄

    private String patientPhone;	//病人手机号码

    private String patientWard;	//患者科室

    private Date patientDateTime;	//病人时间

    private Integer finishType;	//完成状态

    private String patientCondition;	//病人情况

    private String patientDiagous;	//病人诊断

    private Integer patientType;	//病人来源

    private Integer patientStatus;	//病人状态

    private String areaCode;	//院区编码

    private Integer sendType; //发送状态；0 未发送 1已发送 2 发送失败

    private String suggess; //处理意见

    private String idCard;  //身份证号

    private String inhosNo; //住院号

    private Integer formStatus;  //表单填写状态啊 1 未填写 2 已填写

    private String formId;  //满意度表单id

    private String openId;

    private Date patientTime;   //患者时间

    private String patientWardId;   //患者科室主键

    private String diagousId;       //患者疾病主键

}