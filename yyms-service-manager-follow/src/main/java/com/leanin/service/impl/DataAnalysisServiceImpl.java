package com.leanin.service.impl;

import com.leanin.domain.common.AnalysisVo;
import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.response.ReturnFomart;
import com.leanin.mapper.FollowRecordMapper;
import com.leanin.mapper.PlanPatientMapper;
import com.leanin.service.DataAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DataAnalysisServiceImpl implements DataAnalysisService {

    @Autowired
    FollowRecordMapper followRecordMapper;

    @Autowired
    PlanPatientMapper planPatientMapper;

    @Override
    public DataOutResponse followAnalysis(Integer patientSource, String planNum, String dept, String startDateStr, String endDateStr) {
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
        List<AnalysisVo> list = followRecordMapper.findCountByParam(patientSource, planNum, dept, startDate, endDate);
        List<AnalysisVo> list1 = planPatientMapper.findCountByParam(patientSource, planNum, dept, startDate, endDate);
        for (int i = -1; i < 12; i++) {
            int count =0;
            for (AnalysisVo analysisVo : list) {
//                dataMap.put(analysisVo.getStatus(),dataMap.get(analysisVo.getStatus())+analysisVo.getCount());
                if (analysisVo.getStatus() == i){
                    count = count +analysisVo.getCount();
                }
            }
            for (AnalysisVo analysisVo : list1) {
//                dataMap.put(analysisVo.getStatus(),dataMap.get(analysisVo.getStatus())+analysisVo.getCount());
                if (analysisVo.getStatus() == i) {
                    count = count + analysisVo.getCount();
                }
            }
            dataMap.put(i, count);
        }


        return ReturnFomart.retParam(200, dataMap);
    }

    @Override
    public DataOutResponse userFollowAnalysis(Long userId,String time) {
        Map<Integer,Double> dataMap =new HashMap<>();

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

        return ReturnFomart.retParam(200,dataMap);
    }
}
