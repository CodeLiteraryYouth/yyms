package com.leanin.mapper;

import com.leanin.domain.vo.CallBackVo;

public interface CallBackMapper {
    int deleteByPrimaryKey(String callBackNum);

    int insert(CallBackVo record);

    int insertSelective(CallBackVo record);

    CallBackVo selectByPrimaryKey(String callBackNum);

    int updateByPrimaryKeySelective(CallBackVo record);

    int updateByPrimaryKey(CallBackVo record);
}