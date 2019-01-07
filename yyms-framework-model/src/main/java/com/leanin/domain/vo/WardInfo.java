package com.leanin.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 操作用户科室
 * @author Administrator
 *
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class WardInfo {
	
    private Long wardId;	//科室主键

	private String wardName;	//科室名称
	
	private String wardCode;	//科室编码

	private String preWardName;	//上级科室名称

	private String preWardCode;	//上级科室代码

	private String pinyinCode;	//拼音码

	private String wardPhone;	//科室电话

	private String orderCode;	//顺序码

	private String areaCode;	//院区编码

	private Integer wardStatus;	//科室状态

}