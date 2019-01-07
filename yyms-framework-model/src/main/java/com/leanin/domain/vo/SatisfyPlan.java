package com.leanin.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * 满意度计划
 * @author Administrator
 *
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SatisfyPlan {
	
    private String planSatisfyNum;	//满意度计划编号

    private String planSatisfyName;	//满意度计划名称

    private Integer patientType;	//病人来源

    private Date planSatisfyBeginDate;	//满意度计划起始时间

    private Date planSatisfyEndDate;	//满意度计划结束时间

    private String patientWard;	//院区编码

    private String discoverPerson;	//调查人员

    private String discoverTarget;	//调查目的

    private Integer discoverType;	//（1：APP/短信 2：APP 3：短信）

    private Long formTypeId;	//表单类别ID

    private String satisfyNum;	//满意度表单号

    private Date satisfySendTime;	//发送时间

    private Integer satisfyExpireDays;	//有效时间

    private String satisfyExpireUnit;	//有效时间单位

    private String msgId;	//短信单号

    private Integer patientSex;	//（1：男 2：女）

    private String patientAge;	//年龄

    private Integer patientPhone;	//（0：无联系方式 1：有联系方式）

    private String areaCode;	//院区编码

}