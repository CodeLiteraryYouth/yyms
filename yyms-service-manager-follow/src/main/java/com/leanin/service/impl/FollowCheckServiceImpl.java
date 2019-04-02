package com.leanin.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.response.ReturnFomart;
import com.leanin.domain.vo.FollowCheckVo;
import com.leanin.domain.vo.PlanPatientVo;
import com.leanin.mapper.CheckPatientMapper;
import com.leanin.mapper.FollowCheckMapper;
import com.leanin.mapper.PlanInfoMapper;
import com.leanin.mapper.PlanPatientMapper;
import com.leanin.service.FollowCheckService;
import com.leanin.utils.CompareUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;

@Service
@Slf4j
public class FollowCheckServiceImpl implements FollowCheckService {

    @Autowired
    private FollowCheckMapper followCheckMapper;

    @Autowired
    private PlanInfoMapper planInfoMapper;

    @Autowired
    private PlanPatientMapper planPatientMapper;

    @Autowired
    private CheckPatientMapper checkPatientMapper;

    @Override
    public DataOutResponse findCheckList(int page, int pageSize, String checkName) {
        log.info("搜索的抽查名称为:" + checkName);
        PageHelper.startPage(page, pageSize);
        List<FollowCheckVo> checkList = followCheckMapper.findCheckList(checkName);
        PageInfo pageInfo = new PageInfo<>(checkList);
        return ReturnFomart.retParam(200, pageInfo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DataOutResponse updateCheckStatus(String checkNum, int status) {
        FollowCheckVo followCheck = followCheckMapper.findCheckById(checkNum);
        log.info("修改状态的抽查信息为:" + JSON.toJSONString(followCheck));
        followCheckMapper.updateCheckStatus(checkNum, status);
        return ReturnFomart.retParam(200, "操作成功");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DataOutResponse addCheckInfo(FollowCheckVo record) {
        log.info("增加的抽查信息为:" + JSON.toJSONString(record));
        FollowCheckVo followCheck = followCheckMapper.findCheckByName(record.getCheckName());
        String planNums = record.getPlanNum();
        if (followCheck != null || planNums == null) {
            //该抽查计划已经存在,请勿重复添加
            return ReturnFomart.retParam(4000, "计划已经存在或者抽查计划为空");
        }
        followCheckMapper.addCheckInfo(record);
        FollowCheckVo checkVo = followCheckMapper.findCheckByName(record.getCheckName());
        int index = planNums.indexOf(",");
        String[] planNum = new String[]{planNums};
        String checkRatio = record.getCheckRatio();
        double ratio = Double.parseDouble(checkRatio);
        if (index > 0) {
            planNum = planNums.split(",");
        }
        List<PlanPatientVo> list = planPatientMapper.findListByPlanNum(planNum);
        //获取比例
        int size = (int) (list.size() * ratio);
        //随机抽取患者
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            int i1 = random.nextInt(list.size());
            PlanPatientVo planPatientVo = list.get(i1);
            planPatientVo.setPlanNum(checkVo.getCheckNum());
            checkPatientMapper.add(planPatientVo);
        }
        return ReturnFomart.retParam(200, "添加成功");
    }

    @Override
    public DataOutResponse findCheckById(String checkNum) {
        FollowCheckVo followCheck = followCheckMapper.findCheckById(checkNum);
        log.info("修改状态的抽查信息为:" + JSON.toJSONString(followCheck));
        return ReturnFomart.retParam(200, followCheck);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DataOutResponse updateCheckInfo(FollowCheckVo record) {
        log.info("修改的抽查信息为:" + JSON.toJSONString(record));
        FollowCheckVo checkById = followCheckMapper.findCheckById(record.getCheckNum());
        if (checkById == null){
            return ReturnFomart.retParam(300,"数据不存在");
        }
        followCheckMapper.updateCheckInfo(record);
        return ReturnFomart.retParam(200, record);
    }

}
