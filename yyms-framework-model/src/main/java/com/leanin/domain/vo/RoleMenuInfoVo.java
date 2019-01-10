package com.leanin.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 角色菜单中间表
 * @author Administrator
 *
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RoleMenuInfoVo {
	
    private Long id;	//中间表主键

    private Long roleId;	//角色主键

    private Long permissionId;	//菜单主键

}