package com.leanin.domain.dto;

import com.leanin.domain.vo.RulesInfoVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * 共享规则信息 
 * @author Administrator
 *
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CommonRulesInfoDto {

	private Long wardId;	//病区ID
	
	private String wardCode;	//病区编码
	
	private String wardName;	//病区名称
	
	private List<RulesInfoVo> rulesList;	//该病区对应的规则数据
}
