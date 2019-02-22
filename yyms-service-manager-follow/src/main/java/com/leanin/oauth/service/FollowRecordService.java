package com.leanin.oauth.service;

import com.leanin.domain.response.DataOutResponse;

public interface FollowRecordService {
    //条件查询 失访和已完成随访的记录
    DataOutResponse findList(Integer currentPage, Integer pageSize, String patientName, Integer planPatsStatus);

}
