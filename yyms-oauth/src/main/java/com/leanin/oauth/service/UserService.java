package com.leanin.oauth.service;

import com.leanin.domain.dto.AdminUserDto;
import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.vo.AdminUserVo;

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
}
