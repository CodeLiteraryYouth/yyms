package com.leanin.oauth.mapper;

import com.leanin.domain.vo.MenuPermissionVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MenuMapper extends BaseMapper<MenuPermissionVo> {

    List<MenuPermissionVo> findMenuListByUserId(@Param("userId") Long userId);
}
