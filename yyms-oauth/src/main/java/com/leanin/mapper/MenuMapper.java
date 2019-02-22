package com.leanin.mapper;

import com.leanin.domain.dto.MenuPermissionDto;
import com.leanin.domain.vo.MenuPermissionVo;
import io.lettuce.core.dynamic.annotation.Param;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 菜单Mapper
 * @author Administrator
 */
@Mapper
public interface MenuMapper {

    /**
     * 根据用户工号查询该菜单列表
     * @param userCode
     * @return
     */
    List<MenuPermissionDto> findMenuByUser(@Param("userCode") String userCode);
    /**
     * 查询菜单列表
     * @param menuName
     * @return
     */
    List<MenuPermissionDto> findMenuList(@Param("menuName") String menuName);

    /**
     * 根据ID查询菜单信息
     * @param menuId
     * @return
     */
    MenuPermissionVo findMenuById(@Param("menuId") Long menuId);

    /**
     * 根据姓名查询菜单信息
     * @param menuName
     * @return
     */
    MenuPermissionDto findMenuByName(@Param("menuName") String menuName);

    /**
     * 根据父ID查询菜单信息
     * @param menuPid
     * @return
     */
    List<MenuPermissionVo> findMenuByPid(@Param("menuId") Long menuPid);

    /**
     * 添加菜单信息
     * @param menu
     */
    void addMenuInfo(MenuPermissionVo menu);

    /**
     * 修改菜单信息
     * @param menu
     */
    void updateMenuInfo(MenuPermissionVo menu);
}
