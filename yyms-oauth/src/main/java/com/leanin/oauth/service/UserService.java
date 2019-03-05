package com.leanin.oauth.service;

import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.vo.AdminUserVo;

public interface UserService {

    DataOutResponse addUser(AdminUserVo adminUserVo);

    DataOutResponse uptUserStatus(Long adminUserId,Integer status);

    DataOutResponse updateUser(AdminUserVo adminUserVo);

    DataOutResponse findUserById(Long adminId);

    DataOutResponse findUserPage(int currentPage, int pageSize, String userName,String workNum);
}
