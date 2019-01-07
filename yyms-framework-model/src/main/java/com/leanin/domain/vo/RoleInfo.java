package com.leanin.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * 角色信息
 * @author Administrator
 *
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RoleInfo {
	
    private Long roleId;	//角色ID

    private String roleName;	//角色名称

    private String roleDesc;	//角色描述

    private Date roleCreateTime;	//角色创建时间

    private String roleCreater;	//角色创建者

    private Integer roleStatus;	//角色状态（-1：已注销 0：未注销）

    private String roleRemark;	//备注

    private String hospitalAreaCode;	//院区编码

 
}