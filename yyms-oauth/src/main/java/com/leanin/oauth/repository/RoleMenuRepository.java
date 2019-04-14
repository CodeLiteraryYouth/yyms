package com.leanin.oauth.repository;

import com.leanin.domain.vo.RoleMenuInfoVo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleMenuRepository extends JpaRepository<RoleMenuInfoVo,Long> {

    RoleMenuInfoVo findByRoleIdAndPermissionId(Long roleId,Long menuId);

    Integer deleteByRoleIdAndAndPermissionId(Long roleId,Long menuId);
}
