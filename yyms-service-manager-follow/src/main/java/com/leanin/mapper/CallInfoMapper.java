package com.leanin.mapper;

import com.leanin.domain.CallInfo;

public interface CallInfoMapper {
    int deleteByPrimaryKey(String callId);

    int insert(CallInfo record);

    int insertSelective(CallInfo record);

    CallInfo selectByPrimaryKey(String callId);

    int updateByPrimaryKeySelective(CallInfo record);

    int updateByPrimaryKey(CallInfo record);
}