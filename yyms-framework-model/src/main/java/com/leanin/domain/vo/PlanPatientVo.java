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

    private PlanInfoVo planInfoVo;    //计划

    private Long patientId;    //病人唯一标识

    private Integer sendType; //发送状态

    private String patientName;    //病人姓名

    private Integer patientSex;    //病人性别

    private Integer patientAge;    //病人年龄

    private String patientPhone;    //病人手机号

    private String patientWard;  //患者科室

    private Integer formStatus;    //表单填写状态 1 未填写  2 已填写

    private Integer patientStatus;    //患者状态  1 正在使用 2 已删除

    private String patientCondition;    //病人情况

    private String patientDiagous;    //病人诊断

    private Integer patientType;    //病人来源

    private String areaCode;    //院区编码

    private Integer planPatsStatus; //-1:收案 0：未发送表单或者短信前的状态 1：待随访 2：已完成；3:已过期; 4 无法接听 5 号码错误 6 拒绝接听 7 无人接听 8 家属接听 9 患者不合作 10 无联系电话 11 其他

    private Long rulesInfoId; //随访规则id

    private Date nextDate; //下次随访日期

    private Integer patientSource;//患者来源 1,出院；2,门诊;3,在院

    private String handleSugges;//处理意见

    private String idCard;      //身份证号
    private String inhosNo;     //住院号

    private String opendId;     //openid

    private String formId;      //表单id

    private String patientWardId;   //患者科室码

    private Date patientTime;       //患者时间

    private String diagousId;       //患者疾病主键

//    private String sendStatus;  //发送状态

}