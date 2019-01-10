package com.leanin.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.io.Serializable;

/**
 * 疾病数据字典
 * @author Administrator
 *
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DiseaseInfoVo implements Serializable{

	private String diseaseNum;	//疾病单号
	
	private String diseaseName;	//疾病名称
	
	private String diseaseCode;	//疾病编码
	
}
