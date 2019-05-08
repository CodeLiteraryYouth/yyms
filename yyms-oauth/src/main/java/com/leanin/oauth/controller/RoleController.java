package com.leanin.oauth.controller;

import com.leanin.api.auth.RoleControllerApi;
import com.leanin.domain.request.RoleInfoVoReq;
import com.leanin.domain.response.DataOutResponse;
import com.leanin.oauth.service.RoleService;
import com.leanin.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/role")
public class RoleController extends BaseController implements RoleControllerApi{

    @Autowired
    RoleService roleService;

    @Override
//    @PreAuthorize("hasAnyAuthority('root','addRole')")
    @PostMapping("/addRole")
    public DataOutResponse addRole(@RequestBody RoleInfoVoReq RoleInfoVoReq) {
        return roleService.addRole(RoleInfoVoReq,request);
    }

    @Override
//    @PreAuthorize("hasAnyAuthority('root','delRole')")
    @GetMapping("/delRole")
    public DataOutResponse delRole(@RequestParam("roleIds") String roleIds) {
        return roleService.delRole(roleIds);
    }

    @Override
//    @PreAuthorize("hasAnyAuthority('root','updateRole')")
    @PutMapping("/updateRole")
    public DataOutResponse updateRole(@RequestBody RoleInfoVoReq roleInfoVoReq) {
        return roleService.updateRole(roleInfoVoReq);
    }

    @Override
//    @PreAuthorize("hasAnyAuthority('root','findRole')")
    @GetMapping("/findRoleById")
    public DataOutResponse findRoleById(@RequestParam("roleId") Long roleId) {
        return roleService.findRoleById(roleId);
    }

    @Override
//    @PreAuthorize("hasAnyAuthority('root','findRole')")
    @GetMapping("/findRolePage")
    public DataOutResponse findRolePage(Integer currentPage, Integer pageSize, String roleName) {
        return roleService.findRolePage(currentPage,pageSize,roleName);
    }
}
