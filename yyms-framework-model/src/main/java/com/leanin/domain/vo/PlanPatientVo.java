package com.leanin.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * 计划病人信息
 * @author Administrator
 *
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PlanPatientVo {
	
    private Long patientPlanId;	//计划病人信息主键

    private String planNum;	//计划单号

    private Long patientId;	//病人唯一标识

    private String patientName;	//病人姓名

    private Integer patientSex;	//病人性别

    private Integer patientAge;	//病人年龄

    private String patientPhone;	//病人手机号

    private String patientWard;	//病人院区

    private Date patientDateTime;	//病人时间

    private String patientDoctor;	//诊治医生
   
    private String patientCondition;	//病人情况

    private String patientDiagous;	//病人诊断

    private Integer patientType;	//病人来源

    private String areaCode;	//院区编码
  
}