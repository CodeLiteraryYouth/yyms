package com.leanin.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.response.ReturnFomart;
import com.leanin.domain.vo.*;
import com.leanin.mapper.FormInfoMapper;
import com.leanin.mapper.FormRecordMapper;
import com.leanin.mapper.PlanInfoMapper;
import com.leanin.mapper.PlanPatientMapper;
import com.leanin.service.FormRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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
            if (formInfo == null){
                return ReturnFomart.retParam(96,"数据不存在" );
            }
            return ReturnFomart.retParam(200, formInfo);
        } else {
            FormRecordVo formRecord = formRecordMapper.findFormRecordByPid(patientPlanId);
            return ReturnFomart.retParam(200, formRecord);
        }

    }

    @Override
    public DataOutResponse findById(Long formRecordId) {
        FormRecordVo formRecordVo = formRecordMapper.findById(formRecordId);
        if (null == formRecordVo){
            return ReturnFomart.retParam(1,formRecordId);
        }
        return ReturnFomart.retParam(200,formRecordVo);
    }

    @Override
    public DataOutResponse findByPlanNUmAndCount(String planNum, Integer count) {
        List<FormRecordVo> formRecordVos = formRecordMapper.findByPlanNum(planNum);
        if (count != null && (count > formRecordVos.size() || count <= 0)){//抽取表单数量大于提交表单数量
            return ReturnFomart.retParam(5700,count);
        }
        if (count == null){
            return ReturnFomart.retParam(200,formRecordVos);
        }
        Random random = new Random();
        List<FormRecordVo> result =new ArrayList<>();
        for (Integer i = 0; i < count; i++) {
            int anInt = random.nextInt(formRecordVos.size());
            result.add(formRecordVos.get(anInt));
            formRecordVos.remove(anInt);
        }
        return ReturnFomart.retParam(200,result);
    }

    @Override
    public DataOutResponse findByIdCard(String idCard, Integer page, Integer pageSize) {
        if (page == null || page < 1 ){
            page = 1;
        }
        if (pageSize == null || pageSize < 1){
            pageSize = 10;
        }
        PageHelper.startPage(page,pageSize);
        Page<FormRecordVo> pageList = (Page<FormRecordVo>) formRecordMapper.findByIdCard(idCard);
        Map result = new HashMap();
        result.put("totalCount",pageList.getTotal());
        result.put("list",pageList.getResult());
        return ReturnFomart.retParam(200,result);
    }

}
