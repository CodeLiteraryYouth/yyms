package com.leanin.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 患者档案实体类
 */
@Data
public class PatientInfoVo {

    private Long id;        //患者档案表主键
	
    private String patientInfoId;	//病人唯一标识

    private String patientInfoName;	 //病人姓名

    private Integer patientInfoSex;		//性别（1：男 2：女）

    private String patientInfoBirthday;	//出生年月

    private String patientInfoIdcard;	//身份证号

    private String patientInfoPhone;	//手机号

    private String patientInfoMarrStatus;	//婚姻状态（1：未婚 2：已婚）

    private String patientInfoNational;	//民族
    
    private String patientInfoCulture;	//文化程度

    private String patientInfoJob;	//职业

    private String patientInfoJobAddre;	//工作地址

    private String patientInfoAddre;	//家庭地址

    private String hospitalAreaCode;	//院区编码

    private String emerContName;	//紧急联系人姓名

    private String emerContRelation;	//紧急联系人关系

    private String emerContPhone;	//紧急联系人电话

    private String adminCreater;	//建党病人创建者
    
    private Date createTime;	//建档时间

    private Integer patientSource;      //患者来源 1,出院；2,门诊;3,在院;4体检 5 建档

    private String healthCardNo;    //医保卡号

    private String hosNo;           //住院号 / 门诊号 / 体检号

}