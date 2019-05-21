package com.leanin.service.impl;

import com.alibaba.fastjson.JSON;
import com.leanin.client.ManagerPatientClient;
import com.leanin.domain.analysis.DeptAnalysis;
import com.leanin.domain.common.AnalysisVo;
import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.response.ReturnFomart;
import com.leanin.mapper.*;
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

    @Autowired
    MsgRecordMapper msgRecordMapper;

    @Autowired
    WxSendMapper wxSendMapper;

    @Autowired
    LeaninCallLogInfoDaoMapper leaninCallLogInfoDaoMapper;

    @Autowired
    ManagerPatientClient managerPatientClient;

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
        List<DeptAnalysis> data =new ArrayList<>();
        //随访患者科室统计
        List<DeptAnalysis> follow = planPatientMapper.deptFollowAnalysis(patientSource,planNum,dept,startDate,endDate);
        data.addAll(follow);
        //随访记录患者科室统计
        List<DeptAnalysis> followRecords = followRecordMapper.deptFollowAnalysis(patientSource,planNum,dept,startDate,endDate);
        data.addAll(followRecords);
        //随访短信患者科室统计 -3
        List<DeptAnalysis> msgRecord = msgRecordMapper.deptFollowAnalysis(patientSource,planNum,dept,startDate,endDate,1);
        data.addAll(msgRecord);
        //微信发送患者科室统计 -4
        List<DeptAnalysis> wxSend = wxSendMapper.deptFollowAnalysis(patientSource,planNum,dept,startDate,endDate,1);
        data.addAll(wxSend);
        //拨打电话和时长科室统计 -5
        List<DeptAnalysis> callLog = leaninCallLogInfoDaoMapper.deptFollowAnalysis(patientSource,planNum,dept,startDate,endDate,1);
        data.addAll(callLog);
        List<DeptAnalysis> inOutCount = new ArrayList<>();
        switch (patientSource){
            case 1: //出院  -6
                inOutCount = managerPatientClient.findInOutCount(dept, startDate, endDate, 2);
                data.addAll(inOutCount);
                break;
            case 2://门诊

                break;
            case 3://在院
                inOutCount = managerPatientClient.findInOutCount(dept, startDate, endDate, 1);
                data.addAll(inOutCount);
                break;
            case 4://体检
                break;
            case 5://建档
                break;
            case 6://签约
                break;
            case 7://转入
                break;
            case 8://转出
                break;
            case 9://患者管理
                break;
            default://其他情况
                break;
        }
        //随访成功人次  -7
        List<DeptAnalysis> finishCount = planPatientMapper.findFinishCountByParam(patientSource,planNum,dept,startDate,endDate);
        data.addAll(finishCount);
        return ReturnFomart.retParam(200,data);
    }

    @Override
    public DataOutResponse followUpRate(Integer patientSource, String planNum, String dept, String year) {
        log.info("随访率的全年月份展示,过滤的条件为=>患者来源:"+patientSource+",计划主键:"+planNum+",患者科室:"+dept+",年份:"+year);
        List<DeptAnalysis> data = new ArrayList<>();
        List<DeptAnalysis> inOutCount = new ArrayList<>();
        switch (patientSource){
            case 1: //出院  -6
                inOutCount = managerPatientClient.findInOutCountByYear(dept,year , 2);
                log.info("出院人数集合:{}", JSON.toJSONString(inOutCount));
                data.addAll(inOutCount);
                break;
            case 2://门诊

                break;
            case 3://在院
                inOutCount = managerPatientClient.findInOutCountByYear(dept,year , 1);
                log.info("在院人数集合:{}", JSON.toJSONString(inOutCount));
                data.addAll(inOutCount);
                break;
            case 4://体检
                break;
            case 5://建档
                break;
            case 6://签约
                break;
            case 7://转入
                break;
            case 8://转出
                break;
            case 9://患者管理
                break;
            default://其他情况
                break;
        }
        //随访成功  -7
        List<DeptAnalysis> success = planPatientMapper.findInOutCountByYear(patientSource,planNum,dept,year);
        log.info("随访成功人数集合",JSON.toJSONString(success));
        data.addAll(success);
        return ReturnFomart.retParam(200,data);
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
