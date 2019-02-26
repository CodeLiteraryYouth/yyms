package com.leanin.service.impl;

import com.github.pagehelper.PageHelper;
import com.leanin.domain.response.DataOutResponse;
import com.leanin.mapper.FollowRecordMapper;
import com.leanin.service.FollowRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FollowRecordServiceImpl implements FollowRecordService {

    @Autowired
    FollowRecordMapper followRecordMapper;

    //条件查询 失访和已完成随访的记录
    @Override
    public DataOutResponse findList(Integer currentPage, Integer pageSize, String patientName, Integer planPatsStatus) {
        if (currentPage == null || currentPage <= 0 ){
            currentPage=1;
        }
        if (pageSize == null || pageSize <= 0){
            pageSize=10;
        }
//        PageHelper.startPage(currentPage,pageSize);
//        followRecordMapper.findList(patientName,planPatsStatus);
        return null;
    }
}
