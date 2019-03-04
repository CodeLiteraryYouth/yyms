package com.leanin.api.auth;

import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.vo.AdminUserVo;
import com.leanin.domain.vo.RoleInfoVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "角色管理",description = "劫色接口")
public interface RoleControllerApi {

    @ApiOperation("添加角色")
    public DataOutResponse addRole(RoleInfoVo roleInfoVo);

    @ApiOperation("删除角色")
    public DataOutResponse delRole(Long roleId);

    @ApiOperation("修改角色")
    public DataOutResponse updateRole(RoleInfoVo roleInfoVo);

    @ApiOperation("根据id查询角色信息")
    public DataOutResponse findRoleById(Long roleId);

    @ApiOperation("分页查询角色信息")
    public DataOutResponse findRolePage(int currentPage,int pageSize,String roleName);
}
