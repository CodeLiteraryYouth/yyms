package com.leanin.mapper;

import com.leanin.domain.vo.NoticeInfoVo;

public interface NoticeInfoMapper {
    int deleteByPrimaryKey(Long noticeId);

    int insert(NoticeInfoVo record);

    int insertSelective(NoticeInfoVo record);

    NoticeInfoVo selectByPrimaryKey(Long noticeId);

    int updateByPrimaryKeySelective(NoticeInfoVo record);

    int updateByPrimaryKeyWithBLOBs(NoticeInfoVo record);

    int updateByPrimaryKey(NoticeInfoVo record);
}