package com.leanin.service;

import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.vo.NoticeInfoVo;


public interface NoticeService {
    /**
     * 根据公告名称查询公告列表
     * @param noticeName
     * @return
     */
    DataOutResponse findNoticeList( int page,int pageSize,String noticeName);
    /**
     * 修改公告的状态
     * @param noticeId
     * @param status
     * @return
     */
    DataOutResponse updateNoticeStatus(Long noticeId,int status);

    /**
     * 增加公告信息
     * @param record
     * @return
     */
    DataOutResponse addNoticeInfo(NoticeInfoVo record);

    /**
     * 根据ID查询公告信息
     * @param noticeId
     * @return
     */
    DataOutResponse findNoticeById(Long noticeId);

    /**
     * 编辑公告标题信息
     * @param record
     * @return
     */
    DataOutResponse updateNoticeInfo(NoticeInfoVo record);
}
