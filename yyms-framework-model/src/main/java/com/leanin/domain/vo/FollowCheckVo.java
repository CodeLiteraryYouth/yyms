package com.leanin.domain.vo;

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
public class FollowCheckVo {
	
    private String checkNum;	//抽查订单号

    private String checkName;	//抽查计划名称

    private String wardCode;	//抽查科室编码

    private Long checkPerson;	//抽查人员（以逗号拼接）

    private String checkPersonName; //抽查人姓名

    private Integer checkPatientType;	//抽查人员类型（病人来源）

    private String checkPatientWard;	//病人科室

    private Date followBeginDate;	//抽查起始时间

    private Date followEndDate;	//抽查结束时间

    private String checkRatio;	//抽查比例

    private String planNum;	//随访计划单号

    private String areaCode;	//院区编码
    
    private int checkStatus;	//抽查状态

    private Long creater;	    //创建人
    private String createrName; //创建人姓名
    
    private Date createDate;	//创建时间

    private Integer unfinishCount;  //待完成人数
}