package com.leanin.api.auth;

import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.vo.AdminUserVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "角色管理",description = "劫色接口")
public interface RoleControllerApi {

    @ApiOperation("添加角色")
    public DataOutResponse addUser(AdminUserVo adminUserVo);

    @ApiOperation("删除角色")
    public DataOutResponse delUser(Long adminUserId);

    @ApiOperation("修改角色")
    public DataOutResponse updateUser(AdminUserVo adminUserVo);

    @ApiOperation("根据id查询角色信息")
    public DataOutResponse findUserById(Long adminId);

    @ApiOperation("分页查询角色信息")
    public DataOutResponse findUserPage(int currentPage,int pageSize,String roleName);
}
