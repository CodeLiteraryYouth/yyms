package com.leanin.oauth.controller;

import com.leanin.api.auth.RoleControllerApi;
import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.vo.AdminUserVo;
import com.leanin.domain.vo.RoleInfoVo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/role")
public class RoleController implements RoleControllerApi{

    @Override
    public DataOutResponse addRole(RoleInfoVo roleInfoVo) {
        return null;
    }

    @Override
    public DataOutResponse delRole(Long roleId) {
        return null;
    }

    @Override
    public DataOutResponse updateRole(RoleInfoVo roleInfoVo) {
        return null;
    }

    @Override
    public DataOutResponse findRoleById(Long roleId) {
        return null;
    }

    @Override
    public DataOutResponse findRolePage(int currentPage, int pageSize, String roleName) {
        return null;
    }
}
