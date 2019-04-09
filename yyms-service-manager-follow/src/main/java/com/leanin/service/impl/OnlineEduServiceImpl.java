package com.leanin.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.response.ReturnFomart;
import com.leanin.domain.vo.OnlineEdu;
import com.leanin.domain.vo.OnlineEduVo;
import com.leanin.mapper.OnlineEduMapper;
import com.leanin.repository.OnlineEduRepository;
import com.leanin.service.OnlineEduService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class OnlineEduServiceImpl implements OnlineEduService {

    @Autowired
    OnlineEduMapper onlineEduMapper;

    @Autowired
    OnlineEduRepository onlineEduRepository;

    @Override
    public DataOutResponse findListByParam(Integer currentPage, Integer pageSize, String dept, Integer formStatus, Integer sendStatus, String patientName, String sendName,String patientId) {
        if (currentPage == null || currentPage < 1) {
            currentPage = 1;
        }
        if (pageSize == null || pageSize < 1) {
            pageSize = 10;
        }
        PageHelper.startPage(currentPage, pageSize);
        Page<OnlineEduVo> page = (Page<OnlineEduVo>) onlineEduMapper.findListByParam(dept, formStatus, sendStatus, patientName, sendName, patientId);
        Map dataMap = new HashMap();
        dataMap.put("totalCount", page.getTotal());
        dataMap.put("list", page.getResult());
        return ReturnFomart.retParam(200, dataMap);
    }

    @Override
    public DataOutResponse updateFormStatus(String eduId, Integer formStatus) {
        OnlineEdu onlineEdu = onlineEduRepository.findByEduId(eduId);
        if (onlineEdu == null){
            return ReturnFomart.retParam(2011,"数据不存在");
        }
        onlineEdu.setFormStatus(formStatus);
        OnlineEdu save = onlineEduRepository.save(onlineEdu);
        return ReturnFomart.retParam(200,save);
    }
}
