package com.leanin.controller;

import com.leanin.domain.dto.AddRoleDto;
import com.leanin.domain.response.DataOutResponse;
import com.leanin.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author Administrator
 */
@RestController
@RequestMapping(value="role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping("findRoleList")
    public DataOutResponse findRoleList(@RequestParam int page,@RequestParam int pageSize,@RequestParam(required = false) String roleName) {
        return roleService.findRoleList(page,pageSize,roleName);
    }

    @GetMapping("findRoleById")
    public DataOutResponse findRoleById(@RequestParam Long roleId) {
        return roleService.findRoleById(roleId);
    }

    @PostMapping("addRoleInfo")
    public DataOutResponse addRoleInfo(@RequestBody AddRoleDto role) {
        return roleService.addRoleInfo(role);
    }

    @PostMapping("updateRoleInfo")
    public DataOutResponse updateRoleInfo(@RequestBody AddRoleDto role) {
        return roleService.updateRoleInfo(role);
    }
}
