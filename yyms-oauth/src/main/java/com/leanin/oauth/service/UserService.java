package com.leanin.oauth.service;

import com.leanin.domain.dao.UserDao;
import com.leanin.domain.dto.AdminUserDto;
import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.vo.AdminUserVo;
import com.leanin.domain.vo.LoginRequestVo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface UserService {

    DataOutResponse addUser(AdminUserVo adminUserVo, HttpServletRequest request);

    DataOutResponse uptUserStatus(Long adminUserId,Integer status);

    DataOutResponse updateUser(AdminUserVo adminUserVo,HttpServletRequest request);

    DataOutResponse findUserById(Long adminId);

    DataOutResponse findUserPage(int currentPage, int pageSize, String userName,String workNum);

    String findUserName(Long adminId);

    DataOutResponse findAllUser();

    AdminUserDto findUserByWorkNum(String username);

    DataOutResponse updatePassWord(LoginRequestVo loginRequestVo);

    DataOutResponse sendCheckCode(String phone);

    DataOutResponse updatePhone(Long userId, String phone, String checkCode);

    DataOutResponse findByWard(Long wardId);

    DataOutResponse addUserQ(UserDao userDao,HttpServletRequest request);

    DataOutResponse delUserQ(Long userId, Integer status);

    DataOutResponse updateUserQ(UserDao userDao);

    DataOutResponse updateLastLoginTime(Long userId);
}
