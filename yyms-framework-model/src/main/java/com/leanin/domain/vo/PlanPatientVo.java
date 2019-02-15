package com.leanin.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * 计划病人信息
 *
 * @author Administrator
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PlanPatientVo implements Serializable {

    private Long patientPlanId;    //计划病人信息主键

    private String planNum;    //计划单号

    private Long patientId;    //病人唯一标识

    private Integer sendType; //发送状态

    private String patientName;    //病人姓名

    private Integer patientSex;    //病人性别

    private Integer patientAge;    //病人年龄

    private String patientPhone;    //病人手机号

    private String patientWard;  //出院科室

    private Integer followType;    //随访状态  1未完成  2 已完成

    private Integer patientStatus;    //患者状态  1 正在使用 2 已删除

    private String patientCondition;    //病人情况

    private String patientDiagous;    //病人诊断

    private Integer patientType;    //病人来源

    private String areaCode;    //院区编码

    private Integer planPatsStatus; //-1:已移除 0：正在使用

    private Long rulesInfoId; //随访规则id

    private Date nextDate; //下次随访日期

    private Integer patientSource;//患者来源 1,出院；2,门诊;3,在院

    private String sendStatus;  //发送状态

}