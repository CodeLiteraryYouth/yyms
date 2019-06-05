package com.leanin.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 
 * @author Administrator
 *
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PlanInfoVo implements Serializable {
	
    private String planNum;	//计划单号

    private String planName;	//计划名称

    private Integer patientInfoSource;	//病人来源(1:出院 2：门诊 3：在院 4：体检 5：建档)
    
    private Date planBeginTime;	//筛选患者开始时间

    private Date planEndTime;	//筛选患者结束时间

    private String planWardCode;	//随访科室编码

    private Long planDutyPer;	//计划责任人

    private String planDutyPerName; //计划责任人名称

    private String planTarget;	//计划目的

    private Integer planSendType;	//（1：APP/短信 2：APP 3：短信）

    private Long formTypeId;	//表单分类ID

    private String followFormNum;	//表单号

    private Long rulesInfoNum;	//规则号

    private Integer planSex;	//病人性别
    
    private String patsWardCode;	//病人科室  中文名

    private String planAgeInterval;	//年龄段区间

    private Integer planExisPhone;	//（1：有联系方式 2：无联系方式）

    private Integer planType;	//（1：随访计划  2：宣教计划）

    private Integer planStatus;	// -1：删除 0：正在使用 1：宣教已发送 2:禁用

    private String areaCode;	//院区编码
    
    private String diseaseCode;	//疾病编码
    
    private Long planCreater;	//创建人

    private String createrName; //创建人名称
    
    private Date createDate;	//创建时间

    private Integer importData; //是否将病人数据导入到 数据库中 1 已导入 2未导入

    private String msgId;//短信id

    private Long msgType;//短信类型

    private String patsWardId;  //患者科室码

    private String rulesText;   //规则字符串

    private RulesInfoVo rulesInfoVo;//规则信息

    private Date overTime;      //计划结束时间

    private String planWardName;    //计划负责科室

    private String rulesName;       //规则名称

}