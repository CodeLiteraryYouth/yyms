package com.leanin.mapper;

import com.leanin.domain.vo.NoticeInfoVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface NoticeInfoMapper {

    /**
     * 根据公告名称查询公告列表
     * @param noticeName
     * @return
     */
    List<NoticeInfoVo> findNoticeList(@Param("noticeName") String noticeName);
    /**
     * 修改公告的状态
     * @param noticeId
     * @param status
     * @return
     */
    int updateNoticeStatus(Long noticeId, @Param("status") int status);

    /**
     * 增加公告信息
     * @param record
     * @return
     */
    int addNoticeInfo(NoticeInfoVo record);

    /**
     * 根据ID查询公告信息
     * @param noticeId
     * @return
     */
    NoticeInfoVo findNoticeById(Long noticeId);

    /**
     * 根据姓名查询公告信息
     * @param noticeName
     * @return
     */
    NoticeInfoVo findNoticeByName(String noticeName);
    /**
     * 编辑公告标题信息
     * @param record
     * @return
     */
    int updateNoticeInfo(NoticeInfoVo record);

}