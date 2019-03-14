package com.leanin.domain.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BindPat implements Serializable {

    private String patientName;//患者名称
    private String idCard;     //身份证号
    private String phoneNum;    //手机号码

    private String code;        //授权码

}
