package com.leanin.task;

import com.leanin.mapper.PlanInfoMapper;
import com.leanin.mapper.PlanPatientMapper;
import com.leanin.mapper.RulesInfoMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Slf4j
public class ManagerPatientTask {


    @Autowired
    PlanInfoMapper planInfoMapper;

    @Autowired
    RulesInfoMapper rulesInfoMapper;

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    PlanPatientMapper planPatientMapper;

    /**
     * 将缓存中的数据写到数据库中
     *//*
    @Scheduled(cron = "0 0/10 * * * ?")//每十分钟执行一次
    @Transactional(rollbackFor = Exception.class)
    public void addPatientInfo() {
        //查询 计划信息中的病人信息未写到数据库中的 计划
//        PageHelper.startPage(1, 2);//每次取2条数据
//        Page<PlanInfoVo> page = (Page<PlanInfoVo>) planInfoMapper.findPlanListByImportData(2);
//        List<PlanInfoVo> list = page.getResult();
        //读取缓存中计划对应的病人信息
//        if (list != null) {
//            for (PlanInfoVo planInfoVo : list) {
                //查询规则信息
                RulesInfoVo rules = rulesInfoMapper.findRulesById(planInfoVo.getRulesInfoNum());
                String rulesInfoText = rules.getRulesInfoText();
                Map rulesMap = JSON.parseObject(rulesInfoText, Map.class);//获取规则
                String tiemFont = (String) rulesMap.get("tiemFont");//获取下次任务的时间 1天 2星期 3月
                int timeNum = Integer.parseInt((String) rulesMap.get("timeNum"));
                int timeChoosed = Integer.parseInt((String) rulesMap.get("timeChoosed")); //1 6:00， 2 7:00 一次后推直到 16 21:00
                log.info("规则信息:{}", rulesMap);

//                int i = 0;
//                while (true) {
//                    i++;
                    //从缓存中读取数据
                    List<Map> datalist = (List<Map>) redisTemplate.boundHashOps(planInfoVo).get(i);
                    if (datalist == null) {//删除对应的缓存
                        Boolean delete = redisTemplate.delete(planInfoVo.getPlanNum());
                        if (delete) {
                            log.info("成功删除缓存中的键:{}", planInfoVo.getPlanNum());
                        } else {
                            log.error("缓存中的键删除失败:{}", planInfoVo.getPlanNum());
                        }
                        break;
                    }
                    PlanPatientVo planPatientVo = new PlanPatientVo();
                    for (Map map : datalist) {
                        planPatientVo.setPlanNum(planInfoVo.getPlanNum());//设置计划编号
                        Long patientId = (Long) map.get("patientId");
                        planPatientVo.setPatientId(patientId);//设置病人id
                        String patientInHospitalNo = (String) map.get("patientInHospitalNo");
                        planPatientVo.setPatientInHospitalNo(patientInHospitalNo);//住院号有疑问
                        String patientName = (String) map.get("patientName");
                        planPatientVo.setPatientName(patientName);//设置病人姓名
                        Integer patientSex = (Integer) map.get("patientSex");
                        planPatientVo.setPatientSex(patientSex);//设置病人性别
                        Integer patientAge = (Integer) map.get("patientAge");
                        planPatientVo.setPatientAge(patientAge);//设置病人年龄
                        String patientPhone = (String) map.get("patientPhone");
                        planPatientVo.setPatientPhone(patientPhone);//设置病人手机号码
                        String patientWard = (String) map.get("patientWard");
                        planPatientVo.setPatientWard(patientWard);//设置病人科室
                        Date patientDateTime = (Date) map.get("patientDateTime");
                        planPatientVo.setPatientDateTime(patientDateTime);//设置病人时间
                        String patientDoctor = (String) map.get("patientDoctor");
                        planPatientVo.setPatientDoctor(patientDoctor);//设置诊治医生
                        String patientCondition = (String) map.get("patientCondition");
                        planPatientVo.setPatientCondition(patientCondition);//设置病人情况
                        String patientDiagous = (String) map.get("patientDiagous");
                        planPatientVo.setPatientDiagous(patientDiagous);//设置病人诊断
                        Integer patientType = (Integer) map.get("patientType");
                        planPatientVo.setPatientType(patientType);//设置病人来源
                        String areaCode = (String) map.get("areaCode");
                        planPatientVo.setAreaCode(areaCode);//设置院区编码
                        planPatientVo.setPlanPatsStatus(planInfoVo.getPlanStatus());//设置计划状态 -1:已移除 0：正在使用
                        planPatientVo.setRulesInfoId(rules.getRulesInfoId());//设置随访规则id

                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(patientDateTime);
                        Date nextDate = null;
                        switch (tiemFont) {
                            case "1": {//天
                                calendar.set(Calendar.DATE, timeNum);
                                calendar.set(Calendar.HOUR_OF_DAY, timeChoosed + 5);
                                nextDate = calendar.getTime();
                            }
                            break;
                            case "2": {//星期
                                calendar.set(Calendar.WEEK_OF_YEAR, timeNum);
                                calendar.set(Calendar.HOUR_OF_DAY, timeChoosed + 5);
                                nextDate = calendar.getTime();
                            }
                            break;
                            case "3": {//月
                                calendar.set(Calendar.MONTH, timeNum);
                                calendar.set(Calendar.HOUR_OF_DAY, timeChoosed + 5);
                                nextDate = calendar.getTime();
                            }
                            break;
                        }
                        planPatientVo.setNextDate(nextDate);//设置下次随访日期

                        planPatientMapper.addPlanPatient(planPatientVo);//将数据存到数据中
                    }

//                }

//            }

//        }
    }*/

    /**
     * 修改规则时间
     */
    @Scheduled(cron = "0 0/6 * * * ?")//每六分钟执行一次
    @Transactional(rollbackFor = Exception.class)
    public void updateNextDate(){
//        Integer planStatus=0;//-1:已移除 0：正在使用
////        Integer planCycle=1; //0 不循环 1循环
////        //查询是否是循环发送规则的计划
////        List<PlanInfoVo> PlanInfoVoList=planInfoMapper.findPlanInfoByStatusAndPlanCycle(planStatus,planCycle);
////        for (PlanInfoVo planInfoVo : PlanInfoVoList) {
////            //查询计划相对应的规则
////
////            //根据计划查询需要循环发送的病人信息 发送状态是已发送 并且是循环发送
////
////            //修改病人信息的发送状态 和 发送时间
////
////
////        }

    }


}
