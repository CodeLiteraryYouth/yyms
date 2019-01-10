package com.leanin.mapper;

import com.leanin.domain.vo.CallInfoVo;

public interface CallInfoMapper {
    int deleteByPrimaryKey(String callId);

    int insert(CallInfoVo record);

    int insertSelective(CallInfoVo record);

    CallInfoVo selectByPrimaryKey(String callId);

    int updateByPrimaryKeySelective(CallInfoVo record);

    int updateByPrimaryKey(CallInfoVo record);
}