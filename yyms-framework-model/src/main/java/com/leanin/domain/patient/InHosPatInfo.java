package com.leanin.domain.patient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class InHosPatInfo {
    private Long hosId; //医院id
    private Long patientId;//患者id
    private String inhosNo;//住院号码
    private String patientName;//患者姓名
    private Integer sex; //患者性别 1男 2女
    private String idNumber; //身份证号（15位或者18位）
    private String phoneNumber; //电话号码
    private String homeAddr; //家庭住址
    private String contactOther; // 其他联系方式
    private String unitName; // 单位名称
    private Integer marriage; // 婚否 0 未知 1已婚 2未婚 3离异
    private String country; //国籍
    private String nationality; //民族
    private String education; //文化程度
    private String medHistory; //疾病史
    private String allergicHistory; //过敏史
    private String familyHistory; //家族史
    private String operationHistory; //手术史



}
