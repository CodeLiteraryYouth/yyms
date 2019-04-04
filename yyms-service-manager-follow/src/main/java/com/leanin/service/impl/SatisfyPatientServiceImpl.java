package com.leanin.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.response.ReturnFomart;
import com.leanin.domain.vo.SatisfyInfoVo;
import com.leanin.domain.vo.SatisfyPatientVo;
import com.leanin.domain.vo.SatisfyPlanVo;
import com.leanin.domain.vo.StyInfoRecordVo;
import com.leanin.mapper.SatisfyPatientMapper;
import com.leanin.mapper.SatisfyPlanMapper;
import com.leanin.mapper.StyInfoRecordMapper;
import com.leanin.service.SatisfyPatientService;
import com.leanin.utils.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class SatisfyPatientServiceImpl implements SatisfyPatientService {

    @Autowired
    SatisfyPatientMapper satisfyPatientMapper;

    @Autowired
    StyInfoRecordMapper styInfoRecordMapper;

    @Autowired
    SatisfyPlanMapper satisfyPlanMapper;

    //条件查询满意度计划的 患者信息
    @Override
    public DataOutResponse findList(Integer currentPage, Integer pageSize, String satisfyPlanNum, Integer sendType, String patientWard, String patientName, Date startDate, Date endDate, Integer finishType) {
        if (currentPage == null || currentPage <=0){//当前页
            currentPage=1;
        }
        if (pageSize == null || pageSize<=0){//每页展示数据条数
            pageSize=10;
        }
//        if("null".equals(sendType)){
//            sendType=null;
//        }
//        if("null".equals(patientWard)){
//            patientWard=null;
//        }
//        if("null".equals(patientName)){
//            patientName=null;
//        }
//        if("null".equals(startDate)){
//            startDate=null;
//        }
//        if("null".equals(endDate)){
//            endDate=null;
//        }

        PageHelper.startPage(currentPage,pageSize);
        Page<SatisfyPatientVo> page = (Page<SatisfyPatientVo>) satisfyPatientMapper.findList(satisfyPlanNum,sendType,patientWard,patientName,startDate,endDate,finishType);

        //封装参数
        Map dataMap =new HashMap();
        long total = page.getTotal();
        List<SatisfyPatientVo> list = page.getResult();
        dataMap.put("totalCount",total);
        dataMap.put("list",list);
        return ReturnFomart.retParam(200, dataMap);
    }

    @Override
    public DataOutResponse delPatientList(List<Long> longs1) {
        if (longs1 ==null || longs1.size() <= 0){
            return ReturnFomart.retParam(4000, "请选择患者再删除");
        }

        for (Long aLong : longs1) {//修改状态
            satisfyPatientMapper.updatePatientStatus(aLong);
        }
        return ReturnFomart.retParam(200,"删除成功！");
    }

    @Override
    @Transactional
    public DataOutResponse updateByPid( Long patientSatisfyId, Integer finishType,String suggess, StyInfoRecordVo styInfoRecordVo) {
        SatisfyPatientVo satisfyPatientVo = satisfyPatientMapper.findByStyPatId(patientSatisfyId);
        if (satisfyPatientVo == null){
            return ReturnFomart.retParam(300,"信息不存在");
        }
        String uuid = UUIDUtils.getUUID();
        styInfoRecordVo.setSatisfyId(uuid);
        styInfoRecordVo.setFormStatus(satisfyPatientVo.getFormStatus());
        styInfoRecordVo.setCreateTime(new Date());
        styInfoRecordVo.setIdCard(satisfyPatientVo.getIdCard());
        styInfoRecordVo.setInhosNo(satisfyPatientVo.getInhosNo());
        styInfoRecordVo.setPatientId(satisfyPatientVo.getPatientId());
        styInfoRecordMapper.addRecord(styInfoRecordVo);
        if (finishType != null){
            satisfyPatientVo.setFinishType(finishType);
        }
        if (suggess != null){
            satisfyPatientVo.setSuggess(suggess);
        }
        satisfyPatientMapper.updateByPrimaryKeySelective(satisfyPatientVo);
        return ReturnFomart.retParam(200,"操作成功");
    }

    @Override
    @Transactional
    public DataOutResponse addPatentList(List<SatisfyPatientVo> satisfyPatientVos) {
        for (SatisfyPatientVo satisfyPatientVo : satisfyPatientVos) {
            SatisfyPlanVo satisfyPlan = satisfyPlanMapper.findSatisfyPlanById(satisfyPatientVo.getSatisfyPlanNum());
            SatisfyPatientVo vo = satisfyPatientMapper.findByPnumAndPid(satisfyPatientVo.getPatientId(),satisfyPatientVo.getSatisfyPlanNum());
            if (vo != null ){
                return ReturnFomart.retParam(3003,"计划患者已存在，请勿重复导入数据");
            }
            satisfyPatientVo.setFinishType(1);
            satisfyPatientVo.setFormStatus(1);
            satisfyPatientVo.setPatientStatus(0);
            satisfyPatientVo.setFormId(satisfyPlan.getSatisfyNum());
            satisfyPatientVo.setSendType(1);
            satisfyPatientVo.setPatientDateTime(setDate(satisfyPlan,satisfyPatientVo.getPatientDateTime()));
            satisfyPatientMapper.insertSelective(satisfyPatientVo);
        }
        return ReturnFomart.retParam(200,"操作成功");
    }

    private Date setDate(SatisfyPlanVo satisfyPlan,Date lastDate){
        String rulesText = satisfyPlan.getRulesText();
        Map rulesMap = JSON.parseObject(rulesText, Map.class);

        String tiemFont = (String) rulesMap.get("tiemFont");//获取下次任务的时间 1天
        String timeNumStr = (String) rulesMap.get("timeNum");
        int timeChoosed = Integer.parseInt((String) rulesMap.get("timeChoosed")); //1 6:00， 2 7:00 一次后推直到 16 21:00
        String timeSelect = (String) rulesMap.get("timeSelect");//1出院
        String rangeDays = (String) rulesMap.get("rangeDays");//范围天数
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(lastDate);
        calendar.add(Calendar.DATE, Integer.parseInt(timeNumStr));
        calendar.set(Calendar.HOUR_OF_DAY, timeChoosed + 5);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date time = calendar.getTime();
        return time;
    }
}
