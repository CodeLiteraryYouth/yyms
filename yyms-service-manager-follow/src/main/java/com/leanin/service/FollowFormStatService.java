package com.leanin.service;

import com.leanin.domain.request.FormQuest;
import com.leanin.domain.response.DataOutResponse;

/**
 * @author CPJ.
 * @date 2019/6/3.
 * @time 14:06.
 */
public interface FollowFormStatService {
    DataOutResponse add(FormQuest formQuest);

}
