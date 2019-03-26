package com.leanin.domain.vo;

import lombok.Data;

import java.util.Date;

@Data
public class BookPatientVo {
    private String bookId;  //主键
    private String PatientName;//患者姓名
    private Integer patientSex;//patient_sex
    private String idCard;  //身份证号
    private String registrationDoc;//挂号医生
    private String registrationDept;//预约科室
    private String bookDoc; //预约医生
    private Date bookTime;  //预约时间
    private Date seeDocTime;//预约就诊时间
    private Integer bookSource;//预约来源
    private Integer patientType;//患者类型
    private String phoneNum;//手机号码
    private String ex1;
    private String ex2;
    private String ex3;
    private String ex4;
    private String ex5;
    private String ex6;
    private String ex7;

}
