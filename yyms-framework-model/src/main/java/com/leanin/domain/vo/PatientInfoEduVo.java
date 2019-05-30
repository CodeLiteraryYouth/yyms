package com.leanin.domain.vo;

import lombok.Data;

import java.util.Date;

/**
 * 在线宣教
 * @author CPJ.
 * @date 2019/5/30.
 * @time 10:07.
 */
@Data
public class PatientInfoEduVo extends PatientInfoVo {

    private String formId;     //表单主键

    private String msgId;      //短信主键

    private String bedNo;      //床位号

    private Date inhosDate;    //在院日期

    private String patientDeptId; //患者科室主键

    private String patientDeptName; //患者科室名称

    private String patientDiagnosisIcd; //患者诊断icd

    private String patientDiagnosisName;//患者诊断名称
}
