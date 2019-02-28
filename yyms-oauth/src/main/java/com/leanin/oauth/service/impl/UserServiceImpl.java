package com.leanin.oauth.service.impl;

import com.leanin.domain.dto.AdminUserDto;
import com.leanin.domain.plan.response.AuthCode;
import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.response.ReturnFomart;
import com.leanin.domain.vo.AdminUserVo;
import com.leanin.domain.vo.UserRoleVo;
import com.leanin.exception.ExceptionCast;
import com.leanin.oauth.mapper.UserMapper;
import com.leanin.oauth.mapper.UserRoleMapper;
import com.leanin.oauth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    UserRoleMapper userRoleMapper;

    @Override
    @Transactional
    public DataOutResponse addUser(AdminUserVo adminUserVo) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        AdminUserDto user = userMapper.findUserByWorkNum(adminUserVo.getWorkNum());
        if (user != null) {
            return ReturnFomart.retParam(300, "用户已存在请勿重复添加");
        }
        adminUserVo.setPassword(bCryptPasswordEncoder.encode(adminUserVo.getPassword()));
        userMapper.addUser(adminUserVo);
        AdminUserDto userByWorkNum = userMapper.findUserByWorkNum(adminUserVo.getWorkNum());
        List<Long> roleIds = adminUserVo.getRoleIds();
        if (roleIds.size() > 0) {
            for (Long roleId : roleIds) {
                UserRoleVo userRoleVo = userRoleMapper.findByUidAndRid(userByWorkNum.getAdminId(), roleId);
                if (userRoleVo == null) {
                    userRoleVo.setId(null);
                    userRoleVo.setUserId(userByWorkNum.getAdminId());
                    userRoleVo.setRoleId(roleId);
                    userRoleVo.setCreateTime(new Date());
                    userRoleVo.setCreator(null);
                    userRoleMapper.addUserRole(userRoleVo);
                }else {
                    ExceptionCast.cast(AuthCode.ROLE_REPETITION);
                }

            }
        }
        return ReturnFomart.retParam(200, "用户添加成功");
    }

    @Override
    @Transactional
    public DataOutResponse delUser(Long adminUserId) {
        AdminUserVo adminUserVo=userMapper.findUserId(adminUserId);
        if (adminUserVo == null){
            return ReturnFomart.retParam(400, "用户不存在");
        }
        adminUserVo.setAdminState(2);
        userMapper.updateUser(adminUserVo);
        return ReturnFomart.retParam(400, "删除成功");
    }

    @Override
    @Transactional
    public DataOutResponse updateUser(AdminUserVo adminUserVo) {
        AdminUserDto user = userMapper.findUserByWorkNum(adminUserVo.getWorkNum());
        if (user == null) {
            return ReturnFomart.retParam(300, "用户不存在");
        }
        List<Long> roleIds = adminUserVo.getRoleIds();
        if (roleIds.size() > 0) {
            for (Long roleId : roleIds) {
                UserRoleVo userRoleVo = userRoleMapper.findByUidAndRid(adminUserVo.getAdminId(), roleId);
                if (userRoleVo == null) {
                    userRoleVo.setId(null);
                    userRoleVo.setUserId(adminUserVo.getAdminId());
                    userRoleVo.setRoleId(roleId);
                    userRoleVo.setCreateTime(new Date());
                    userRoleVo.setCreator(null);
                    userRoleMapper.addUserRole(userRoleVo);
                }else {
                    ExceptionCast.cast(AuthCode.ROLE_REPETITION);
                }
            }
        }
        userMapper.updateUser(adminUserVo);
        return ReturnFomart.retParam(200, "用户修改成功");
    }

    @Override
    public DataOutResponse findUserById(Long adminId) {
        return null;
    }

    @Override
    public DataOutResponse findUserPage(int currentPage, int pageSize, String nameOrNum) {
        return null;
    }
}
