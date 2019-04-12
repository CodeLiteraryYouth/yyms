package com.leanin.api.auth;

import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.vo.MenuPermissionVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "权限接口",description = "权限操作接口")
public interface MenuControllerApi {

    @ApiOperation("添加")
    public DataOutResponse addMenu(MenuPermissionVo menu);

    @ApiOperation("删除")
    public DataOutResponse delMenu(String ids);

    @ApiOperation("修改")
    public DataOutResponse updateMenu(MenuPermissionVo menu);

    @ApiOperation("修改")
    public DataOutResponse findByPid(Integer pid);
}
