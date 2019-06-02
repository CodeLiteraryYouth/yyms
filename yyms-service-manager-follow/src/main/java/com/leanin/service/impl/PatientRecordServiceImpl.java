package com.leanin.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.response.ReturnFomart;
import com.leanin.domain.vo.*;
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
    public DataOutResponse findPageEduRecord(Integer page, Integer pageSize,String patientId) {
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
    public DataOutResponse findPageFollowRecord(Integer page, Integer pageSize, String patientId) {
        if (null == page || page < 0  ){//默认显示第一页
            page = 1;
        }
        if (null == pageSize || pageSize < 0  ){//每页展示条数
            pageSize = 10;
        }
        PageHelper.startPage(page,pageSize);
        Page<FollowRecordVo> pageList = (Page<FollowRecordVo>) planPatientMapper.findPageFollowRecord(patientId);
        Map data =new HashMap();
        data.put("totalCount",pageList.getTotal());
        data.put("list",pageList.getResult());
        return ReturnFomart.retParam(200,data);
    }

    @Override
    public DataOutResponse findPageSatisfyRecord(Integer page, Integer pageSize, String patientId) {
        if (null == page || page < 0  ){//默认显示第一页
            page = 1;
        }
        if (null == pageSize || pageSize < 0  ){//每页展示条数
            pageSize = 10;
        }
        PageHelper.startPage(page,pageSize);
        Page<SatisfyRecordVo> pageList = (Page<SatisfyRecordVo>) planPatientMapper.findPageSatisfyRecord(patientId);
        Map result = new HashMap();
        result.put("totalCount",pageList.getTotal());
        result.put("list",pageList.getResult());
        return ReturnFomart.retParam(200,result);
    }

    @Override
    public DataOutResponse findPageMsgRecord(Integer page, Integer pageSize, String patientId) {
        if (null == page || page < 0  ){//默认显示第一页
            page = 1;
        }
        if (null == pageSize || pageSize < 0  ){//每页展示条数
            pageSize = 10;
        }
        PageHelper.startPage(page,pageSize);
        Page<MsgRecordVo> pageList = (Page<MsgRecordVo>) planPatientMapper.findPageMsgRecord(patientId);
        Map result = new HashMap();
        result.put("totalCount",pageList.getTotal());
        result.put("list",pageList.getResult());
        return ReturnFomart.retParam(200,result);
    }

    @Override
    public DataOutResponse findPagePhoneRecord(Integer page, Integer pageSize, String patientId) {
        if (null == page || page < 0  ){//默认显示第一页
            page = 1;
        }
        if (null == pageSize || pageSize < 0  ){//每页展示条数
            pageSize = 10;
        }
        PageHelper.startPage(page,pageSize);
        Page<PhoneRecordVo> pageList = (Page<PhoneRecordVo>) planPatientMapper.findPagePhoneRecord(patientId);
        Map result = new HashMap();
        result.put("totalCount",pageList.getTotal());
        result.put("list",pageList.getResult());
        return ReturnFomart.retParam(200,result);
    }
}
