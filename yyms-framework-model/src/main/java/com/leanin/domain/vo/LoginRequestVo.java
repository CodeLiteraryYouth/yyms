package com.leanin.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

//用户登录
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LoginRequestVo implements Serializable {


    private Long userId;            //用户id
    private String username;        //工号
    private String password;        //密码
    private String checkCode;       //验证码
    private String newPassword;     //新密码

}
