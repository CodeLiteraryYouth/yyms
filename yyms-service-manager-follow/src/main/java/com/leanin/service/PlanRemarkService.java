package com.leanin.service;

import com.leanin.domain.dao.RemarkDao;
import com.leanin.domain.response.DataOutResponse;

/**
 * @author CPJ.
 * @date 2019/6/5.
 * @time 16:48.
 */
public interface PlanRemarkService {

    DataOutResponse addRemark(RemarkDao remarkDao);

    DataOutResponse findByParam(String planNum, String formNum, Integer planType, String questionId, Integer page, Integer pageSize);
}
