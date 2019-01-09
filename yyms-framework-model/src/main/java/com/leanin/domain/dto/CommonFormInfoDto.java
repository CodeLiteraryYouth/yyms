package com.leanin.domain.dto;

import com.leanin.domain.vo.FormInfoVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * 共享表单信息
 * @author Administrator
 *
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CommonFormInfoDto {

	private Long wardId;	//病区ID
	
	private String wardCode;	//病区编码
	
	private String wardName;	//病区名称
	
	private List<FormInfoVo> formList;	//该病区对应的表单数据
}
