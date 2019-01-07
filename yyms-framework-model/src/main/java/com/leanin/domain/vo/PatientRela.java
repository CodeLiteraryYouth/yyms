package com.leanin.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 病人联系人
 * @author Administrator
 *
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PatientRela {
	
    private Long patientRelaId;	//联系人主键

    private String patientRelaName;	//联系人姓名

    private String patientRela;	//与病人关系

    private String patientRelaPhone;	//联系人手机号码

    private String patientId;	//病人唯一标识

    private Integer patientRelaState;	//1：默认联系人  0:普通联系人

}