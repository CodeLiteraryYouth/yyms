package com.leanin.api.auth;

import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.vo.LoginRequestVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * Created by Administrator.
 */
@Api(value = "用户认证",description = "用户认证接口")
public interface AuthControllerApi {
    @ApiOperation("登录")
    public DataOutResponse login(LoginRequestVo loginRequest);

    @ApiOperation("退出")
    public DataOutResponse logout();

    @ApiOperation("查询用户jwt令牌")
    public DataOutResponse userjwt();
}
