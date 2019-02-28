package com.leanin.oauth.controller;

import com.leanin.api.auth.UserControllerApi;
import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.vo.AdminUserVo;
import com.leanin.oauth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController implements UserControllerApi{

    @Autowired
    UserService userService;

    @Override
    @PostMapping("/addUser")
    public DataOutResponse addUser(@RequestBody AdminUserVo adminUserVo) {
        return userService.addUser(adminUserVo);
    }

    @Override
    public DataOutResponse delUser(Long adminUserId) {
        return userService.delUser(adminUserId);
    }

    @Override
    public DataOutResponse updateUser(AdminUserVo adminUserVo) {
        return userService.updateUser(adminUserVo);
    }

    @Override
    public DataOutResponse findUserById(Long adminId) {
        return userService.findUserById(adminId);
    }

    @Override
    public DataOutResponse findUserPage(int currentPage, int pageSize, String NameOrNum) {
        return userService.findUserPage(currentPage,pageSize,NameOrNum);
    }
}
