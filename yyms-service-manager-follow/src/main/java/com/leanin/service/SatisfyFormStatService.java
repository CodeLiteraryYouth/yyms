package com.leanin.service;

import com.leanin.domain.request.FormQuest;
import com.leanin.domain.response.DataOutResponse;

/**
 * @author CPJ.
 * @date 2019/6/3.
 * @time 16:56.
 */
public interface SatisfyFormStatService {

    DataOutResponse add(FormQuest formQuest);
}
