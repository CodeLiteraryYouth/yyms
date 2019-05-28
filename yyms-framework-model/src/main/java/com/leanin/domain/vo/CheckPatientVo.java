package com.leanin.domain.vo;

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
public class CheckPatientVo {
	
    private Long checkPatientId;	//抽查病人主键

    private String checkNum;	//抽查编号

    private String patientId;	//病人唯一标识

    private String formId;      //表单id

    private String opendId;      //表单id

    private String idCard;      //表单id

    private String inhosNo;      //表单id

    private String patientName;	//抽查病人姓名

    private Integer patientSex;	//病人性别

    private Integer patientAge;	//病人年龄

    private String patientPhone;	//电话号码

    private String patientWard;	//病人科室

    private Integer patientStatus;	//状态  1使用  2删除

    private String patientCondition;	//病人情况

    private String patientDiagous;	//病人诊断

    private Integer patientSource;	//病人来源

    private String handleSugges;	//随访患者处理情况

    private String areaCode;	//院区编码

    private String finishStatus;


    private String ex2;
    private String ex3;
    private String ex4;
    private String ex5;


}