package com.leanin.ucenter.service.impl;

import com.leanin.domain.dto.AdminUserDto;
import com.leanin.domain.vo.MenuPermissionVo;
import com.leanin.domain.vo.UserJwt;
import com.leanin.ucenter.client.UserClient;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    ClientDetailsService clientDetailsService;

/*    @Autowired
    UserMapper userMapper;

    @Autowired
    MenuMapper menuMapper;*/

    @Autowired
    UserClient userClient;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //取出身份，如果身份为空说明没有认证
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //没有认证统一采用httpbasic认证，httpbasic中存储了client_id和client_secret，开始认证client_id和client_secret
        if (authentication == null) {
            ClientDetails clientDetails = clientDetailsService.loadClientByClientId(username);
            if (clientDetails != null) {
                //密码
                String clientSecret = clientDetails.getClientSecret();
                return new User(username, clientSecret,
                        AuthorityUtils.commaSeparatedStringToAuthorityList(""));
            }
        }
        if (StringUtils.isEmpty(username)) {
            return null;
        }
        //根据数据库查询用户信息
//        AdminUserDto adminUserDto = userMapper.findUserByWorkNum(username);
        AdminUserDto adminUserDto = userClient.findUserByWorkNum(username);
        if (adminUserDto == null) {
            //返回空给spring security表示用户不存在
            return null;
        }
        //根据用户id 获取用户权限
        List<MenuPermissionVo> menuPermissionDtos = userClient.findMenuListByUserId(adminUserDto.getAdminId());
        adminUserDto.setMenuPermissionVoList(menuPermissionDtos);//权限暂时用静态的

        //取出正确密码（hash值）
        String password = adminUserDto.getPassword();
        //从数据库获取权限
        List<MenuPermissionVo> permissions = adminUserDto.getMenuPermissionVoList();
        if (permissions == null) {
            permissions = new ArrayList<>();
        }
        List<String> user_permission = new ArrayList<>();
        permissions.forEach(item -> user_permission.add(item.getMenuIdentify()));//item.getMenuIdentify();
        //添加权限
        String user_permission_string = StringUtils.join(user_permission.toArray(), ",");
        UserJwt userDetails = new UserJwt(username,
                password,
                AuthorityUtils.commaSeparatedStringToAuthorityList(user_permission_string));
        userDetails.setId(adminUserDto.getAdminId());
        userDetails.setName(adminUserDto.getAdminName());       //用户名称
        userDetails.setWardCode(adminUserDto.getWardCode());
        userDetails.setHospitalName(adminUserDto.getHospitalName());
        return userDetails;
    }
}

