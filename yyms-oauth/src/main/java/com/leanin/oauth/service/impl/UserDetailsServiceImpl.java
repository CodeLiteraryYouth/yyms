package com.leanin.oauth.service.impl;

import com.leanin.domain.dto.AdminUserDto;
import com.leanin.domain.vo.MenuPermissionVo;
import com.leanin.domain.vo.UserJwt;
import com.leanin.oauth.mapper.MenuMapper;
import com.leanin.oauth.mapper.UserMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    ClientDetailsService clientDetailsService;

    @Autowired
    UserMapper userMapper;

    @Autowired
    MenuMapper menuMapper;


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
        AdminUserDto adminUserDto = userMapper.findUserByWorkNum(username);
        if (adminUserDto == null) {
            //返回空给spring security表示用户不存在
            return null;
        }
//        adminUserDto.setWorkNum("leanin");
//        adminUserDto.setPassword(new BCryptPasswordEncoder().encode("123"));

        //根据用户id 获取用户权限
        List<MenuPermissionVo> menuPermissionDtos = menuMapper.findMenuListByUserId(adminUserDto.getAdminId());
        adminUserDto.setMenuPermissionVoList(menuPermissionDtos);//权限暂时用静态的

        //取出正确密码（hash值）
        String password = adminUserDto.getPassword();
        //用户权限，这里暂时使用静态数据，最终会从数据库读取
        //从数据库获取权限
//        List<XcMenu> permissions = userext.getPermissions();
        List<MenuPermissionVo> permissions = adminUserDto.getMenuPermissionVoList();
        if (permissions == null) {
            permissions = new ArrayList<>();
        }
        List<String> user_permission = new ArrayList<>();
        permissions.forEach(item -> user_permission.add(item.getMenuIdentify()));/*item.getMenuIdentify()*/
        //使用静态的权限表示用户所拥有的权限
//        user_permission.add("course_get_baseinfo");//查询课程信息
//        user_permission.add("course_pic_list");//图片查询
        String user_permission_string = StringUtils.join(user_permission.toArray(), ",");
        UserJwt userDetails = new UserJwt(username,
                password,
                AuthorityUtils.commaSeparatedStringToAuthorityList(user_permission_string));
        userDetails.setId(adminUserDto.getAdminId());
        userDetails.setName(adminUserDto.getAdminName());//用户名称
       /* UserDetails userDetails = new org.springframework.security.core.userdetails.User(username,
                password,
                AuthorityUtils.commaSeparatedStringToAuthorityList(""));*/
//                AuthorityUtils.createAuthorityList("course_get_baseinfo","course_get_list"));
        return userDetails;
    }
}

