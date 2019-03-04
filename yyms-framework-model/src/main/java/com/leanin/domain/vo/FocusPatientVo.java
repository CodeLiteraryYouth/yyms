package com.leanin.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 关注病人信息
 * @author Administrator
 *
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class FocusPatientVo {
	
    private Long focusId;	//关注的病人主键

    private String patientId;	//病人唯一标识

    private String patientName;		//病人姓名

    private Integer patientSex;		//病人性别

    private String patientBirthday;		//病人生日

    private String patientPhone;	//病人手机号

    private String patientIdCard;	//身份证号

    private Integer focusStatus;	//病人状态（（-1：标记死亡 0：取消关注 1：已关注））

    private Integer patientSource;	//病人来源

    private Long userId;            //关注患者的用户



}