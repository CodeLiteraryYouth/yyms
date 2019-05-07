package com.leanin.ucenter.client;


import com.leanin.domain.dto.AdminUserDto;
import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.vo.MenuPermissionVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "yyms-oauth",fallback = UserClientFallback.class)//制定远程调用的服务名
public interface UserClient {

    @GetMapping("/user/findUserByWorkNum")
    public AdminUserDto findUserByWorkNum(@RequestParam("username")String username);

    @GetMapping("/menu/findMenuListByUserId")
    public List<MenuPermissionVo> findMenuListByUserId(@RequestParam("userId") Long userId);

    @GetMapping("/user/updateLastLoginTime")
    public DataOutResponse updateLastLoginTime(@RequestParam("userId")Long userId);
}
