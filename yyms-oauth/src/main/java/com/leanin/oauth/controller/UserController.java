package com.leanin.oauth.controller;

import com.leanin.api.auth.UserControllerApi;
import com.leanin.domain.dto.AdminUserDto;
import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.vo.AdminUserVo;
import com.leanin.domain.vo.LoginRequestVo;
import com.leanin.oauth.service.UserService;
import com.leanin.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user")
public class UserController extends BaseController implements UserControllerApi{

    @Autowired
    UserService userService;

    @Override
    @PreAuthorize("hasAnyAuthority('root','addUser')")
    @PostMapping("/addUser")
    public DataOutResponse addUser(@RequestBody AdminUserVo adminUserVo) {
        return userService.addUser(adminUserVo,request);
    }

    /**
     *
     * @param adminUserId 主键
     * @param status 1 在用 2删除 3禁用
     * @return
     */
    @Override
    @PreAuthorize("hasAnyAuthority('root','delUser')")
    @GetMapping("/uptUserStatus")
    public DataOutResponse uptUserStatus(@RequestParam("adminUserId") Long adminUserId,@RequestParam("status") Integer status) {
        return userService.uptUserStatus(adminUserId,status);
    }

    @Override
//    @PreAuthorize("hasAnyAuthority('root','updateUser')")
    @PutMapping("/updateUser")
    public DataOutResponse updateUser(@RequestBody AdminUserVo adminUserVo) {
        return userService.updateUser(adminUserVo,request);
    }

    @Override
//    @PreAuthorize("hasAnyAuthority('root','findUser')")
    @GetMapping("/findUserById")
    public DataOutResponse findUserById(@RequestParam Long adminId) {
        return userService.findUserById(adminId);
    }

    @Override
    @PreAuthorize("hasAnyAuthority('root','findUser')")
    @GetMapping("/findUserPage")
    public DataOutResponse findUserPage(@RequestParam int currentPage,@RequestParam int pageSize, String userName,String workNum) {
        return userService.findUserPage(currentPage,pageSize,userName,workNum);
    }

    @Override
    @PreAuthorize("hasAnyAuthority('root','findUser')")
    @GetMapping("/findAllUser")
    public DataOutResponse findAllUser() {
        return userService.findAllUser();
    }


    @Override
    @PreAuthorize("hasAnyAuthority('root','findUser')")
    @GetMapping("/findUserName")
    public String findUserName(Long adminId) {
        return userService.findUserName(adminId);
    }

    @GetMapping("/findUserByWorkNum")
    public AdminUserDto findUserByWorkNum(@RequestParam("username")String username){
        return userService.findUserByWorkNum(username);
    }

    @PostMapping("/updatePassWord")
    public DataOutResponse updatePassWord(@RequestBody LoginRequestVo loginRequestVo){
        return userService.updatePassWord(loginRequestVo);
    }

    @GetMapping("/updatePhone")
    public DataOutResponse updatePhone(@RequestParam("userId") Long userId,@RequestParam("phone")String phone,@RequestParam("checkCode")String checkCode){
        return userService.updatePhone(userId,phone,checkCode);
    }

    @GetMapping("/sendCheckCode")
    public DataOutResponse sendCheckCode(@RequestParam("phone") String phone){
        return userService.sendCheckCode(phone);
    }



}
