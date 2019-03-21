package com.leanin.utils;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by mrt on 2018/5/25.
 */
public class LyOauth2Util {

    public UserJwt getUserJwtFromHeader(HttpServletRequest request){
        Map jwtClaims = Oauth2Util.getJwtClaimsFromHeader(request);
        String id = jwtClaims.get("id").toString();
        if(jwtClaims == null || StringUtils.isEmpty(id)){
            return null;
        }
        UserJwt userJwt = new UserJwt();
        userJwt.setId(Long.parseLong(jwtClaims.get("id").toString()));
        userJwt.setName(jwtClaims.get("name").toString());
        userJwt.setWardCode(jwtClaims.get("wardCode").toString());
        userJwt.setHosName(jwtClaims.get("hosName").toString());
        return userJwt;
    }

    @Data
    public class UserJwt{
        private Long id;
        private String name;
        private String wardCode;
        private String hosName;
    }

}
