package com.leanin.service;

import com.leanin.domain.request.BindPat;
import com.leanin.domain.response.DataOutResponse;

public interface WeChatService {

    DataOutResponse bindPatient(BindPat bindPat);

    DataOutResponse login(String code);

}
