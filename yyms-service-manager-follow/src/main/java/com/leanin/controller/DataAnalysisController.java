package com.leanin.controller;


import com.leanin.domain.response.DataOutResponse;
import com.leanin.service.DataAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("dataAnalysis")
public class DataAnalysisController {

    @Autowired
    DataAnalysisService dataAnalysisService;


    /**
     * 随访计划条件分析
     * @param patientSource  患者来源
     * @param planNum       计划主键
     * @param dept          科室
     * @param startDate     开始时间
     * @param endDate       结束时间
     * @param planType      计划类型  1随访  3满意度
     * @return
     */
//    @PreAuthorize("hasAnyAuthority('root','dataAly')")
    @GetMapping("followAnalysis")
    public DataOutResponse followAnalysis(Integer patientSource,String planNum,String dept,String startDate,String endDate,@RequestParam("planType") Integer planType,Integer formStatus,Long userId,Integer isAll){
        return dataAnalysisService.followAnalysis(patientSource,planNum,dept,startDate,endDate,planType,formStatus,userId,isAll);
    }


    /**
     * 根据用户id进行随访分析
     * @param userId 用户主键
     * @param planType      计划类型  1随访  3满意度
     * @return
     */
//    @PreAuthorize("hasAnyAuthority('root','userDataAly')")
    @GetMapping("userFollowAnalysis")
    public DataOutResponse userFollowAnalysis(@RequestParam("userId") Long userId,@RequestParam("time") String time,@RequestParam("planType") Integer planType){
        return dataAnalysisService.userFollowAnalysis(userId,time,planType);
    }

    /**
     * 投诉表扬统计
     * @param type  类型 1 投诉  2 表扬
     * @param dealStatus 处理状态 0 未处理  1 已处理
     * @return
     */
    @GetMapping("callBackAnalysis")
    public DataOutResponse callBackAnalysis(Integer type,Integer dealStatus){
        return dataAnalysisService.callBackAnalysis(type,dealStatus);
    }









}
