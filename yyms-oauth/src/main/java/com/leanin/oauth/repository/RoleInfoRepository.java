package com.leanin.oauth.repository;

import com.leanin.domain.vo.RoleInfoVo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleInfoRepository extends JpaRepository<RoleInfoVo,Long> {

    RoleInfoVo findByRoleIdAndAndRoleStatus(Long id,Integer satus);
}
