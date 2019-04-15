package com.leanin.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

/**
 * 角色信息
 * @author Administrator
 *
 */
@Data
@ToString
@Entity
@Table(name = "leanin_role")
public class RoleInfoVo {

    @Id
    @Column(name = "role_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roleId;	//角色ID

    @Column(name = "role_name")
    private String roleName;	//角色名称

    @Column(name = "role_desc")
    private String roleDesc;	//角色描述

    @Column(name = "role_create_time")
    private Date roleCreateTime;	//角色创建时间

    @Column(name = "role_creater")
    private Long roleCreater;	//角色创建者

    @Column(name = "role_status")
    private Integer roleStatus;	//角色状态（-1：已注销 0：未注销）

    @Column(name = "role_remark")
    private String roleRemark;	//备注

    @Column(name = "hospital_area_code")
    private String hospitalAreaCode;	//院区编码

 
}