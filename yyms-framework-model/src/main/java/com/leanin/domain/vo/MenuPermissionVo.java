package com.leanin.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * 菜单信息
 * @author Administrator
 *
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MenuPermissionVo {
	
    private Long id;	//菜单主键	

    private String menuName;	//菜单名称

    private Long menuPid;	//父ID

    private String menuRemark;	//菜单备注

    private Date menuCreatetime;	//菜单创建时间

    private Long menuIsDelete;	//是否删除

    private Long menuState;	//是否注销

    private Long menuType;	//权限类型

    private String menuIdentify;	//权限标识

    private Long menuSort;	//菜单排序

    private String menuIco;	//菜单图片

    private String menuUrl;	//菜单路径

}