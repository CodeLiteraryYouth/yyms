package com.leanin.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.response.ReturnFomart;
import com.leanin.domain.vo.CheckPatientVo;
import com.leanin.mapper.CheckPatientMapper;
import com.leanin.service.CheckPatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class CheckPatientServiceImpl implements CheckPatientService {

    @Autowired
    CheckPatientMapper checkPatientMapper;

    @Override
    public DataOutResponse findList(Integer currentPage,Integer pageSize,String patientName, Integer finishStatus,String checkNUm) {
        if (currentPage == null || currentPage < 0){
            currentPage=1;
        }
        if (pageSize == null || pageSize < 0){
            pageSize=10;
        }
        PageHelper.startPage(currentPage,pageSize);
        Page<CheckPatientVo> page = (Page<CheckPatientVo>) checkPatientMapper.findList(patientName,finishStatus);

        Map dataMap = new HashMap();
        dataMap.put("totalCount",page.getTotal());
        dataMap.put("list",page.getResult());
        dataMap.put("unfinish",checkPatientMapper.findCount(1,checkNUm));
        dataMap.put("finish",checkPatientMapper.findCount(2,checkNUm));
        return ReturnFomart.retParam(200,dataMap);
    }

    @Override
    public DataOutResponse findById(Long checkPatientId) {
        CheckPatientVo checkPatientVo = checkPatientMapper.findById(checkPatientId);
        if (checkPatientVo == null ){
            return ReturnFomart.retParam(300,"数据不存在");
        }
        return ReturnFomart.retParam(200,checkPatientVo);
    }
}
