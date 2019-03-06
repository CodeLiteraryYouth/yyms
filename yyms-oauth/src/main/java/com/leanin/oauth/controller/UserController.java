package com.leanin.oauth.controller;

import com.leanin.api.auth.UserControllerApi;
import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.vo.AdminUserVo;
import com.leanin.oauth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @GetMapping("/uptUserStatus")
    public DataOutResponse uptUserStatus(@RequestParam Long adminUserId,@RequestParam Integer status) {
        return userService.uptUserStatus(adminUserId,status);
    }

    @Override
    @PutMapping("/updateUser")
    public DataOutResponse updateUser(@RequestBody AdminUserVo adminUserVo) {
        return userService.updateUser(adminUserVo);
    }

    @Override
    @GetMapping("/findUserById")
    public DataOutResponse findUserById(@RequestParam Long adminId) {
        return userService.findUserById(adminId);
    }

    @Override
    @GetMapping("/findUserPage")
    public DataOutResponse findUserPage(@RequestParam int currentPage,@RequestParam int pageSize, String userName,String workNum) {
        return userService.findUserPage(currentPage,pageSize,userName,workNum);
    }

    @Override
    @GetMapping("/findAllUser")
    public DataOutResponse findAllUser() {
        return userService.findAllUser();
    }


    @Override
    @GetMapping("/findUserName")
    public String findUserName(Long adminId) {
        return userService.findUserName(adminId);
    }


}
