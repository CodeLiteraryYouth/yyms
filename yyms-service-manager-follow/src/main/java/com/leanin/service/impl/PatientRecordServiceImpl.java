package com.leanin.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.response.ReturnFomart;
import com.leanin.domain.vo.EduRecordVo;
import com.leanin.mapper.PlanPatientMapper;
import com.leanin.service.PatientRecordService;
import com.rabbitmq.client.Return;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author CPJ.
 * @date 2019/5/27.
 * @time 13:16.
 */
@Service
public class PatientRecordServiceImpl implements PatientRecordService {

    @Autowired
    PlanPatientMapper planPatientMapper;

    @Override
    public DataOutResponse findPageEduRecord(Integer page, Integer pageSize,Long patientId) {
        if (null == page || page < 0  ){//默认显示第一页
            page = 1;
        }
        if (null == pageSize || pageSize < 0  ){//每页展示条数
            pageSize = 10;
        }
        PageHelper.startPage(page,pageSize);
        Page<EduRecordVo> pageList = (Page<EduRecordVo>) planPatientMapper.findPageEduRecord(patientId);
        Map result =new HashMap();
        result.put("totalCount",pageList.getTotal());
        result.put("list",pageList.getResult());
        return ReturnFomart.retParam(200,result);
    }

    @Override
    public DataOutResponse findPageFollowRecord(Integer page, Integer pageSize, Long patientId) {
        return null;
    }
}
