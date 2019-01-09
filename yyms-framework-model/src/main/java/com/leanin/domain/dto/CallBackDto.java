package com.leanin.domain.dto;

import com.leanin.domain.vo.CallBackVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 投诉表扬信息
 * @author Administrator
 *
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CallBackDto {

	private String dealNum;	//处理单号
	
	private CallBackVo callBack;	//投诉表扬处理单号
	
	private String adminName;	//操作人员姓名
	
	private String accuParisCondi;	//投诉/表扬情况
	
	private String solveAccu;	//核实情况及解决方案
	
	private Integer isPhone;	//（1：已沟通 2：未沟通）
	
	private Integer isSatisfy;	//（1：满意 2：不满意）
	
	private String connResult;	//沟通结果描述
	
	private String dealName;	//处理人姓名
	
	private Integer dealStatus;	//处理状态（0：待处理 1：处理中 2：未处理）
	
	private String areaCode;	//院区编码
	
}

