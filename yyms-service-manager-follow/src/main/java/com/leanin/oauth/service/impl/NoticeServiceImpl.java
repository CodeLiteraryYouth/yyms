package com.leanin.oauth.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.response.ReturnFomart;
import com.leanin.domain.vo.NoticeInfoVo;
import com.leanin.mapper.NoticeInfoMapper;
import com.leanin.oauth.service.NoticeService;
import com.leanin.utils.CompareUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Administrator
 */
@Service
@Slf4j
public class NoticeServiceImpl implements NoticeService {

    @Autowired
    private NoticeInfoMapper noticeInfoMapper;

    @Override
    public DataOutResponse findNoticeList(int page,int pageSize,String noticeName) {
        log.info("公告名称为:"+noticeName);
        PageHelper.startPage(page, pageSize);
        List<NoticeInfoVo> noticeList=noticeInfoMapper.findNoticeList(noticeName);
        PageInfo pageInfo=new PageInfo<>(noticeList);
        return ReturnFomart.retParam(200,pageInfo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DataOutResponse updateNoticeStatus(Long noticeId, int status) {
        NoticeInfoVo noticeInfo=noticeInfoMapper.findNoticeById(noticeId);
        log.info("公告信息为:"+ JSON.toJSONString(noticeInfo));
        noticeInfoMapper.updateNoticeStatus(noticeId, status);
        return ReturnFomart.retParam(200,noticeInfo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DataOutResponse addNoticeInfo(NoticeInfoVo record) {
        log.info("增加的公告信息为:"+JSON.toJSONString(record));
        NoticeInfoVo noticeInfo=noticeInfoMapper.findNoticeByName(record.getNoticeTitle());
        if(CompareUtil.isNotEmpty(noticeInfo)) {
            return ReturnFomart.retParam(4000,noticeInfo);
        }
        noticeInfoMapper.addNoticeInfo(record);
        return ReturnFomart.retParam(200,record);
    }

    @Override
    public DataOutResponse findNoticeById(Long noticeId) {
        NoticeInfoVo noticeInfo=noticeInfoMapper.findNoticeById(noticeId);
        log.info("公告信息为:"+ JSON.toJSONString(noticeInfo));
        return ReturnFomart.retParam(200,noticeInfo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DataOutResponse updateNoticeInfo(NoticeInfoVo record) {
        log.info("修改的公告信息为:"+JSON.toJSONString(record));
        NoticeInfoVo noticeInfo=noticeInfoMapper.findNoticeById(record.getNoticeId());
        if (CompareUtil.isEmpty(noticeInfo) || noticeInfo.getStatus()==1) {
            return ReturnFomart.retParam(96,noticeInfo);
        }
        noticeInfoMapper.updateNoticeInfo(record);
        return ReturnFomart.retParam(200,record);
    }
}
