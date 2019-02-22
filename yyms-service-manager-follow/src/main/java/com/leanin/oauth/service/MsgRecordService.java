package com.leanin.oauth.service;

import com.leanin.domain.response.DataOutResponse;

public interface MsgRecordService {


    DataOutResponse findMsgRecordList(Integer currentPage, Integer pageSize, Integer planType, String msgThem, Integer sendType);
}
