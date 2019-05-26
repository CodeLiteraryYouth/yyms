package com.leanin.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FormRecordVo /*implements Serializable*/ {

    private Long formId;  //表单记录主键
    private String formNum; //表单号
    private Long formFormId; //表单分类id
    private String formFunction; //表单功能
    private String formRefer;   //引用表单的表单号
    private String formName;    //表单名称
    private String formTitle;   //表单开头语
    private String formBottom;  //表单结束语
    private String formText;    //表单内容
    private Integer formType;   //（1：随访表单 2：宣教表单）
    private String areaCode;    //院区编码
    private Long patientPlanId; //患者关连建
    private Long createId;      //表单填写人Id
    private Date createTime;     //创建时间
}
