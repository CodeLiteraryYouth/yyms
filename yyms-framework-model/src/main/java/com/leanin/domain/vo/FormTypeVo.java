package com.leanin.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 表单类型
 * @author Administrator
 *
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class FormTypeVo {
	
    private Long formTypeId;	//类型主键

    private String formTypeName;	//类型名称

    private Integer formTypeInfo;	//（1：随访表单类型  2：宣传表单类型 3:随访规则类型 4：宣教规则类型 5：满意度类型 6：短信提醒库类型）

    private String areaCode;	//院区编码

    private Integer typeStatus;	//（-1：注销 0：正在使用）
}