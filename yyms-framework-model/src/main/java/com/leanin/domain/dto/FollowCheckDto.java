package com.leanin.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * 随访抽查信息
 * @author Administrator
 *
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class FollowCheckDto {
	
    private String checkNum;	//抽查订单号

    private String checkName;	//抽查计划名称

    private String wardCode;	//抽查科室编码

    private String checkPerson;	//抽查人员（以逗号拼接）

    private Integer checkPatientType;	//抽查人员类型（病人来源）

    private String checkPatientWard;	//病人科室

    private Date followBeginDate;	//抽查起始时间

    private Date followEndDate;	//抽查结束时间

    private String checkRatio;	//抽查比例

    private Long planNum;	//随访计划单号

    private String areaCode;	//院区编码
    
    private int checkStatus;	//抽查状态

    private String creater;	//创建人
    
    private Date createrDate;	//创建时间
}