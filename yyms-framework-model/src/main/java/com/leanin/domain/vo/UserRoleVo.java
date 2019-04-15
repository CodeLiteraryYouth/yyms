package com.leanin.domain.vo;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Data
@ToString
@Table(name = "leanin_user_role")
public class UserRoleVo {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  //用户角色关联表id

    @Column(name = "user_id")
    private Long userId;   //用户Id

    @Column(name = "role_id")
    private Long roleId;   //角色id

    @Column(name = "create_t")
    private Date createTime; //创建时间

    @Column(name = "creator")
    private Long creator;   //创建者




}
