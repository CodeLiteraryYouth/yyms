package com.leanin.oauth.controller;

import com.leanin.api.auth.MenuControllerApi;
import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.vo.MenuPermissionVo;
import com.leanin.oauth.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/menu")
public class MenuController implements MenuControllerApi{

    @Autowired
    MenuService menuService;

    @Override
    @PreAuthorize("hasAnyAuthority('root','addMenu')")
    @PostMapping("/addMenu")
    public DataOutResponse addMenu(@RequestBody MenuPermissionVo menu) {
        return menuService.addMenu(menu);
    }

    @Override
    @PreAuthorize("hasAnyAuthority('root','delMenu')")
    @GetMapping("/delMenu")
    public DataOutResponse delMenu(@RequestParam("ids") String ids) {
        return menuService.delMenu(ids);
    }

    @Override
    @PreAuthorize("hasAnyAuthority('root','updateMenu')")
    @PutMapping("/updateMenu")
    public DataOutResponse updateMenu(@RequestBody MenuPermissionVo menu) {
        return menuService.updateMenu(menu);
    }

    @Override
    @PreAuthorize("hasAnyAuthority('root','findMenu')")
    @GetMapping("findByPid")
    public DataOutResponse findByPid(@RequestParam("pid") Integer pid) {
        return menuService.findByPid(pid);
    }


}
