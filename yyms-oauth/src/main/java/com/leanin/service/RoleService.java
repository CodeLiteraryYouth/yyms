package com.leanin.service;

import com.leanin.domain.dto.AddRoleDto;
import com.leanin.domain.response.DataOutResponse;


/**
 * @author Administrator
 */
public interface RoleService {
    /**
     * 查询角色列表
     * @param roleName
     * @return
     */
    DataOutResponse findRoleList(int page,int pageSize,String roleName);


    /**
     * 根据角色ID查询角色信息
     * @param roleId
     * @return
     */
    DataOutResponse findRoleById(Long roleId);
    /**
     * 添加角色信息
     * @param role
     */
    DataOutResponse addRoleInfo(AddRoleDto role);

    /**
     * 修改角色信息
     * @param role
     */
    DataOutResponse updateRoleInfo(AddRoleDto role);

}
