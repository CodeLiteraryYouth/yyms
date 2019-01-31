package com.leanin.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * 菜单信息
 * @author Administrator
 *
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MenuPermissionDto implements Comparable<MenuPermissionDto> {

    private Long id;         //主键ID
    private String menuName;		//菜单名称
    private Long menuPid;			//父类ID
    private String menuRemark;		//菜单备注
    private String menuCreateTime;		//菜单创建时间
    private int isDlete;   //是否删除
    private int menuState;  //是否备注
    private int menuType;  //权限类型
    private String menuIdentify;  //权限标识
    private Integer menuSort;  //权限排序
    private String menuIco;  //权限图片
    private String menuUrl;  //菜单路径
    private List<MenuPermissionDto> children=new LinkedList<>();

    @Override
    public int compareTo(MenuPermissionDto o) {
        return this.getMenuSort().compareTo(o.getMenuSort());
    }
}