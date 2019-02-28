package com.leanin.oauth.mapper;

import com.leanin.domain.dto.RoleInfoDto;
import com.leanin.domain.vo.RoleInfoVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleMapper {

    List<RoleInfoVo> findRoleListByUserId(@Param("userId") Long userId);
}
