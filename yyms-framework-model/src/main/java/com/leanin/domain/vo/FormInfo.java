package com.leanin.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * 表单信息
 * @author Administrator
 *
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class FormInfo {
	
    private String formNum;		//表单号

    private String formCreater;		//表单创建人

    private Date formCreateTime;	//表单创建时间

    private Long formFormId;	//表单分类ID

    private String formFunction;	//表单功能

    private String formRefer;	//引用表单的表单号

    private String formName;	//表单名称

    private String formTitle;	//表单开头语

    private String formBottom;	//表单结束语

    private Integer formStatus;		//表单状态（-1：已删除 0：正在使用 ：1：共享 2:已禁用）

    private String createrWard;		//创始人科室编码

    private Integer formType;	//表单类型（1：随访表单 2：宣教表单）

    private String areaCode;	//院区编码

    private String formText;	//表单文本数据

}