package com.leanin.service;

import com.leanin.domain.response.DataOutResponse;

public interface MsgRecordService {


    DataOutResponse findMsgRecordList(Integer currentPage, Integer pageSize, Integer planType, String msgThem, Integer sendType);
}
