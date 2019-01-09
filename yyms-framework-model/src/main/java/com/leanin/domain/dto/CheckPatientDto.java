package com.leanin.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * 抽查病人信息
 * @author Administrator
 *
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CheckPatientDto {
	
    private Long checkPatientId;	//抽查病人主键

    private String checkNum;	//抽查编号

    private Long patientId;	//病人唯一标识

    private String patientName;	//抽查病人姓名	

    private Integer patientSex;	//病人性别

    private Integer patientAge;	//病人年龄

    private String patientPhone;	//电话号码

    private String patientWard;	//病人科室

    private Date patientDateTime;	//创建时间

    private String patientDoctor;	//主治医生

    private String patientCondition;	//病人情况

    private String patientDiagous;	//病人诊断

    private Integer patientType;	//病人来源

    private String areaCode;	//院区编码


}