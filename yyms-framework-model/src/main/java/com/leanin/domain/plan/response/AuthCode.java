package com.leanin.domain.plan.response;

import com.google.common.collect.ImmutableMap;
import com.leanin.model.response.ResultCode;
import io.swagger.annotations.ApiModelProperty;
import lombok.ToString;


/**
 * Created by admin on 2018/3/5.
 */
@ToString
public enum AuthCode implements ResultCode {
    AUTH_USERNAME_NONE(false,23001,"请输入账号！"),
    ROLE_REPETITION(false,23000,"该用户角色已存在！"),
    AUTH_PASSWORD_NONE(false,23002,"请输入密码！"),
    AUTH_VERIFYCODE_NONE(false,23003,"请输入验证码！"),
    AUTH_ACCOUNT_NOTEXISTS(false,23004,"账号不存在！"),
    AUTH_CREDENTIAL_ERROR(false,23005,"账号或密码错误！"),
    AUTH_LOGIN_ERROR(false,23006,"登陆过程出现异常请尝试重新操作！"),
    AUTH_LOGIN_APPLYTOKEN_FAIL(false,23007,"申请令牌失败！"),
    AUTH_LOGIN_TOKEN_SAVEFAIL(false,23008,"存储令牌失败！"),
    AUTH_LOGOUT_FAIL(false,23009,"退出失败！");

    //操作是否成功
    boolean success;
    //操作代码
    int status;
    //提示信息
    String message;

    private AuthCode(boolean success, int status, String message){
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
