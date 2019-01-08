package com.leanin.api.test;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api(value="测试接口",description = "测试配置管理接口，提供数据模型的管理、查询接口")
public interface TestApi {

   //restful风格传参
    @ApiOperation("测试接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param1", value = "页码", required = true, paramType = "path", dataType = "int"),
            @ApiImplicitParam(name = "param2", value = "每页记录数", required = true, paramType = "path", dataType = "int")
    })
    public String test(Integer param1, Integer param2);

    //普通传参方式
    @ApiOperation("测试接口二")
    public String test2(int param1,int param2);
}
