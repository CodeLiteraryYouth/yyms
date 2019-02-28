package com.leanin.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * 管理员用户操作类
 * @author Administrator
 *
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AdminUserVo {

	private Long adminId;	//管理员ID
	
	private String adminName;	//管理员名称
	
	private String workNum;	//用户工号
	
	private String password;	//密码
	
	private String wardCode;	//用户科室编码
	
	private String jobTitle;	//用户职称
	
	private String adminDesc;	//用户自我介绍
	
	private String phone;	//用户手机号
	
	private String areaCode;	//院区编码
	
	private String idCard;	//身份证号
	
	private Integer sex;	//性别
	
	private String birthday;	//管理员出生日期
	
	private String organAscri;	//机构归属

	private Integer adminState;	//用户状态(-1:已注销 0：正在使用)
	
	private String remark;	//备注

	private List<Long> roleIds; 	//角色id集合
}