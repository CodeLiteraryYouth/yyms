package com.leanin.controller;

import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.vo.MenuPermissionVo;
import com.leanin.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 菜单的Controller
 * @author Administrator
 */
@RestController
@RequestMapping(value="menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @GetMapping("findMenuByUser")
    public DataOutResponse findMenuByUser(@RequestParam String userCode) {
        return menuService.findMenuByUser(userCode);
    }

    @GetMapping("findMenuList")
    public DataOutResponse findMenuList(@RequestParam(required = false) String menuName) {
        return menuService.findMenuList(menuName);
    }

    @GetMapping("updateMenuStatus")
    public DataOutResponse updateMenuStatus(@RequestParam Long menuId) {
        return menuService.updateMenuStatus(menuId);
    }

    @PostMapping("addMenuInfo")
    public DataOutResponse addMenuInfo(@RequestBody MenuPermissionVo menu) {
        return menuService.addMenuInfo(menu);
    }

    @PostMapping("updateMenuInfo")
    public DataOutResponse updateMenuInfo(@RequestBody MenuPermissionVo menu) {
        return menuService.updateMenuInfo(menu);
    }
}
