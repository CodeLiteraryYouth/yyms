package com.leanin.schdule.task;

import com.leanin.domain.vo.PlanInfoVo;
import com.leanin.schdule.mapper.PlanInfoMapper;
import com.leanin.schdule.mapper.SatisfyPlanMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author CPJ.
 * @date 2019/5/22.
 * @time 17:19.
 */
@Component
@Slf4j
public class ImportPlanPatient {


    @Autowired
    PlanInfoMapper planInfoMapper;

    @Autowired
    SatisfyPlanMapper satisfyPlanMapper;



    //随访  宣教 计划导入患者
    @Scheduled(cron = "0 0 0 * * ? *")//每日 0:00:00  执行
    @Transactional(rollbackFor = Exception.class)
    public void importFollowPatient(){
        List<PlanInfoVo> list = planInfoMapper.findByImportData(2);
    }


    //随访  宣教 计划导入患者
    @Scheduled(cron = "0 0 0 * * ? *")//每日 0:00:00  执行
    @Transactional(rollbackFor = Exception.class)
    public void importSatisfyPatient(){

    }
}
