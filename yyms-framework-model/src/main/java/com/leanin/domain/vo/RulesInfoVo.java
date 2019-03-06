package com.leanin.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * 表单规则信息
 * @author Administrator
 *
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RulesInfoVo {
	
    private Long rulesInfoId;	//规则主键

    private Integer rulesInfoTypeName;	//（1：阶段随访 2：定时随访 3：普通随访 4：闭环宣教 5：普通宣教 6：药品宣教 7：疾病宣教 8：普通提醒）

    private String rulesInfoName;	//规则名称

    private Integer rulesInfoType;	//（1：随访规则 2：宣教规则）

    private Long rulesCreater;	//规则创建者

    private String createrName;  //创建人名称


    private String rulesCreaterWard;	//规则创建者科室

    private Date rulesCreateTime;	//规则创建时间

    private Integer rulesInfoStatus;	// -1：删除 0：正在使用 1:共享 2:禁用

    private Long followFormId;	//类型ID

    private String areaCode;	//院区编码

    private String rulesInfoText;	//规则内容

}