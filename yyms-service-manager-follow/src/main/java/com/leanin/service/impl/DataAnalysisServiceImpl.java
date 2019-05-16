package com.leanin.service.impl;

import com.leanin.domain.analysis.DeptAnalysis;
import com.leanin.domain.common.AnalysisVo;
import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.response.ReturnFomart;
import com.leanin.mapper.CallBackMapper;
import com.leanin.mapper.FollowRecordMapper;
import com.leanin.mapper.PlanPatientMapper;
import com.leanin.mapper.SatisfyPatientMapper;
import com.leanin.service.DataAnalysisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Slf4j
public class DataAnalysisServiceImpl implements DataAnalysisService {

    @Autowired
    FollowRecordMapper followRecordMapper;

    @Autowired
    PlanPatientMapper planPatientMapper;

    @Autowired
    SatisfyPatientMapper satisfyPatientMapper;

    @Autowired
    CallBackMapper callBackMapper;

    @Override
    public DataOutResponse followAnalysis(Integer patientSource, String planNum, String dept, String startDateStr, String endDateStr,Integer planType,Integer formStatus,Long userId,Integer isAll) {
        log.info("传递的参数:{}","患者来源:"+patientSource,"计划主键:"+planNum,"患者科室"+dept,"开始日期"+startDateStr,"结束日期"+endDateStr,"计划类型"+planType,"表单状态"+formStatus,"用户主键"+userId);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date startDate = null;
        Date endDate = null;
        if (startDateStr != null) {
            try {
                startDate = sdf.parse(startDateStr);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        if (endDateStr != null) {
            try {
                endDate = sdf.parse(endDateStr);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        Map<Integer,Integer> dataMap = new HashMap();
        switch (planType){
            case 1 ://随访数据分析
                dataMap = follow(dataMap,patientSource,planNum,dept,startDate,endDate,planType,formStatus,userId,isAll);
                break;
            case 2://宣教数据分析
                dataMap = follow(dataMap,patientSource,planNum,dept,startDate,endDate,planType,formStatus,userId,isAll);
                break;
            case 3://满意度数据分析
                dataMap = satisfy(dataMap,patientSource,planNum,dept,startDate,endDate,isAll);
                break;
            default:
                return ReturnFomart.retParam(2010,"数据不存在");
//                break;
        }
        return ReturnFomart.retParam(200, dataMap);
    }

    private Map<Integer,Integer> follow(Map<Integer,Integer> dataMap,Integer patientSource, String planNum, String dept,Date startDate,Date endDate,Integer planType,Integer formStatus,Long userId,Integer isAll){
        List<AnalysisVo> list =new ArrayList<>();
        if (isAll != null){//不包含已经随访的记录
//            List<AnalysisVo> list = new ArrayList<>();
        }else{//包含已经随访的记录
            list = followRecordMapper.findCountByParam(patientSource, planNum, dept, startDate, endDate,planType,formStatus,userId);
        }
        List<AnalysisVo> list1 = planPatientMapper.findCountByParam(patientSource, planNum, dept, startDate, endDate,planType,formStatus,userId);
        for (int i = -1; i < 12; i++) {
            int count =0;
            if (list.size() >0){
                for (AnalysisVo analysisVo : list) {
//                dataMap.put(analysisVo.getStatus(),dataMap.get(analysisVo.getStatus())+analysisVo.getCount());
                    if (analysisVo.getStatus() == i){
                        count = count +analysisVo.getCount();
                    }
                }
            }

            if (list1.size() > 0){
                for (AnalysisVo analysisVo : list1) {
//                dataMap.put(analysisVo.getStatus(),dataMap.get(analysisVo.getStatus())+analysisVo.getCount());
                    if (analysisVo.getStatus() == i) {
                        count = count + analysisVo.getCount();
                    }
                }
            }

            dataMap.put(i, count);
        }
        return dataMap;
    }

    private Map<Integer,Integer> satisfy(Map<Integer,Integer> dataMap,Integer patientSource, String planNum, String dept,Date startDate,Date endDate,Integer isAll){
        List<AnalysisVo> list = satisfyPatientMapper.findCountByParam(patientSource, planNum, dept, startDate, endDate);
        for (int i = -1; i < 12; i++) {
            int count =0;
            for (AnalysisVo analysisVo : list) {
                if (analysisVo.getStatus() == i){
                    count = count +analysisVo.getCount();
                }
            }
            dataMap.put(i, count);
        }
        return dataMap;
    }

    @Override
    public DataOutResponse userFollowAnalysis(Long userId,String time,Integer planType) {
        Map<Integer,Double> dataMap =new HashMap<>();
        switch (planType){
            case 1 :
                dataMap = userFollow(dataMap,userId,time);
                break;
            case 3:
                dataMap = userSatisfy(dataMap,userId,time);
                break;
            default:
                return ReturnFomart.retParam(2010,"数据不存在");

        }


        return ReturnFomart.retParam(200,dataMap);
    }

    @Override
    public DataOutResponse callBackAnalysis(Integer type, Integer dealStatus) {
        Integer count = callBackMapper.callBackAnalysis(type,dealStatus);
        return ReturnFomart.retParam(200,count);
    }

    @Override
    public DataOutResponse deptFollowAnalysis(Integer patientSource, String planNum, String dept, String startDate, String endDate) {
        List<DeptAnalysis> list = planPatientMapper.deptFollowAnalysis(patientSource,planNum,dept,startDate,endDate);
        return null;
    }

    private Map<Integer,Double> userFollow(Map<Integer,Double> dataMap,Long userId,String time){
        List<AnalysisVo> list = followRecordMapper.findUserCount(userId,time);
        List<AnalysisVo> list1 = planPatientMapper.findUserCount(userId,time);
        for (int i = 1; i < 13; i++) {
            double success =0;
            double total =0;
            for (AnalysisVo analysisVo : list) {
                if (analysisVo.getMonth() == i && analysisVo.getStatus() == 2){
                    success = success+analysisVo.getCount();
                }
                total = total+analysisVo.getCount();
            }
            for (AnalysisVo analysisVo : list1) {
                if (analysisVo.getMonth() == i && analysisVo.getStatus() == 2){
                    success = success+analysisVo.getCount();
                }
                total = total+analysisVo.getCount();
            }
            dataMap.put(i,success/total);
        }
        return dataMap;
    }

    private Map<Integer,Double> userSatisfy(Map<Integer,Double> dataMap,Long userId,String time){
        List<AnalysisVo> list = satisfyPatientMapper.findUserCount(userId,time);
//        List<AnalysisVo> list1 = planPatientMapper.findUserCount(userId,time);
        for (int i = 1; i < 13; i++) {
            double success =0;
            double total =0;
            for (AnalysisVo analysisVo : list) {
                if (analysisVo.getMonth() == i && analysisVo.getStatus() == 2){
                    success = success+analysisVo.getCount();
                }
                total = total+analysisVo.getCount();
            }
            /*for (AnalysisVo analysisVo : list1) {
                if (analysisVo.getMonth() == i && analysisVo.getStatus() == 2){
                    success = success+analysisVo.getCount();
                }
                total = total+analysisVo.getCount();
            }*/
            dataMap.put(i,success/total);
        }
        return dataMap;
    }
}
