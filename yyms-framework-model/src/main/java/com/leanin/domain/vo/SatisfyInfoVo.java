package com.leanin.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 满意度表单
 * @author Administrator
 *
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SatisfyInfoVo {
	
    private String satisfyNum;	//满意度表单号

    private String satisfyName;	//满意度表单名称

    private String satisfyDate;	//满意度题型

    private Integer satisfyStatus;	//（-1：已删除 0：正在使用）

    private Long satisfyTypeId;	//分类主键

    private String areaCode;	//院区编码

    private Long creater;	//表单创建者

    private String satisfyText;	//表单正文

}