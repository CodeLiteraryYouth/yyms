package com.leanin.oauth.controller;

import com.leanin.domain.dto.AddUserDto;
import com.leanin.domain.response.DataOutResponse;
import com.leanin.oauth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;

/**
 * @author Administrator
 */
@RestController
@RequestMapping(value="/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("findUserById")
    public DataOutResponse findUserById(@RequestParam Long userId) {
        return userService.findUserById(userId);
    }

    @GetMapping("findUserList")
    public DataOutResponse findUserList(@RequestParam int page,@RequestParam int pageSize,
                                        @RequestParam(required = false) String adminName,@RequestParam(required = false) String adminWorkNum) {
        System.out.println(adminName);
        return userService.findUserList(page,pageSize,adminName,adminWorkNum);
    }

    @PostMapping("addUserInfo")
    public DataOutResponse addUserInfo(@RequestBody AddUserDto userDto) {
        return userService.addUserInfo(userDto);
    }

    @PostMapping("updateUserInfo")
    public DataOutResponse updateUserInfo(@RequestBody AddUserDto userDto) {
        return userService.updateUserInfo(userDto);
    }

    @RequestMapping("principal")
    public Principal user(Principal user) {
        return user;
    }
}
