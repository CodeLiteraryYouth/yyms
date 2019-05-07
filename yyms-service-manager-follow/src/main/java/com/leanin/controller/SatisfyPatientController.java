package com.leanin.controller;

import com.alibaba.fastjson.JSON;
import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.vo.SatisfyInfoVo;
import com.leanin.domain.vo.SatisfyPatientVo;
import com.leanin.domain.vo.StyInfoRecordVo;
import com.leanin.service.SatisfyPatientService;
import com.leanin.utils.LyOauth2Util;
import com.leanin.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/satisfyPatient")
public class SatisfyPatientController extends BaseController {

    @Autowired
    SatisfyPatientService satisfyPatientService;

    //条件查询 满意度计划患者信息
    @GetMapping("/findList")
    public DataOutResponse findList( Integer currentPage,  Integer pageSize, String satisfyPlanNum,
                                     Integer sendType,  String patientWard, Integer finishType,
                                     String patientName,  String startDateStr,  String endDateStr,
                                     Integer patientSource) {
        SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd");
        Date startDate =null;
        Date endDate =null;
        if (startDateStr != null ){
            try {
                startDate = sdf.parse(startDateStr);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        if (endDateStr != null ){
            try {
                endDate = sdf.parse(endDateStr);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return satisfyPatientService.findList(currentPage,pageSize,satisfyPlanNum,sendType,patientWard,patientName,startDate,endDate,finishType,patientSource);
    }

    /**
     * 修改满意度状态
     * @param patientSatisfyId  满意度计划患者主键
     * @param finishType 满意度患者状态 -1:收案 1：未完成 2：已完成；3:已过期; 4 无法接听 5 号码错误 6 拒绝接听 7 无人接听 8 家属接听 9 患者不合作 10 无联系电话 11 其他
     * @param suggess 处理意见
     * @param styInfoRecordVo 满意度表单填写记录
     * @param formStatus 表单完成状态  1 未完成 2 已完成
     * @return
     */
    @PostMapping("/updateByPid")
    public DataOutResponse updateByPid(@RequestParam("patientSatisfyId") Long patientSatisfyId, Integer finishType, String suggess,
                                       @RequestBody(required = false) StyInfoRecordVo styInfoRecordVo,Integer formStatus){
//        LyOauth2Util.UserJwt user = getUser(request);
//        styInfoRecordVo.setOperatingId(user.getId());
        return satisfyPatientService.updateByPid(patientSatisfyId,finishType,suggess,styInfoRecordVo,formStatus);
    }

    /**
     * 修改满意度计划患者状态
     * @param planPatientId 计划患者主键
     * @param status 状态
     * @return
     */
    @GetMapping("updateStatus")
    public DataOutResponse updateStatus(@RequestParam("planPatientId")Long planPatientId,@RequestParam("status") Integer status){
        return satisfyPatientService.updateStatus(planPatientId,status);
    }

    @GetMapping("/delPatientList")
    public DataOutResponse delPatientList(@RequestParam String patientSatisfyId){
        List<Long> longs1 = JSON.parseArray(patientSatisfyId, Long.class);
//        JSON.parseArray(patientPlanIds, Long[].class);
//        System.out.println(longs1);
//        Long[] longs = JSON.parseObject(patientPlanIds, Long[].class);
//        List<Long> longs = JSON.parseArray(patientPlanIds, Long.class);
//        Long[] longs=new Long[5];
        return satisfyPatientService.delPatientList(longs1);
    }


    @PostMapping("/addPatentList")
    public DataOutResponse addPatentList(@RequestBody List<SatisfyPatientVo> satisfyPatientVos){
        return satisfyPatientService.addPatentList(satisfyPatientVos);
    }


    /**
     * 根据计划患者主键查询满意度患者信息
     * @param planPatientId
     * @return
     */
    @GetMapping("/findById")
    public DataOutResponse findById(@RequestParam("planPatientId")Long planPatientId){
        return satisfyPatientService.findById(planPatientId);
    }



    private LyOauth2Util.UserJwt getUser(HttpServletRequest httpServletRequest){
        LyOauth2Util lyOauth2Util = new LyOauth2Util();
        LyOauth2Util.UserJwt userJwt= lyOauth2Util.getUserJwtFromHeader(httpServletRequest);
        return userJwt;
    }

}
