package com.leanin.service.impl;

import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.response.ReturnFomart;
import com.leanin.domain.vo.FormInfoVo;
import com.leanin.domain.vo.FormRecordVo;
import com.leanin.domain.vo.PlanInfoVo;
import com.leanin.domain.vo.PlanPatientVo;
import com.leanin.mapper.FormInfoMapper;
import com.leanin.mapper.FormRecordMapper;
import com.leanin.mapper.PlanInfoMapper;
import com.leanin.mapper.PlanPatientMapper;
import com.leanin.service.FormRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FormRecordServiceImpl implements FormRecordService {

    @Autowired
    FormRecordMapper formRecordMapper;

    @Autowired
    FormInfoMapper formInfoMapper;

    @Autowired
    PlanPatientMapper planPatientMapper;

    @Autowired
    PlanInfoMapper planInfoMapper;

    @Override
    public DataOutResponse addFormRecord(FormRecordVo formRecordVo) {
        formRecordMapper.addFormRecord(formRecordVo);
        return ReturnFomart.retParam(200, "保存成功");
    }

    @Override
    public DataOutResponse findFormRecordByPid(Long patientPlanId) {

        PlanPatientVo planPatient = planPatientMapper.findPlanPatientById(patientPlanId);
        if (planPatient == null) {
            return ReturnFomart.retParam(300, "数据不存在");
        }
        PlanInfoVo planInfo = planInfoMapper.findPlanInfoById(planPatient.getPlanNum());
        if (planPatient.getFormStatus() == 1) {//未填写表单
            FormInfoVo formInfo = formInfoMapper.findFormInfoById(planInfo.getPlanNum());
            return ReturnFomart.retParam(200, formInfo);
        } else {
            FormRecordVo formRecord = formRecordMapper.findFormRecordByPid(patientPlanId);
            return ReturnFomart.retParam(200, formRecord);
        }

    }

}
