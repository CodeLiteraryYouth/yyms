package com.leanin.model.response;

import lombok.ToString;

/**
 * @Author: mrt.
 * @Description:
 * @Date:Created in 2018/1/24 18:33.
 * @Modified By:
 */

@ToString
public enum CommonCode implements ResultCode{
    INVALID_PARAM(false,500,"非法参数！"),
    SUCCESS(true,200,"操作成功！"),
    FAIL(false,400,"操作失败！"),
    UNAUTHENTICATED(false,1000,"此操作需要登陆系统！"),
    UNAUTHORISE(false,403,"权限不足，无权操作！"),
    SERVER_ERROR(false,9999,"抱歉，系统繁忙，请稍后重试！"),
    FEIGN_ERROR(false,501,"调取患者信息异常");
//    private static ImmutableMap<Integer, CommonCode> codes ;
    //操作是否成功
    boolean success;
    //操作代码
    int status;
    //提示信息
    String message;

    private CommonCode(boolean success,int status, String message){
        this.success = success;
        this.status = status;
        this.message = message;
    }

    @Override
    public boolean success() {
        return success;
    }
    @Override
    public int status() {
        return status;
    }

    @Override
    public String message() {
        return message;
    }


}
