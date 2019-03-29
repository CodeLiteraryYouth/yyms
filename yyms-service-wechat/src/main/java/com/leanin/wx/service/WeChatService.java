package com.leanin.wx.service;

import com.leanin.domain.request.BindPat;
import com.leanin.domain.response.DataOutResponse;

public interface WeChatService {

    DataOutResponse bindPatient(BindPat bindPat);
}
