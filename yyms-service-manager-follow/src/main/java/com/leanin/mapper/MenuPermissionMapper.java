package com.leanin.mapper;


import com.leanin.domain.vo.MenuPermissionVo;

public interface MenuPermissionMapper {
    int deleteByPrimaryKey(Long id);

    int insert(MenuPermissionVo record);

    int insertSelective(MenuPermissionVo record);

    MenuPermissionVo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(MenuPermissionVo record);

    int updateByPrimaryKey(MenuPermissionVo record);
}