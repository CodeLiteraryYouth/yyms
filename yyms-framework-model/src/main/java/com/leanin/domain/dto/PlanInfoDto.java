package com.leanin.domain.dto;


import com.leanin.domain.vo.FormInfoVo;
import com.leanin.domain.vo.FormTypeVo;
import com.leanin.domain.vo.RulesInfoVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * 表单信息传输实体类
 * @author Administrator
 *
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PlanInfoDto {

	 	private String planNum;	//计划单号

	    private String planName;	//计划名称

	    private Integer patientInfoSource;	//病人来源(1:出院 2：门诊 3：在院 4：体检 5：建档)
	    
	    private Date planBeginTime;	//计划起始时间

	    private Date planEndTime;	//计划结束时间

	    private String planWardCode;	//随访科室编码

	    private String planDutyPer;	//计划责任人

	    private String planTarget;	//计划目的

	    private Integer planSendType;	//（1：APP/短信 2：APP 3：短信）

	    private FormTypeVo formType;	//表单分类ID

	    private FormInfoVo formInfo;	//表单号

	    private RulesInfoVo rulesInfo;	//规则号

	    private Integer planSex;	//病人性别
	    
	    private String patsWardCode;	//病人科室

	    private String planAgeInterval;	//年龄段区间

	    private Integer planExisPhone;	//（1：有联系方式 2：无联系方式）

	    private Integer planType;	//（1：随访计划  2：宣教计划）

	    private Integer planStatus;	// -1：删除 0：正在使用 1：宣教已发送 2:禁用

	    private String areaCode;	//院区编码
	    
	    private String diseaseCode;	//疾病编码
	    
	    private String creater;	//创建人
	    
	    private Date createDate;	//创建时间
}
