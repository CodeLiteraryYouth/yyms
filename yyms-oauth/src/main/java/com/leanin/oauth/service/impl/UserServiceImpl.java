package com.leanin.oauth.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
                    userRoleVo =new UserRoleVo();
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
    public DataOutResponse uptUserStatus(Long adminUserId,Integer status) {
        AdminUserVo adminUserVo=userMapper.findUserId(adminUserId);
        if (adminUserVo == null){
            return ReturnFomart.retParam(400, "用户不存在");
        }
        adminUserVo.setAdminState(status);
        userMapper.updateUser(adminUserVo);
        return ReturnFomart.retParam(200, "修改成功");
    }

    @Override
    @Transactional
    public DataOutResponse updateUser(AdminUserVo adminUserVo) {
        AdminUserVo user = userMapper.findUserId(adminUserVo.getAdminId());
        if (user == null) {
            return ReturnFomart.retParam(300, "用户不存在");
        }
        List<Long> roleIds = adminUserVo.getRoleIds();
        if (roleIds.size() > 0) {
            for (Long roleId : roleIds) {
                UserRoleVo userRoleVo = userRoleMapper.findByUidAndRid(adminUserVo.getAdminId(), roleId);
                if (userRoleVo == null) {
                    userRoleVo =new UserRoleVo();
                    userRoleVo.setId(null);
                    userRoleVo.setUserId(adminUserVo.getAdminId());
                    userRoleVo.setRoleId(roleId);
                    userRoleVo.setCreateTime(new Date());
                    userRoleVo.setCreator(null);
                    userRoleMapper.addUserRole(userRoleVo);
                }else {

//                    ExceptionCast.cast(AuthCode.ROLE_REPETITION);
                }
            }
        }
        userMapper.updateUser(adminUserVo);
        return ReturnFomart.retParam(200, "用户修改成功");
    }

    @Override
    public DataOutResponse findUserById(Long adminId) {
        AdminUserVo adminUserVo = userMapper.findUserId(adminId);
        return ReturnFomart.retParam(200, adminUserVo);
    }

    @Override
    public DataOutResponse findUserPage(int currentPage, int pageSize, String userName,String workNum) {
        if (currentPage < 1){
            currentPage = 1;
        }
        if (pageSize < 1){
            pageSize = 10;
        }
        PageHelper.startPage(currentPage,pageSize);
        Page<AdminUserVo> page = (Page<AdminUserVo>) userMapper.findUserPage(userName,workNum);

        Map dataMap =new HashMap();
        dataMap.put("totalCount",page.getTotal());
        dataMap.put("list",page.getResult());
        return ReturnFomart.retParam(200, dataMap);
    }
}
