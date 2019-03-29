package com.leanin.service.impl;

import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.response.ReturnFomart;
import com.leanin.domain.vo.*;
import com.leanin.mapper.*;
import com.leanin.service.WxFormService;
import com.leanin.utils.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.UUID;

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
    public DataOutResponse getWxForm(Integer planType, String formNum,Long planPatientId) {
        // 1随访  2宣教  3满意度
        if (planType == 3 ){
            SatisfyInfoVo SatisfyInfoVo = satisfyInfoMapper.findSatisfyByIdAndStatus(formNum,1,planPatientId);
            if (SatisfyInfoVo != null){
                return ReturnFomart.retParam(200,SatisfyInfoVo);
            }
        }else {
            FormInfoVo FormInfoVo = formInfoMapper.findFormInfoByIdAndStatus(formNum,1,planPatientId);
            if (FormInfoVo != null){
                return ReturnFomart.retParam(200,FormInfoVo);
            }
        }
        return ReturnFomart.retParam(300,"数据不存在或者表单已经提交");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DataOutResponse addFollowForm(FormRecordVo formRecordVo) {
        PlanPatientVo planPatient = planPatientMapper.findPlanPatientById(formRecordVo.getPatientPlanId());
        if (planPatient == null){
            return ReturnFomart.retParam(300,"患者信息不存在");
        }
        planPatient.setFormStatus(2);//设置已完成
        planPatientMapper.updatePlanPatient(planPatient);
        formRecordMapper.addFormRecord(formRecordVo);
        return ReturnFomart.retParam(200,"操作成功");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DataOutResponse addStyForm(StyInfoRecordVo styInfoRecordVo) {
        SatisfyPatientVo SatisfyPatientVo = satisfyPatientMapper.findByStyPatId(styInfoRecordVo.getPlanPatientId());
        if (SatisfyPatientVo == null){
            return ReturnFomart.retParam(300,"患者信息不存在");
        }
        SatisfyPatientVo.setFormStatus(2);//设置已完成状态
        satisfyPatientMapper.updateByPrimaryKeySelective(SatisfyPatientVo);
        if (styInfoRecordVo == null ){
            return ReturnFomart.retParam(300,"传入数据为空");
        }
        styInfoRecordVo.setSatisfyId(UUIDUtils.getUUID());
        styInfoRecordVo.setCreateTime(new Date());
        styInfoRecordMapper.addRecord(styInfoRecordVo);
        return ReturnFomart.retParam(200,"操作成功");
    }
}
