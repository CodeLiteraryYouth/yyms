package com.leanin.service.impl;

import com.leanin.domain.dto.AdminUserDto;
import com.leanin.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 用户Service
 * @author Administrator
 */
@Service
public class AdminUserDetailsService implements UserDetailsService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AdminUserDto user = userMapper.findUserByUserCode(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return user;
    }
}
