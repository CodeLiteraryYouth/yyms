package com.leanin.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 院区编码信息
 * @author Administrator
 *
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class HospitalAreaDto {
	
    private Long areaId;	//院区主键

    private String areaName;	//院区名称

    private String areaCode;	//院区编码

    private Integer areaStatus;	//院区状态

}