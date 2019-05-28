package com.leanin.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * 投诉表扬
 * @author Administrator
 *
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CallBackVo {
	
    private String callBackNum;	//投诉表扬处理单号

    private String callBackWard;	//科室编码

    private String adminName;	//操作人员姓名

    private Integer callBackType;	//（1：医疗技术）

    private String callBackText;	//事件概述

    private String callBackKeyText;	//事件重点

    private String patientId;	//病人唯一标识

    private String callBackName;	//投诉/表扬姓名

    private String callBackPhone;	//投诉/表扬联系方式

    private String callBackSource;	//投诉/表扬来源

    private String patientRealtion;	//与患者关系

    private String callBackTarget;	//投诉/表扬目的

    private Date callBackDate;	//投诉/表扬 时间

    private Integer callBackStatus;	//（1：投诉 2：表扬）

    private String areaCode;	//院区编码

}