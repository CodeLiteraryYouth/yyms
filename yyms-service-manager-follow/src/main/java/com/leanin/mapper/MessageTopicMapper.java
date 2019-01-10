package com.leanin.mapper;


import com.leanin.domain.vo.MessageTopicVo;

public interface MessageTopicMapper {
    int deleteByPrimaryKey(String msgTopicId);

    int insert(MessageTopicVo record);

    int insertSelective(MessageTopicVo record);

    MessageTopicVo selectByPrimaryKey(String msgTopicId);

    int updateByPrimaryKeySelective(MessageTopicVo record);

    int updateByPrimaryKey(MessageTopicVo record);
}