package com.leanin.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;


@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class StyInfoRecordVo {

    private String  satisfyId;   //主键
    private String  satisfyNum; //满意度表单号
    private String  satisfyName; //满意度表单名称
    private Date    createTime;    //创建时间
    private Long    formCreater;   //表单创建者
    private String  satisfyText; //满意度正文
    private String  satisfyTypeId;//满意度分类主键
    private String  satisfyTitle;  //表单头
    private String  satisfyBottom;   //表单尾
    private String  areaCode;        //院区编码
    private Integer finishStatus;   //完成状态  1 已完成
    private Long    planPatientId;     //患者主键
    private Long    operatingId;       //操作人id
    private Long   patientId;       //患者主键
    private Integer  formStatus;         //微信唯一标识
    private String  idCard;         //身份证号
    private String  inhosNo;        //住院号
    private String  ex4;
    private String  ex5;


}
