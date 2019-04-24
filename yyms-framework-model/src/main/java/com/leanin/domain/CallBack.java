package com.leanin.domain;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 投诉表扬
 * @author Administrator
 *
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CallBack {

	private String callBackNum;	//投诉表扬信息单号
	
	private String callBackWard;	//被投诉/表扬科室编码
	
	private String callBackName;	//投诉/表扬姓名
	
	private String callBackText;	//事件概述
	
	private String patientId;	//病人唯一标识
	
	private String patientName;	//病人姓名
	
	private String phone;	//手机号
	
	private Integer source;	//病人来源(1:出院 2：门诊 3：在院 4：体检 5：建档)
	
	private Date callBackDate;		//  投诉/表扬时间
	
	private String acuuPariseName;	// 被 投诉/表扬姓名
	
	private String relation;	//与患者关系
	
	private String accuParRela;	//投诉/表扬来源
	
	private String target;	//投诉/表扬目的
	
	private String pushWard;	//推送科室
	
	private String pushName;	//推送人
	
	private Date pushDate;	//推送时间
	
	private String areaCode;	//院区编码
	
	private Integer status;	//（1：投诉 2：表扬）
	
	private Integer callBackType;	//投诉表扬处理类型（1：医疗技术 2：服务态度 3：不及时）
	
	private Long assignName;	   //处理人

	private String name;		//处理人姓名

	private Integer dealStatus;		//处理状态
}
