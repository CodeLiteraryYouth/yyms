package com.leanin.domain.vo;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
public class UserRoleVo {

    private Long id;  //用户角色关联表id

    private Long userId;   //用户Id

    private Long roleId;   //角色id

    private Date createTime; //创建时间

    private String creator;   //创建者




}
