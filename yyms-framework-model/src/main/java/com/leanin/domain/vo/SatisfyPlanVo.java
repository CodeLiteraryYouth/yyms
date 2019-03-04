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
public class  SatisfyPlanVo {
	
    private String planSatisfyNum;	//满意度计划编号

    private String planSatisfyName;	//满意度计划名称

    private Integer patientType;	//病人来源

    private Date planSatisfyBeginDate;	//满意度计划起始时间

    private Date planSatisfyEndDate;	//满意度计划结束时间

    private String patientWard;	//院区编码

    private Long discoverPerson;	//调查人员

    private String discoverTarget;	//调查目的

    private Integer discoverType;	//（1：APP/短信 2：APP 3：短信）

    private Long formTypeId;	//表单类别ID

    private String satisfyNum;	//满意度表单号

    private Integer patientSex;	//（1：男 2：女）

    private String patientAge;	//年龄

    private Integer patientPhone;	//（0：无联系方式 1：有联系方式）

    private String areaCode;	//院区编码

    private Integer satisfyPlanStatus; //计划状态(-1:已删除 0：正在使用 1：禁用)

    private String rulesText;   //规则的JSON串内容

    private String diseaseName; //疾病名称

    private Long creater; //计划创建者

    private Date createDate;    //创建时间

    private String satisfyPlanWard;  //计划科室

    private Integer unfinishCount; // 未完成人数

    private String msgId; //短信id

    private Long msgType; //短信类型
}