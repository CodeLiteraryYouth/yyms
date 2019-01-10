package com.leanin.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 就诊记录实体类
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OutHosRecord {
    private Long hosId;             //医疗机构唯一识别号
    private Long patientId;         //院内病人唯一识别号
    private Long registrationId;    //就诊识别号，此字段与上面两个字段，构成唯一识别号
    private String hosDept;         //门诊科室
    private String hosDoctor;       //就诊医生
    private String outhosDate;      //就诊时间
    private String diagnosisIcd;    //诊断代码（ICD10）
    private String diagnosisName;   //诊断名称
    private String selfComplain;    //病人自述
}
