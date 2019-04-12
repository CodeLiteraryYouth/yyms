package com.leanin.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

/**
 * 角色菜单中间表
 * @author Administrator
 *
 */
@Data
@ToString
@Entity
@Table(name = "leanin_role_menu_permission")
public class RoleMenuInfoVo {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;	//中间表主键

    @Column(name = "role_id")
    private Long roleId;	//角色主键

    @Column(name = "menu_id")
    private Long permissionId;	//菜单主键

    @Column(name = "create_time")
    private Date createTime;

}