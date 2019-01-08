package com.leanin.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
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
@Entity
@Table(name = "leanin_disease_info")
public class DiseaseInfo implements Serializable{
    @Id
	private String diseaseNum;	//疾病单号
	
	private String diseaseName;	//疾病名称
	
	private String diseaseCode;	//疾病编码
	
}
