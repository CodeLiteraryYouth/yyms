package com.leanin.service;

import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.vo.MenuPermissionVo;

/**
 * 菜单Service
 * @author Administrator
 */
public interface MenuService {

    /**
     * 根据用户工号查询该菜单列表
     * @return
     */
    DataOutResponse findMenuByUser(String userCode);
    /**
     * 查询菜单列表
     * @param menuName
     * @return
     */
    DataOutResponse findMenuList(String menuName);

    /**
     * 根据父ID查询菜单信息
     * @param menuId
     * @return
     */
    DataOutResponse updateMenuStatus(Long menuId);

    /**
     * 添加菜单信息
     * @param menu
     */
    DataOutResponse addMenuInfo(MenuPermissionVo menu);

    /**
     * 修改菜单信息
     * @param menu
     */
    DataOutResponse updateMenuInfo(MenuPermissionVo menu);
}
