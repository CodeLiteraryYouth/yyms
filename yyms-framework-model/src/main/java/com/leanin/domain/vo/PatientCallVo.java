package com.leanin.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 通话信息病人
 * @author Administrator
 *
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PatientCallVo {
	
    private Long callPatientId;	//通话信息主键

    private String callId;	//通话单号

    private Long patientId;	//病人唯一标识

    private Integer patientSource;	//病人来源

    private String areaCode;	//院区编码


}