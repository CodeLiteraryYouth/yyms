package com.leanin.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * 添加角色传输实体类
 * @author Administrator
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AddRoleDto {

    private Long roleId;	//角色ID

    private String roleName;	//角色名称

    private String roleDesc;	//角色描述

    private Date roleCreateTime;	//角色创建时间

    private Long roleCreater;	//角色创建者

    private Integer roleStatus;	//角色状态（-1：已注销 0：未注销）

    private String roleRemark;	//备注

    private String hospitalAreaCode;	//院区编码

    private String menuId;  //多个菜单ID以逗号隔开
}
