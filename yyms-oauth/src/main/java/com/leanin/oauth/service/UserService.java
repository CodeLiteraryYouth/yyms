package com.leanin.oauth.service;

import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.vo.AdminUserVo;

public interface UserService {

    DataOutResponse addUser(AdminUserVo adminUserVo);

    DataOutResponse delUser(Long adminUserId);

    DataOutResponse updateUser(AdminUserVo adminUserVo);

    DataOutResponse findUserById(Long adminId);

    DataOutResponse findUserPage(int currentPage, int pageSize, String nameOrNum);
}
