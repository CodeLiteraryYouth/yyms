package com.leanin.mapper;

import com.leanin.domain.dto.RoleInfoDto;
import com.leanin.domain.vo.RoleInfoVo;
import io.lettuce.core.dynamic.annotation.Param;

import java.util.List;

/**
 * @author Administrator
 */
public interface RoleMapper {

    /**
     * 查询角色列表
     * @param roleName
     * @return
     */
    List<RoleInfoDto> findRoleList(@Param("roleName") String roleName);

    /**
     *根据角色姓名查询单个角色信息
     * @param roleName
     * @return
     */
    RoleInfoDto findRoleByName(@Param("roleName") String roleName);

    /**
     * 根据角色ID查询角色信息
     * @param roleId
     * @return
     */
    RoleInfoDto findRoleById(@Param("roleId") Long roleId);
    /**
     * 添加角色信息
     * @param role
     */
    void addRoleInfo(RoleInfoVo role);

    /**
     * 添加角色ID和菜单栏ID
     * @param roleId
     * @param menuId
     */
    void addRoleMenu(Long roleId,Long menuId);

    /**
     * 修改角色信息
     * @param role
     */
    void updateRoleInfo(RoleInfoVo role);

    /**
     * 修改角色对应的菜单信息
     * @param roleId
     * @param menuId
     */
    void updateRoleMenu(Long roleId,Long menuId);
}
