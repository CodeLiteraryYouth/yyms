package com.leanin.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 检查信息实体类
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OutHosCheck {
    private Long hosId;             //医疗机构唯一识别号
    private Long patientId;         //院内病人唯一识别号
    private Long registrationId;    //就诊识别号，此字段与上面两个字段，构成唯一识别号
    private String checkName;       //检查名称
    private String checkUnit;       //检查单位
    private String checkQuantity;   //检查数量
    private String checkResult;     //检查结果

}
