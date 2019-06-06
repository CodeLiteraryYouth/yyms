package com.leanin.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.leanin.domain.dao.RemarkDao;
import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.response.ReturnFomart;
import com.leanin.mapper.PlanRemarkMapper;
import com.leanin.repository.PlanRemarkRepository;
import com.leanin.service.PlanRemarkService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author CPJ.
 * @date 2019/6/5.
 * @time 16:48.
 */
@Service
@Slf4j
public class PlanRemarkServiceImpl implements PlanRemarkService {

    @Autowired
    PlanRemarkRepository planRemarkRepository;

    @Autowired
    PlanRemarkMapper planRemarkMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DataOutResponse addRemark(RemarkDao remarkDao) {
        log.info("添加的计划评论"+remarkDao.toString());
        RemarkDao save = planRemarkRepository.save(remarkDao);
        return ReturnFomart.retParam(200,save);
    }

    @Override
    public DataOutResponse findByParam(String planNum, String formNum, Integer planType, String questionId, Integer page, Integer pageSize) {
        if (page == null || page < 1){
            page = 1;
        }
        if (pageSize == null || pageSize < 0){
            pageSize = 10;
        }
        PageHelper.startPage(page,pageSize);
        Page<RemarkDao> pageList = (Page<RemarkDao>) planRemarkMapper.findByParam(planNum,formNum,planType,questionId);
        Map result = new HashMap();
        result.put("totalCount",pageList.getTotal());
        result.put("list",pageList.getResult());
        return ReturnFomart.retParam(200,result);
    }
}
