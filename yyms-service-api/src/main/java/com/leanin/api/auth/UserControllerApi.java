package com.leanin.api.auth;

import com.leanin.domain.dto.AdminUserDto;
import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.vo.AdminUserVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.List;


@Api(value = "用户信息",description = "用户信息接口")
public interface UserControllerApi {

    @ApiOperation("添加用户")
    public DataOutResponse addUser(AdminUserVo adminUserVo);

    @ApiOperation("修改用户状态")
    public DataOutResponse uptUserStatus(Long adminUserId,Integer status);

    @ApiOperation("修改用户")
    public DataOutResponse updateUser(AdminUserVo adminUserVo);

    @ApiOperation("根据id查询用户信息")
    public DataOutResponse findUserById(Long adminId);

    @ApiOperation("添加用户")
    public DataOutResponse findUserPage(int currentPage,int pageSize,String userName,String workNum);

    @ApiOperation("查询所有用户")
    public DataOutResponse findAllUser();


    @ApiOperation("查询用户名称")
    public String findUserName(Long adminId);
}
