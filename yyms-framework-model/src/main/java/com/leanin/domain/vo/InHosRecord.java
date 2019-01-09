package com.leanin.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class InHosRecord {
    private Long hosId; //医院id
    private Long patientId;//患者id
    private String dept;//科室id
    private String dist;//医院病区id
    private String outhosDate;//出院日期
    private String diagnosisIcd;//诊断代码
    private String diagnosisName;//诊断名称
    private Integer inOut;// 住院状态 1在院  2出院
}
