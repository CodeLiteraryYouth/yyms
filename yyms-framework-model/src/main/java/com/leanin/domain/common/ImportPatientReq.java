package com.leanin.domain.common;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
public class ImportPatientReq {

//    private String planNum;     //移入患者的计划号
    private Long patientId;     //his患者主键
    private String patientName; //患者姓名
    private Integer sex;        // 1 男  2女
    private Integer age;        //年龄
    private String phone;       //患者手机号
    private String ward;        //患者科室
    private Date date;          //患者时间
    private String condition;   //患者情况
    private String diagous;     //患者诊断
    private String areaCode;    //院区编码
    private String idCard;      //身份证号
    private String inhosNo;     //住院号

}
