package com.leanin.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

/**
 * 菜单信息
 * @author Administrator
 *
 */
@Data
@ToString
@Table(name = "leanin_menu_permissions")
public class MenuPermissionVo {

	@Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;	//菜单主键	

    @Column(name = "menu_name")
    private String menuName;	//菜单名称

    @Column(name = "menu_pid")
    private Long menuPid;	//父ID

    @Column(name ="menu_remark" )
    private String menuRemark;	//菜单备注

    @Column(name ="menu_createtime" )
    private Date menuCreatetime;	//菜单创建时间

    @Column(name ="menu_isDelete" )
    private Long menuIsDelete;	//是否删除

    @Column(name = "menu_state" )
    private Long menuState;	//是否注销

    @Column(name = "menu_type")
    private Long menuType;	//权限类型

    @Column(name = "menu_identify")
    private String menuIdentify;	//权限标识

    @Column(name = "menu_sort" )
    private Long menuSort;	//菜单排序

    @Column(name = "menu_ico")
    private String menuIco;	//菜单图片

    @Column(name = "menu_url" )
    private String menuUrl;	//菜单路径

}