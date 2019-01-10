package com.leanin.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 处方信息实体类
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OutHosRecipe {
    private Long hosId;             //医疗机构唯一识别号
    private Long patientId;         //院内病人唯一识别号
    private Long registrationId;    //就诊识别号，此字段与上面两个字段，构成唯一识别号
    private String drugName;        //药品名称
    private String drugSpec;        //药品规格
    private String drugUnit;        //药品单位
    private String drugQuantity;    //药品数量
    private String drugUsage;       //药品用法
}
