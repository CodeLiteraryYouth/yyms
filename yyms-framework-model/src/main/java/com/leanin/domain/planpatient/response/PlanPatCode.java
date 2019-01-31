package com.leanin.domain.planpatient.response;

import com.leanin.model.response.ResultCode;

public enum  PlanPatCode implements ResultCode {

    INVALID_PARAM(false,500,"患者信息不存在！"),
    SUCCESS(true,200,"操作成功！"),
    FAIL(false,400,"操作失败！"),
    UNAUTHENTICATED(false,1000,"此操作需要登陆系统！"),
    UNAUTHORISE(false,403,"权限不足，无权操作！"),
    Data_ERROR(false,502,"ji！"),
    FEIGN_ERROR(false,501,"服务器异常请联系管理员");
    //    private static ImmutableMap<Integer, CommonCode> codes ;
    //操作是否成功
    boolean success;
    //操作代码
    int status;
    //提示信息
    String message;

    private PlanPatCode(boolean success, int status, String message){
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
