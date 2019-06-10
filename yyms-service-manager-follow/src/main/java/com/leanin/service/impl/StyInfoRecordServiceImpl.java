package com.leanin.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.response.ReturnFomart;
import com.leanin.domain.vo.SatisfyInfoVo;
import com.leanin.domain.vo.SatisfyPatientVo;
import com.leanin.domain.vo.StyInfoRecordVo;
import com.leanin.mapper.SatisfyInfoMapper;
import com.leanin.mapper.SatisfyPatientMapper;
import com.leanin.mapper.StyInfoRecordMapper;
import com.leanin.service.StyInfoRecordService;
import com.leanin.utils.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class StyInfoRecordServiceImpl implements StyInfoRecordService {

    @Autowired
    StyInfoRecordMapper styInfoRecordMapper;

    @Autowired
    SatisfyPatientMapper satisfyPatientMapper;

    @Autowired
    SatisfyInfoMapper satisfyInfoMapper;

    @Override
    public DataOutResponse addStyInfoRecord(StyInfoRecordVo styInfoRecordVo) {
        styInfoRecordVo.setSatisfyId(UUIDUtils.getUUID());//主键
        styInfoRecordVo.setFormStatus(2);
        styInfoRecordVo.setCreateTime(new Date());//时间
        styInfoRecordMapper.addRecord(styInfoRecordVo);
        return ReturnFomart.retParam(200,"添加成功");
    }

    @Override
    public DataOutResponse findStyInfoRecordByPid(Long planPatientId) {
        SatisfyPatientVo SatisfyPatientVo = satisfyPatientMapper.findByStyPatId(planPatientId);
        if (SatisfyPatientVo == null){
            return ReturnFomart.retParam(300,"患者信息");
        }
        if(SatisfyPatientVo.getFormStatus() == 1){
            SatisfyInfoVo satisfyInfoVo = satisfyInfoMapper.findSatisfyByPid(planPatientId);
            if (satisfyInfoVo != null){
                return ReturnFomart.retParam(200,satisfyInfoVo);
            }
        }else {
            StyInfoRecordVo styInfoRecordVo = styInfoRecordMapper.findStyInfoRecord(planPatientId);
            if (styInfoRecordVo != null){
                return ReturnFomart.retParam(200,styInfoRecordVo);
            }
        }
        return ReturnFomart.retParam(300,"数据不存在");
    }

    @Override
    public DataOutResponse findById(String satisfyRecordId) {
        StyInfoRecordVo styInfoRecordVo = styInfoRecordMapper.findById(satisfyRecordId);
        if (null == styInfoRecordVo){
            return ReturnFomart.retParam(1,satisfyRecordId);
        }
        return ReturnFomart.retParam(200,styInfoRecordVo);
    }

    @Override
    public DataOutResponse findByPlanNumAndCount(String planNum, Integer count) {
        List<StyInfoRecordVo> styInfoRecordVos = styInfoRecordMapper.findByPlanNumAndCount(planNum);
        if (count != null && (count > styInfoRecordVos.size() || count <= 0)){//抽取表单数量大于提交表单数量
            return ReturnFomart.retParam(5700,count);
        }
        if (count == null){
            return ReturnFomart.retParam(200,styInfoRecordVos);
        }
        Random random = new Random();
        List<StyInfoRecordVo> result =new ArrayList<>();
        for (Integer i = 0; i < count; i++) {
            int anInt = random.nextInt(styInfoRecordVos.size());
            result.add(styInfoRecordVos.get(anInt));
            styInfoRecordVos.remove(anInt);
        }
        return ReturnFomart.retParam(200,result);
    }

    @Override
    public DataOutResponse findByIdCard(String idCard, Integer page, Integer pageSize) {
        if ( page == null || page < 1){
            page = 1;
        }
        if (pageSize == null || pageSize < 0){
            pageSize = 10;
        }
        PageHelper.startPage(page,pageSize);
        Page<StyInfoRecordVo> pageList = (Page<StyInfoRecordVo>) styInfoRecordMapper.findByIdCard(idCard);
        Map result = new HashMap();
        result.put("totalCount",pageList.getTotal());
        result.put("list",pageList.getResult());
        return ReturnFomart.retParam(200,result);
    }
}
