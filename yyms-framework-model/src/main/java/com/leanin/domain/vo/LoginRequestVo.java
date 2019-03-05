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

    String username;        //工号
    String password;        //密码

}
