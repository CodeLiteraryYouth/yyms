package com.leanin.oauth.mapper;

import com.leanin.domain.request.RoleInfoVoReq;
import com.leanin.domain.response.RoleInfoReP;
import com.leanin.domain.vo.RoleInfoVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RoleMapper /*extends BaseMapper<RoleInfoVo>*/ {

    List<RoleInfoVo> findRoleListByUserId(@Param("userId") Long userId);

    RoleInfoReP findRoleId(@Param("roleId") Long roleId);

    List<RoleInfoReP> findByRoleName(@Param("roleName") String roleName);


//    void addRole(@Param("roleInfoVo") RoleInfoVo roleInfoVo);
}
