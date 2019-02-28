package com.leanin.oauth.mapper;

import com.leanin.domain.vo.UserRoleVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserRoleMapper {

    UserRoleVo findByUidAndRid(@Param("adminId") Long adminId,@Param("roleId") Long roleId);

    void addUserRole(@Param("userRoleVo") UserRoleVo userRoleVo);
}
