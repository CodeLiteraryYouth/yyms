package com.leanin.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.io.Serializable;
import java.util.Collection;
@Data
@ToString
public class UserJwt extends User implements Serializable {

    private Long id;                // 用户id
    private String name;            //用户名称
    private String wardCode;        //用户科室
    private String hospitalName;    //医院名称
//    private String userpic;
//    private String utype;
//    private String companyId;

    public UserJwt(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

}
