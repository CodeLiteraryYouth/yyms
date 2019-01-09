package com.leanin.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * 通话设置
 * @author Administrator
 *
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CallInfoVo {
	
    private String callId;	//童话单号

    private Integer callType;	//呼叫类型

    private String callNumber;	//呼叫号码

    private String callAddre;	//呼叫地址

    private Integer callDealStatus;	//处理状态

    private Date callBeginDate;	//呼叫起始时间

    private Date callEndDate;	//呼叫截止时间

    private String callSourceNumber;	//去电号码

    private String callDealName;	//处理坐席

    private String callLength;	//通话时长

    private String callCreater;	//呼叫创建人

    private Date callCreateTime;	//呼叫管理创建时间

    private String callCreateWard;	//呼叫创建科室

    private String remark;	//备注

    private String areaCode;	//院区编码

}