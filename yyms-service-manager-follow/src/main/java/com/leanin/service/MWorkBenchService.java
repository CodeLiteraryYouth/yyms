package com.leanin.service;

import com.leanin.domain.request.MyFollowReq;
import com.leanin.domain.response.DataOutResponse;

public interface MWorkBenchService {

    DataOutResponse findPats(MyFollowReq myFollowReq);

    DataOutResponse findStyPats(MyFollowReq myFollowReq);
}
