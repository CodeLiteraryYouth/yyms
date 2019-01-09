package com.leanin.mapper;


import com.leanin.domain.vo.RoleInfoVo;

public interface RoleInfoMapper {
    int deleteByPrimaryKey(Long roleId);

    int insert(RoleInfoVo record);

    int insertSelective(RoleInfoVo record);

    RoleInfoVo selectByPrimaryKey(Long roleId);

    int updateByPrimaryKeySelective(RoleInfoVo record);

    int updateByPrimaryKey(RoleInfoVo record);
}