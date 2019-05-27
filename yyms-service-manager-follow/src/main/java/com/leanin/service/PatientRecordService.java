package com.leanin.service;

import com.leanin.domain.response.DataOutResponse;

/**
 * @author CPJ.
 * @date 2019/5/27.
 * @time 13:16.
 */
public interface PatientRecordService {

    DataOutResponse findPageEduRecord(Integer page, Integer pageSize,Long patientId);

    DataOutResponse findPageFollowRecord(Integer page, Integer pageSize, Long patientId);
}
