package com.leanin.mapper;


import com.leanin.domain.vo.RoleMenuInfoVo;

public interface RoleMenuInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(RoleMenuInfoVo record);

    int insertSelective(RoleMenuInfoVo record);

    RoleMenuInfoVo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(RoleMenuInfoVo record);

    int updateByPrimaryKey(RoleMenuInfoVo record);
}