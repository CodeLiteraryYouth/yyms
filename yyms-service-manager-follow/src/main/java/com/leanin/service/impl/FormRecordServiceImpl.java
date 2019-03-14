package com.leanin.service.impl;

import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.response.ReturnFomart;
import com.leanin.domain.vo.FormRecordVo;
import com.leanin.mapper.FormRecordMapper;
import com.leanin.service.FormRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FormRecordServiceImpl implements FormRecordService {

    @Autowired
    FormRecordMapper formRecordMapper;

    @Override
    public DataOutResponse addFormRecord(FormRecordVo formRecordVo) {
        formRecordMapper.addFormRecord(formRecordVo);
        return ReturnFomart.retParam(200,"保存成功");
    }

    @Override
    public DataOutResponse findFormRecordByPid(Long patientPlanId) {
        FormRecordVo formRecordByPid = formRecordMapper.findFormRecordByPid(patientPlanId);
        if (formRecordByPid == null){
            return null;
        }
        return ReturnFomart.retParam(200,formRecordByPid);
    }
}
