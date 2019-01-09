package com.leanin.domain.patient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.lang.model.element.NestingKind;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OutHosPatInfo {
    private Long hosId;             //医疗机构唯一识别号
    private Long patientId;         //院内病人唯一识别号,此字段与HOS_ID构成联合主键
    private String outhosNo;        //门诊号码
    private String patientName;     //病人姓名
    private Integer sex;            //病人性别，1男2女
    private String idNumber;        //身份证号（15或18位）
    private String phoneNumber;     //电话号码
    private String homeAddr;        //家庭地址
    private String contactOther;    //其他联系方式
    private String unitName;        //单位名称
    private Integer marriage;       //婚否（0未知1是2否）
    private String medHistory;      //疾病史
    private String allergicHistory; //过敏史
    private String familyHistory;   //家族史
    private String operationHistory;//手术史
}
