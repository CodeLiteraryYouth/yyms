package com.leanin.mapper;


import com.leanin.domain.vo.CallBackConfigVo;

public interface CallBackConfigMapper {
    int deleteByPrimaryKey(String configNum);

    int insert(CallBackConfigVo record);

    int insertSelective(CallBackConfigVo record);

    CallBackConfigVo selectByPrimaryKey(String configNum);

    int updateByPrimaryKeySelective(CallBackConfigVo record);

    int updateByPrimaryKey(CallBackConfigVo record);
}