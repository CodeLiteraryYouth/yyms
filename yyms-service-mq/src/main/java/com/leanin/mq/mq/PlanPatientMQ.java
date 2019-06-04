package com.leanin.mq.mq;

import com.alibaba.fastjson.JSON;
import com.leanin.domain.vo.*;
import com.leanin.mq.config.RabbitMQConfig;
import com.leanin.mq.dao.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.security.PublicKey;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
@Slf4j
public class PlanPatientMQ {

    @Autowired
    PlanPatientMapper planPatientMapper;

    @Autowired
    PlanInfoMapper planInfoMapper;

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    RulesInfoMapper rulesInfoMapper;

    @Autowired
    SatisfyPlanMapper satisfyPlanMapper;

    @Autowired
    SatisfyPatientMapper satisfyPatientMapper;

    @Autowired
    MessageTopicMapper messageTopicMapper;

    @Autowired
    MessagePatientMapper messagePatientMapper;

    @Autowired
    PatientInfoMapper patientInfoMapper;

    //监听短信主题消息队列
    @RabbitListener(queues = RabbitMQConfig.QUEUE_SEND_NAME)
    @Transactional(rollbackFor = Exception.class)
    public void msgQueue(String msgTopicId){
        log.info("添加短信计划的主键是:{}",msgTopicId);
        MessageTopicVo msgTopic = messageTopicMapper.findMsgTopicById(msgTopicId);
        if(msgTopic == null ){
            log.info("短信主题计划为空"+msgTopicId);
            return;
        }
        List<Map> list = (List<Map>) redisTemplate.boundHashOps("msgPlan").get(msgTopic.getMsgTopicId());
        log.info("导入计划患者的信息:{}", JSON.toJSONString(list));
        MessagePatientVo messagePatientVo =new MessagePatientVo();
        PatientInfoVo patientInfoVo = new PatientInfoVo();
        SimpleDateFormat sdf = new SimpleDateFormat();
        for (Map map : list) {
            //his 患者数据处理
            String patientId = (String) map.get("patientId");           //his患者主键
            String patientName = (String) map.get("patientName");       //his患者姓名
            Integer patientSex = (Integer) map.get("patientSex");       //患者性别  1 男 2 女
            Integer patientAge = (Integer) map.get("patientAge");       //患者年龄
            String patientPhone = (String) map.get("patientPhone");     //患者手机号
            String idCard = (String) map.get("idCard");                 //his患者身份证
            String dist = (String) map.get("DIST");                     //his患者病区
            String inhosNo = (String)map.get("inhosNo");                ///患者住院号
            String dept = (String) map.get("DEPT");                     //患者科室中文名
            String deptId = (String) map.get("DEPTID");                 //患者科室码
            Date patientTime = new Date((Long) map.get("lastDate"));    //患者 出住院 门诊 体检时间
            String diagousId = (String) map.get("DIAGNOSIS_ICD");       //患者诊断吗
            String diagous = (String) map.get("DIAGNOSIS_NAME");        //患者诊断名称
            String home_addr = (String) map.get("HOME_ADDR");     //his家庭住址
            String unit_name = (String) map.get("UNIT_NAME");     //his工作单位
            String marriage = (String) map.get("MARRIAGE");       //his婚否
            String nationality = (String) map.get("NATIONALITY"); //his民族
            String education = (String) map.get("EDUCATION");     //his学历
            String birthstring = null;
            Date birth = new Date((Long) map.get("BIRTH"));                   //his出生日期
            if (null != birth ){
                birthstring = sdf.format(birth);
            }
            //
            //患者档案、
            patientInfoVo.setPatientInfoId(idCard);      //身份证号
            patientInfoVo.setPatientInfoName(patientName);  //患者档案中的 患者姓名
            patientInfoVo.setPatientInfoSex(patientSex);    //患者档案中的 患者性别  1 男 2 女
            patientInfoVo.setPatientInfoBirthday(birthstring);//出生日期
            patientInfoVo.setPatientInfoIdcard(idCard);     //身份证号
            patientInfoVo.setPatientInfoPhone(patientPhone); //手机号码
            patientInfoVo.setPatientInfoMarrStatus(marriage);//是否结婚
            patientInfoVo.setPatientInfoNational(nationality);  //民族
            patientInfoVo.setPatientInfoCulture(education);     //文化程度
            patientInfoVo.setPatientInfoJob(null);              //职业
            patientInfoVo.setPatientInfoJobAddre(unit_name);    //工作地址
            patientInfoVo.setPatientInfoAddre(home_addr);       //家庭住址
            patientInfoVo.setHospitalAreaCode(null);            //院区编码
            patientInfoVo.setEmerContName(null);                //紧急联系人姓名
            patientInfoVo.setEmerContRelation(null);            //紧急联系人关系
            patientInfoVo.setEmerContPhone(null);               //紧急联系人电话
            patientInfoVo.setCreateTime(new Date());            //建档创建时间
            patientInfoVo.setPatientSource(msgTopic.getPatientType()); //患者来源 1,出院；2,门诊;3,在院;4体检 5 建档
            patientInfoVo.setHealthCardNo(null);                //医保卡号
            patientInfoVo.setHosNo(inhosNo);                    //住院号 门诊号 体检号
            //查询 患者档案里是否有相同的患者来源的患者
            PatientInfoVo dao = patientInfoMapper.findByPatientIdAndSource(patientInfoVo.getPatientInfoId(),patientInfoVo.getPatientSource());
            if (null == dao){
                patientInfoMapper.addPatientInfo(patientInfoVo);
            }

            //短信主题患者
            messagePatientVo.setMsgTopicId(msgTopic.getMsgTopicId());   //设置短信主题计划id
            messagePatientVo.setPatientId(idCard);                   //his患者id
            messagePatientVo.setPatientName(patientName);               //患者姓名
            messagePatientVo.setPatientSex(patientSex);                 //设置病人性别  1 男  2 女
            messagePatientVo.setPatientAge(patientAge);                 //设置病人年龄
            messagePatientVo.setPatientPhone(patientPhone);             //设置病人手机号码
            messagePatientVo.setPatientIdCard(idCard);
            messagePatientVo.setIdCard(idCard);                         //身份证号
            messagePatientVo.setPatientType(msgTopic.getPatientType()); //设置病人来源
            messagePatientVo.setAreaCode(dist);                         //设置病区编码
            messagePatientVo.setSendType(1);                            //发送状态；1 未发送 2 已发送 3 发送失败
            messagePatientVo.setInhosNo(inhosNo);                       //住院号
            messagePatientVo.setPatientStatus(1);                       //患者状态  -1 删除  1 正在使用
            messagePatientVo.setPatientWard(dept);                      //患者科室
            messagePatientVo.setPatientWardId(deptId);                  //患者科室主键
            messagePatientVo.setPatientTime(patientTime);               //获取患者时间
            messagePatientVo.setIllnessId(diagousId);
            messagePatientVo.setIllnessName(diagous);
//            messagePatientVo.set(satisfyPlan.getPatientWard());//设置病人科室  可能是集合
//            Date lastDate = new Date((Long) map.get("lastDate"));//获取最近一次的出院时间
//                String patientCondition = (String) map.get("patientCondition");
//                planPatientVo.setPatientCondition(patientCondition);//设置病人情况  可能是集合
//            satisfyPatientVo.setPatientDiagous(satisfyPlan.getDiseaseName());//设置病人诊断  可能是集合

//                String areaCode = (String) map.get("areaCode");
//                planPatientVo.setAreaCode(areaCode);//设置院区编码   可能是集合
//            satisfyPatientVo.setFinishType(1); //完成状态  1 未完成 2已完成

//            satisfyPatientVo.setPatientStatus(0); //是否删除; 0 未删除 1 已删除



            log.info("短信计划患者信息:{}",JSON.toJSONString(messagePatientVo));
            messagePatientMapper.insertSelective(messagePatientVo);
        }
        Long delete = redisTemplate.boundHashOps("msgPlan").delete(msgTopic.getMsgTopicId());

    }

    //满意度监听队列
    @RabbitListener(queues = RabbitMQConfig.QUEUE_INSERT_SP)
    @Transactional(rollbackFor = Exception.class)
    public void insertSatisfyPlan(String planSatisfyNum){
        log.info("添加满意度计划的主键是:{}",planSatisfyNum);
        //获取满意度计划信息
        SatisfyPlanVo satisfyPlan = satisfyPlanMapper.findSatisfyPlanById(planSatisfyNum);
        if(satisfyPlan == null){
            log.info("满意度计划不存在:{}",planSatisfyNum);
            return;
        }
        //获取缓存中的患者信息
        List<Map> list = (List<Map>) redisTemplate.boundHashOps("satisfyPlan").get(planSatisfyNum);
        log.info("添加满意度计划患者信息是:{}",JSON.toJSONString(list));
        //获取规则内容
        String rulesText = satisfyPlan.getRulesText();
        Map rulesMap = JSON.parseObject(rulesText, Map.class);

        String tiemFont = (String) rulesMap.get("tiemFont");//获取下次任务的时间 1天
        String timeNumStr = (String) rulesMap.get("timeNum");
        Integer timeChoosed =Integer.parseInt((String) rulesMap.get("timeChoosed"));//Integer.parseInt((String)rulesMap.get("timeChoosed")); //1 6:00， 2 7:00 一次后推直到 16 21:00
        String timeSelect = (String) rulesMap.get("timeSelect");//1出院


        SatisfyPatientVo satisfyPatientVo=new SatisfyPatientVo();
        PatientInfoVo patientInfoVo =new PatientInfoVo();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (Map map : list) {
            String patientId = (String) map.get("patientId");
            String patientName = (String) map.get("patientName");
            Integer patientSex = (Integer) map.get("patientSex");
            Integer patientAge = (Integer) map.get("patientAge");
            String patientPhone = (String) map.get("patientPhone");
            String dept = (String) map.get("DEPT");
            String diagous = (String) map.get("DIAGNOSIS_NAME");
            String areaCode = (String) map.get("DIST");
            String idCard = (String) map.get("idCard");
            String inhosNo = (String) map.get("inhosNo");
            String deptId = (String) map.get("DEPTID");
            Date lastDate = new Date((Long) map.get("lastDate"));//获取最近一次的出院时间
            String diagousId = (String) map.get("DIAGNOSIS_ICD");
            String home_addr = (String) map.get("HOME_ADDR");     //his家庭住址
            String unit_name = (String) map.get("UNIT_NAME");     //his工作单位
            String marriage = (String) map.get("MARRIAGE");       //his婚否
            String nationality = (String) map.get("NATIONALITY"); //his民族
            String education = (String) map.get("EDUCATION");     //his学历
            String birthstring = null;
            Date birth = new Date((Long) map.get("BIRTH"));                   //his出生日期
            if (null != birth ){
                birthstring = sdf.format(birth);
            }
            //
            //患者档案、
            patientInfoVo.setPatientInfoId(idCard);      //身份证号
            patientInfoVo.setPatientInfoName(patientName);  //患者档案中的 患者姓名
            patientInfoVo.setPatientInfoSex(patientSex);    //患者档案中的 患者性别  1 男 2 女
            patientInfoVo.setPatientInfoBirthday(birthstring);//出生日期
            patientInfoVo.setPatientInfoIdcard(idCard);     //身份证号
            patientInfoVo.setPatientInfoPhone(patientPhone); //手机号码
            patientInfoVo.setPatientInfoMarrStatus(marriage);//是否结婚
            patientInfoVo.setPatientInfoNational(nationality);  //民族
            patientInfoVo.setPatientInfoCulture(education);     //文化程度
            patientInfoVo.setPatientInfoJob(null);              //职业
            patientInfoVo.setPatientInfoJobAddre(unit_name);    //工作地址
            patientInfoVo.setPatientInfoAddre(home_addr);       //家庭住址
            patientInfoVo.setHospitalAreaCode(null);            //院区编码
            patientInfoVo.setEmerContName(null);                //紧急联系人姓名
            patientInfoVo.setEmerContRelation(null);            //紧急联系人关系
            patientInfoVo.setEmerContPhone(null);               //紧急联系人电话
            patientInfoVo.setCreateTime(new Date());            //建档创建时间
            patientInfoVo.setPatientSource(satisfyPlan.getPatientType()); //患者来源 1,出院；2,门诊;3,在院;4体检 5 建档
            patientInfoVo.setHealthCardNo(null);                //医保卡号
            patientInfoVo.setHosNo(inhosNo);                    //住院号 门诊号 体检号
            //查询 患者档案里是否有相同的患者来源的患者
            PatientInfoVo dao = patientInfoMapper.findByPatientIdAndSource(patientInfoVo.getPatientInfoId(),patientInfoVo.getPatientSource());
            if (null == dao){
                patientInfoMapper.addPatientInfo(patientInfoVo);
            }

            //满意度计划患者
            satisfyPatientVo.setSatisfyPlanNum(satisfyPlan.getPlanSatisfyNum());//设置满意度计划id
            satisfyPatientVo.setPatientId(idCard);//患者id
            satisfyPatientVo.setPatientName(patientName);//患者姓名
            satisfyPatientVo.setPatientSex(patientSex);//设置病人性别
            satisfyPatientVo.setPatientAge(patientAge);//设置病人年龄
            satisfyPatientVo.setPatientPhone(patientPhone);//设置病人手机号码
            satisfyPatientVo.setPatientWard(dept);//设置病人科室
            satisfyPatientVo.setFinishType(1); //完成状态  -1:收案 1：未完成 2：已完成；3:已过期; 4 无法接听 5 号码错误 6 拒绝接听 7 无人接听 8 家属接听 9 患者不合作 10 无联系电话 11 其他
//                String patientCondition = (String) map.get("patientCondition");
//                planPatientVo.setPatientCondition(patientCondition);//设置病人情况  可能是集合
            satisfyPatientVo.setPatientDiagous(diagous);//设置病人诊断
            satisfyPatientVo.setPatientType(satisfyPlan.getPatientType());//设置病人来源 1,出院；2,门诊;3,在院;4体检 5 建档
            satisfyPatientVo.setPatientStatus(0);  //0 在用  1  已删除
            satisfyPatientVo.setAreaCode(areaCode);//设置病区编码   可能是集合
            satisfyPatientVo.setSendType(1); //发送状态；1 未发送 2 已发送 3 发送失败
            satisfyPatientVo.setFormStatus(1);//表单填写状态 1 未填写 2 已填写
            satisfyPatientVo.setFormId(satisfyPlan.getSatisfyNum());//满意度表单主键
            satisfyPatientVo.setPatientTime(lastDate);//获取患者时间
            satisfyPatientVo.setPatientWardId(deptId);//患者科室主键
            satisfyPatientVo.setDiagousId(diagousId);   //患者疾病主键
            satisfyPatientVo.setIdCard(idCard);     //身份证号
            satisfyPatientVo.setInhosNo(inhosNo);   //在院号
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(lastDate);
            calendar.add(Calendar.DATE, Integer.parseInt(timeNumStr));
            calendar.set(Calendar.HOUR_OF_DAY, timeChoosed + 5);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            Date time = calendar.getTime();
            satisfyPatientVo.setPatientDateTime(time);
            Integer rangeDays =Integer.parseInt((String) rulesMap.get("rangeDays")) ;//范围天数
            if(((new Date().getTime() - time.getTime())/(1*60*60*1000*24)) > rangeDays){//判断是否过期
                satisfyPatientVo.setFinishType(12);//患者失效
            }
            log.info("满意度患者信息:{}",JSON.toJSONString(satisfyPatientVo));
            satisfyPatientMapper.insertSelective(satisfyPatientVo);
        }
        Long delete = redisTemplate.boundHashOps("satisfyPlan").delete(planSatisfyNum);

    }


    //随访监听队列
    @RabbitListener(queues = RabbitMQConfig.QUEUE_INSERT_NAME)
    @Transactional(rollbackFor = Exception.class)
    public void insertData(String planNum) {
        log.info("添加随访/宣教计划主键是:{}",planNum);
        //获取计划信息
        PlanInfoVo planInfo = planInfoMapper.findPlanInfoById(planNum);
        if (planInfo == null){
            log.info("随访/宣教计划不存在:{}",planNum);
            return;
        }
        //获取缓存中的病人数据
        List<Map> list = (List<Map>) redisTemplate.boundHashOps("plan").get(planNum);
        log.info("添加随访/宣教计划的患者信息是:{}",JSON.toJSONString(list));
        if (list != null && list.size() > 0) {//有缓存
            //获取规则信息
            RulesInfoVo rules = rulesInfoMapper.findRulesById(planInfo.getRulesInfoNum());
            String rulesInfoText = rules.getRulesInfoText();
            Map rulesMap = JSON.parseObject(rulesInfoText, Map.class);//获取规则
            String tiemFont = (String) rulesMap.get("tiemFont");//获取下次任务的时间 1天 2星期 3月
            String timeNumStr = rulesMap.get("timeNum")+"";//数字
            int timeNum = 0;
            if (!"".equals(timeNumStr) && timeNumStr != null) {
                timeNum = Integer.parseInt(timeNumStr);//
            }

            int timeChoosed = Integer.parseInt((String) rulesMap.get("timeChoosed")); //1 6:00， 2 7:00 一次后推直到 16 21:00
//            int timeChoosed = (int) rulesMap.get("timeChoosed"); //1 6:00， 2 7:00 一次后推直到 16 21:00
            String timeSelect = (String) rulesMap.get("timeSelect");
            Date timePick = null;
            Object timePick1 = rulesMap.get("timePick");
            if (!"".equals(timePick1) && timePick1 != null) {
                SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    timePick = sf.parse((String) timePick1);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            String weeks1 =(String) rulesMap.get("weeks");
            int weeks = 0;
            if (!"".equals(weeks1) && weeks1 != null) {
                weeks = Integer.parseInt(weeks1);
            }
            String sendTimeDays1 = (String) rulesMap.get("sendTimeDays");
            int sendTimeDays = 0;
            if (!"".equals(sendTimeDays1) && sendTimeDays1 != null) {
                sendTimeDays = Integer.parseInt(sendTimeDays1);
            }
            String sendTimeMonths1 = (String) rulesMap.get("sendTimeMonths");
            int sendTimeMonths = 0;
            if (!"".equals(sendTimeMonths1) && sendTimeMonths1 != null) {
                sendTimeMonths = Integer.parseInt(sendTimeMonths1);
            }

            PlanPatientVo planPatientVo = new PlanPatientVo();  //计划患者信息
            PatientInfoVo patientInfoVo =new PatientInfoVo();   //本地患者档案对象
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            for (Map map : list) {
                String patientId =(String) map.get("patientId");      //his 患者主键 现改成身份证号
                String patientName = (String) map.get("patientName");   // his 患者姓名
                Integer patientSex = (Integer) map.get("patientSex");   //his 患者性别
                Integer patientAge = (Integer) map.get("patientAge");   //his 患者年龄
                String patientPhone = (String) map.get("patientPhone");//his 患者手机号
                String dept = (String) map.get("DEPT");                 //his患者科室 中文名
                String disease = (String) map.get("DIAGNOSIS_NAME");    //his 患者诊断 中文名
                String areaCode = (String) map.get("DIST");             //his患者病区
                String idcard = (String) map.get("idCard");           //his患者身份证号
                String inhosNo = (String) map.get("inhosNo");         //his患者住院号
                String deptId = (String) map.get("DEPTID");           //his患者科室id
                Date lastDate = new Date((Long) map.get("lastDate"));   //his患者出住院时间
                String diagousId = (String) map.get("DIAGNOSIS_ICD");   //his患者疾病ICD码
                String home_addr = (String) map.get("HOME_ADDR");     //his家庭住址
                String unit_name = (String) map.get("UNIT_NAME");     //his工作单位
                String marriage = (String) map.get("MARRIAGE");       //his婚否
                String nationality = (String) map.get("NATIONALITY"); //his民族
                String education = (String) map.get("EDUCATION");     //his学历
                String birthstring = null;
                Date birth = new Date((Long) map.get("BIRTH"));                   //his出生日期
                if (null != birth ){
                    birthstring = sdf.format(birth);
                }



                //计划患者
                planPatientVo.setPlanNum(planInfo.getPlanNum());//设置计划编号
                planPatientVo.setPatientId(idcard);          //设置his 患者主键
                planPatientVo.setSendType(1);                   //发送状态  1未发送  2已发送 3发送异常
                planPatientVo.setPatientName(patientName);      //设置病人姓名
                planPatientVo.setPatientSex(patientSex);        //设置病人性别
                planPatientVo.setPatientAge(patientAge);        //设置病人年龄
                planPatientVo.setPatientPhone(patientPhone);    //设置病人手机号码
                planPatientVo.setPatientWard(dept);             //设置病人科室 中文名
                planPatientVo.setFormStatus(1);                 //设置表单完成状态 1 未完成  2 已完成
                planPatientVo.setPatientStatus(1);              //设置 状态  1：未删除  2 已删除
//                String patientCondition = (String) map.get("patientCondition");
//                planPatientVo.setPatientCondition(patientCondition);//设置病人情况  可能是集合
                planPatientVo.setPatientDiagous(disease);       //设置病人诊断 中文名
                planPatientVo.setPatientType(planInfo.getPatientInfoSource());//设置病人来源 1,出院；2,门诊;3,在院;4体检 5 建档
                planPatientVo.setAreaCode(areaCode);            //设置院区编码   可能是集合
                if (planInfo.getPlanType() == 1){//随访
                    planPatientVo.setPlanPatsStatus(0);//-1:收案 0.初始状态全部 1：待随访 2：已完成；3:已过期
                }else{//宣教
                    planPatientVo.setPlanPatsStatus(1);//-1:收案 0.初始状态全部 1：待随访 2：已完成；3:已过期
                }
                planPatientVo.setRulesInfoId(planInfo.getRulesInfoNum());//规则号
                planPatientVo.setPatientSource(planInfo.getPatientInfoSource());//设置患者来源
                planPatientVo.setFormId(planInfo.getFollowFormNum());//设置随访表单id
                planPatientVo.setPatientWardId(deptId);         //患者科室id
                planPatientVo.setPatientTime(lastDate);         //出住院日期
                planPatientVo.setDiagousId(diagousId);          //诊断结果
                planPatientVo.setInhosNo(inhosNo);              //出住院号
                planPatientVo.setIdCard(idcard);                //身份证号
                Date nextDate = null;

                //患者档案、
                patientInfoVo.setPatientInfoId(idcard);      //身份证号
                patientInfoVo.setPatientInfoName(patientName);  //患者档案中的 患者姓名
                patientInfoVo.setPatientInfoSex(patientSex);    //患者档案中的 患者性别  1 男 2 女
                patientInfoVo.setPatientInfoBirthday(birthstring);//出生日期
                patientInfoVo.setPatientInfoIdcard(idcard);     //身份证号
                patientInfoVo.setPatientInfoPhone(patientPhone); //手机号码
                patientInfoVo.setPatientInfoMarrStatus(marriage);//是否结婚
                patientInfoVo.setPatientInfoNational(nationality);  //民族
                patientInfoVo.setPatientInfoCulture(education);     //文化程度
                patientInfoVo.setPatientInfoJob(null);              //职业
                patientInfoVo.setPatientInfoJobAddre(unit_name);    //工作地址
                patientInfoVo.setPatientInfoAddre(home_addr);       //家庭住址
                patientInfoVo.setHospitalAreaCode(null);            //院区编码
                patientInfoVo.setEmerContName(null);                //紧急联系人姓名
                patientInfoVo.setEmerContRelation(null);            //紧急联系人关系
                patientInfoVo.setEmerContPhone(null);               //紧急联系人电话
                patientInfoVo.setCreateTime(new Date());            //建档创建时间
                patientInfoVo.setPatientSource(planInfo.getPatientInfoSource()); //患者来源 1,出院；2,门诊;3,在院;4体检 5 建档
                patientInfoVo.setHealthCardNo(null);                //医保卡号
                patientInfoVo.setHosNo(inhosNo);                    //住院号 门诊号 体检号
                //查询 患者档案里是否有相同的患者来源的患者
                PatientInfoVo dao = patientInfoMapper.findByPatientIdAndSource(patientInfoVo.getPatientInfoId(),patientInfoVo.getPatientSource());
                if (null == dao){
                    patientInfoMapper.addPatientInfo(patientInfoVo);
                }
                switch (rules.getRulesInfoTypeName()) {
                    case 1: {//1：定期随访
                        nextDate = setNextDate(new Date(),timeSelect, timeChoosed, weeks, sendTimeDays, sendTimeMonths);
                    }
                    break;
                    case 2: {//2：定时随访
                        nextDate = setNextDate(timePick, timeChoosed);
                    }
                    break;
                    case 3: {//3：普通随访
                        nextDate = setNextDate(lastDate, tiemFont, timeNum, timeChoosed);
                    }
                    break;
                    default:{
                        nextDate = setNextDate(lastDate, tiemFont, timeNum, timeChoosed);
                    }
                    break;
                }
                planPatientVo.setNextDate(nextDate);//设置下次随访日期
                if (planInfo.getPlanType() == 1){//随访计划存在过期  宣教不存在过期
                    Integer validDays = Integer.parseInt(rulesMap.get("validDays")+"");
                    if (((new Date().getTime()- nextDate.getTime())/(1*60*60*1000*24)) > validDays){//判断导入患者的时候是否过期
                        planPatientVo.setPlanPatsStatus(12);// 患者失效
                    }
                }
                log.info("随访/宣教患者信息:{}",JSON.toJSONString(planPatientVo));
                planPatientMapper.addPlanPatient(planPatientVo);//将数据存到数据中

            }
            Long delete = redisTemplate.boundHashOps("plan").delete(planInfo.getPlanNum());
//            log.info("是否删除缓存中存的数据:{}", planInfo.getPlanNum(), delete);
        }
    }

    //设置普通随访时间
    private Date setNextDate(Date lastDate, String tiemFont, Integer timeNum, Integer timeChoosed) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(lastDate);
        Date nextDate = null;
        //普通随访
        switch (tiemFont) {
            case "1": {//天
                calendar.add(Calendar.DATE, timeNum);
                calendar.set(Calendar.HOUR_OF_DAY, timeChoosed + 5);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                nextDate = calendar.getTime();
            }
            break;
            case "2": {//星期
                calendar.add(Calendar.WEEK_OF_YEAR, timeNum);
                calendar.set(Calendar.HOUR_OF_DAY, timeChoosed + 5);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                nextDate = calendar.getTime();
            }
            break;
            case "3": {//月
                calendar.add(Calendar.MONTH, timeNum);
                calendar.set(Calendar.HOUR_OF_DAY, timeChoosed + 5);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                nextDate = calendar.getTime();
            }
            break;
        }
        return nextDate;
    }

    //定期随访
    private Date setNextDate(Date nowDate,String timeSelect, Integer timeChoosed, Integer weeks, Integer sendTimeDays, Integer sendTimeMonths) {
        Calendar calendar = Calendar.getInstance();
//        Date nowDate = new Date();
        calendar.setTime(nowDate);
        Date nextDate = null;
        switch (timeSelect) {
            case "1": {//每天
                calendar.add(Calendar.DATE, 1);//默认从明天开始
                calendar.set(Calendar.HOUR_OF_DAY, timeChoosed + 5);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                nextDate = calendar.getTime();
            }
            break;
            case "2": {//每周
                calendar.set(Calendar.DAY_OF_WEEK, weeks + 1);
                calendar.set(Calendar.HOUR_OF_DAY, timeChoosed + 5);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                nextDate = calendar.getTime();

                if (nextDate.getTime() < nowDate.getTime()) {
                    calendar.add(Calendar.WEEK_OF_YEAR, 1);//默认从下周开始
                    nextDate = calendar.getTime();
                }

            }
            break;
            case "3": {//每月
                calendar.set(Calendar.DAY_OF_MONTH, sendTimeDays);
                calendar.set(Calendar.HOUR_OF_DAY, timeChoosed + 5);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);

                nextDate = calendar.getTime();
                if (nextDate.getTime() < nowDate.getTime()) {
                    calendar.add(Calendar.MONTH, 1);//默认从下个月开始
                    nextDate = calendar.getTime();
                }
            }
            break;
            case "4": {//每年
                calendar.set(Calendar.MONTH, sendTimeMonths - 1);//0 表示 一月
                calendar.set(Calendar.DAY_OF_MONTH, sendTimeDays);//超过当月日期则表示则超过日期累计到下个月
                calendar.set(Calendar.HOUR_OF_DAY, timeChoosed + 5);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                nextDate = calendar.getTime();
                if (nextDate.getTime() < nowDate.getTime()) {
                    calendar.add(Calendar.YEAR, 1);//默认从开始
                    nextDate = calendar.getTime();
                }
            }
            break;
        }
        return nextDate;
    }

    //定时随访
    private Date setNextDate(Date timePick, Integer timeChoosed) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(timePick);
        calendar.add(Calendar.HOUR_OF_DAY, timeChoosed + 5+8);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date nextDate = calendar.getTime();
        return nextDate;
    }
}
