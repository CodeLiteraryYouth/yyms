package com.leanin.oauth.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.leanin.domain.dao.UserDao;
import com.leanin.domain.dto.AdminUserDto;
import com.leanin.domain.dto.RoleInfoDto;
import com.leanin.domain.plan.response.AuthCode;
import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.response.ReturnFomart;
import com.leanin.domain.vo.AdminUserVo;
import com.leanin.domain.vo.UserRoleVo;
import com.leanin.exception.ExceptionCast;
import com.leanin.oauth.mapper.UserMapper;
import com.leanin.oauth.mapper.UserRoleMapper;
import com.leanin.oauth.service.UserService;
import com.leanin.utils.LyOauth2Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
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

    private LyOauth2Util.UserJwt getUser(HttpServletRequest request) {
        LyOauth2Util lyOauth2Util = new LyOauth2Util();
        LyOauth2Util.UserJwt userJwt = lyOauth2Util.getUserJwtFromHeader(request);
        return userJwt;
    }

    @Override
    @Transactional
    public DataOutResponse addUser(AdminUserVo adminUserVo, HttpServletRequest request) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        AdminUserDto user = userMapper.findUserByWorkNum(adminUserVo.getWorkNum());
        if (user != null) {
            return ReturnFomart.retParam(300, "用户已存在请勿重复添加");
        }
        adminUserVo.setPassword(bCryptPasswordEncoder.encode(adminUserVo.getPassword()));
        userMapper.addUser(adminUserVo);
        AdminUserDto userByWorkNum = userMapper.findUserByWorkNum(adminUserVo.getWorkNum());
        List<Long> roleIds = adminUserVo.getRoleIds();

        LyOauth2Util.UserJwt userJwt = getUser(request);
        if (roleIds.size() > 0) {
            UserRoleVo userRoleVo =new UserRoleVo();
            for (Long roleId : roleIds) {
//                UserRoleVo userRoleVo = userRoleMapper.findByUidAndRid(userByWorkNum.getAdminId(), roleId);
//                if (userRoleVo == null) {
//                    userRoleVo = new UserRoleVo();
                    userRoleVo.setId(null);
                    userRoleVo.setUserId(userByWorkNum.getAdminId());
                    userRoleVo.setRoleId(roleId);
                    userRoleVo.setCreateTime(new Date());
                    userRoleVo.setCreator(userJwt.getId());
                    userRoleMapper.addUserRole(userRoleVo);
//                } else {
//                    ExceptionCast.cast(AuthCode.ROLE_REPETITION);
//                }

            }
        }
        return ReturnFomart.retParam(200, "用户添加成功");
    }

    @Override
    @Transactional
    public DataOutResponse uptUserStatus(Long adminUserId, Integer status) {
        AdminUserVo adminUserVo = userMapper.findUserId(adminUserId);
        if (adminUserVo == null) {
            return ReturnFomart.retParam(400, "用户不存在");
        }
        adminUserVo.setAdminState(status);
        userMapper.updateUser(adminUserVo);
        return ReturnFomart.retParam(200, "修改成功");
    }

    @Override
    @Transactional
    public DataOutResponse updateUser(AdminUserVo adminUserVo, HttpServletRequest request) {
        AdminUserDto user = userMapper.findUserDtoId(adminUserVo.getAdminId());
        if (user == null) {
            return ReturnFomart.retParam(1010, "用户不存在");
        }
        List<Long> roleIds = adminUserVo.getRoleIds();
        List<RoleInfoDto> roleList = user.getRoleList();
        LyOauth2Util.UserJwt userJwt = getUser(request);
        if (roleIds.size() > 0) {//判断是否有角色
            for (Long roleId : roleIds) {
                UserRoleVo userRoleVo = userRoleMapper.findByUidAndRid(adminUserVo.getAdminId(), roleId);
                if (userRoleVo == null) {
                    userRoleVo = new UserRoleVo();
                    userRoleVo.setId(null);
                    userRoleVo.setUserId(adminUserVo.getAdminId());
                    userRoleVo.setRoleId(roleId);
                    userRoleVo.setCreateTime(new Date());
                    userRoleVo.setCreator(userJwt.getId());
                    userRoleMapper.addUserRole(userRoleVo);
                }
//                    ExceptionCast.cast(AuthCode.ROLE_REPETITION);
            }
        }
        for (RoleInfoDto roleInfoDto : roleList) {
            if (!roleList.contains(roleInfoDto.getRoleId())) {
                userRoleMapper.deleteByUidAndRid(user.getAdminId(), roleInfoDto.getRoleId());
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
    public DataOutResponse findUserPage(int currentPage, int pageSize, String userName, String workNum) {
        if (currentPage < 1) {
            currentPage = 1;
        }
        if (pageSize < 1) {
            pageSize = 10;
        }
        PageHelper.startPage(currentPage, pageSize);
        Page<AdminUserVo> page = (Page<AdminUserVo>) userMapper.findUserPage(userName, workNum);

        Map dataMap = new HashMap();
        dataMap.put("totalCount", page.getTotal());
        dataMap.put("list", page.getResult());
        return ReturnFomart.retParam(200, dataMap);
    }

    @Override
    public String findUserName(Long adminId) {
        return userMapper.findUserName(adminId);
    }

    @Override
    public DataOutResponse findAllUser() {
        return ReturnFomart.retParam(200, userMapper.findAllUser());
    }
}
