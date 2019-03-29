package com.leanin.wx.service.impl;

import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.response.ReturnFomart;
import com.leanin.domain.vo.*;
import com.leanin.wx.mapper.*;
import com.leanin.wx.service.WxFormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WxFormServiceImpl implements WxFormService {

    @Autowired
    SatisfyInfoMapper satisfyInfoMapper;

    @Autowired
    FormInfoMapper formInfoMapper;

    @Autowired
    FormRecordMapper formRecordMapper;

    @Autowired
    PlanPatientMapper planPatientMapper;

    @Autowired
    SatisfyPatientMapper satisfyPatientMapper;

    @Autowired
    StyInfoRecordMapper styInfoRecordMapper;

    @Override
    public DataOutResponse getWxForm(Integer planType, String formNum) {
        // 1随访  2宣教  3满意度
        if (planType == 3 ){
            SatisfyInfoVo SatisfyInfoVo = satisfyInfoMapper.findSatisfyById(formNum);
            if (SatisfyInfoVo != null){
                return ReturnFomart.retParam(200,SatisfyInfoVo);
            }
        }else {
            FormInfoVo FormInfoVo = formInfoMapper.findFormInfoById(formNum);
            if (FormInfoVo != null){
                return ReturnFomart.retParam(200,FormInfoVo);
            }
        }
        return ReturnFomart.retParam(300,"数据不存在");
    }

    @Override
    @Transactional
    public DataOutResponse addFollowForm(FormRecordVo formRecordVo) {
        PlanPatientVo planPatient = planPatientMapper.findPlanPatientById(formRecordVo.getPatientPlanId());
        planPatient.setPlanPatsStatus(2);//设置已完成
        planPatientMapper.updatePlanPatient(planPatient);
        formRecordMapper.addFormRecord(formRecordVo);
        return ReturnFomart.retParam(200,"操作成功");
    }

    @Override
    @Transactional
    public DataOutResponse addStyForm(StyInfoRecordVo styInfoRecordVo) {
        SatisfyPatientVo SatisfyPatientVo = satisfyPatientMapper.findByStyPatId(styInfoRecordVo.getPlanPatientId());
        SatisfyPatientVo.setFinishType(2);//设置已完成状态
        satisfyPatientMapper.updateByPrimaryKeySelective(SatisfyPatientVo);
        styInfoRecordMapper.addRecord(styInfoRecordVo);
        return ReturnFomart.retParam(200,"操作成功");
    }
}
