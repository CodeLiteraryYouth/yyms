package com.leanin.oauth.exception;

import com.leanin.exception.ExceptionCatch;
import com.leanin.model.response.CommonCode;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice//控制器增强
public class MenuException extends ExceptionCatch {

    static {
        //定义异常类型所对应的错误代码
        builder.put(AccessDeniedException.class, CommonCode.UNAUTHORISE);
    }

}
