package com.leanin.ucenter.client;

import com.leanin.domain.dto.AdminUserDto;
import com.leanin.domain.vo.MenuPermissionVo;
import org.springframework.stereotype.Component;

import java.util.List;

//熔断器   当服务连接不上时 执行此方法
@Component
public class UserClientFallback implements UserClient {

    @Override
    public AdminUserDto findUserByWorkNum(String username) {
        return null;
    }

    @Override
    public List<MenuPermissionVo> findMenuListByUserId(Long userId) {
        return null;
    }
}
