package com.leanin.controller;

import com.alibaba.fastjson.JSON;
import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.vo.FormRecordVo;
import com.leanin.domain.vo.PlanPatientVo;
import com.leanin.service.PlanPatientService;
import com.leanin.utils.LyOauth2Util;
import com.leanin.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("planPatient")
public class PlanPatientController extends BaseController {

    @Autowired
    PlanPatientService planPatientService;

    /**
     * 根据planId查询计划患者信息  分页查询
     * @param planNum
     * @param planPatsStatus
     * @param currentPage
     * @param pageSize
     * @return
     */
//    @PreAuthorize("hasAnyAuthority('root')")
    @GetMapping("findPlanPatientListByPlanId")
    public DataOutResponse findPlanPatientListByPlanId(@RequestParam(required=false) String planNum, @RequestParam(required=false) Integer planPatsStatus,
                                                       @RequestParam(required=false) Integer currentPage, @RequestParam(required=false) Integer pageSize,
                                                       @RequestParam(required=false) String patientName){
        return planPatientService.findPlanPatientListByPlanId(planNum,planPatsStatus,currentPage,pageSize,patientName);
    }

    //根据planId查询计划患者信息  全部查询 供其他模块调用
//    @PreAuthorize("hasAuthority('root')")
    @GetMapping("findListByPlanId")
    public DataOutResponse findListByPlanId(@RequestParam(required=true) String planNum){
        return planPatientService.findListByPlanId(planNum);
    }

    /**
     * 批量删除
     * @param patientPlanIds
     * @return
     */
    @GetMapping("delPatientList")
    public DataOutResponse delPatientList(@RequestParam String patientPlanIds,Integer planPatsStatus){
        List<Long> longs1 = JSON.parseArray(patientPlanIds, Long.class);
//        JSON.parseArray(patientPlanIds, Long[].class);
//        System.out.println(longs1);
//        Long[] longs = JSON.parseObject(patientPlanIds, Long[].class);
//        List<Long> longs = JSON.parseArray(patientPlanIds, Long.class);
//        Long[] longs=new Long[5];
        return planPatientService.delPatientList(longs1,planPatsStatus);
    }

    @PostMapping("addPatientList")
    public DataOutResponse addPatientList(@RequestBody List<PlanPatientVo> PlanPatients){
        return planPatientService.addPatientList(PlanPatients);
    }


    /**
     * 我的随访
     * @return
     */
    /*@GetMapping("findPlanPatientList")
    public DataOutResponse findPlanPatientList(Date startDate,Date endDate,String planNum,String patientName){
        return planPatientService.findPlanPatientList(startDate,endDate,planNum,patientName);
    }*/

    /**
     * 根据患者id查询患者信息 和病史
     * @param patientId
     * @return
     */
    @GetMapping("findPlanPatientById")
    public DataOutResponse findPlanPatientById(@RequestParam Long patientId,@RequestParam Integer patientSource,String planNum,Integer planType,@RequestParam Integer type){
        return planPatientService.findPlanPatientById(patientId,patientSource,planNum,planType,type);
    }

    /**
     * 根据患者id查询患者His信息 和病史
     * @param patientId
     * @return
     */
    @GetMapping("findHisPlanPatientById")
    public DataOutResponse findHisPlanPatientById(@RequestParam Long patientId,@RequestParam Integer patientSource){
        return planPatientService.findPlanHisPatientById(patientId,patientSource);
    }

    /**
     * 处理表单信息以及随访状态 表单状态  处理意见的 接口
     * @param patientPlanId  计划患者主键
     * @param followType  随访状态 -1:收案 0：未发送表单或者短信前的状态 1：待随访 2：已完成；3:已过期; 4 无法接听 5 号码错误 6 拒绝接听 7 无人接听 8 家属接听 9 患者不合作 10 无联系电话 11 其他
     * @param handleSugges 处理意见
     * @param formStatus  表单状态  1 未完成 2 已完成
     * @param formRecordVo 表单填写记录
     * @return
     */
    @PostMapping("updatePatById")
    public DataOutResponse updatePlanPatient(@RequestParam Long patientPlanId, Integer followType, String handleSugges,Integer formStatus,
                                             @RequestBody(required = false) FormRecordVo formRecordVo){
        return planPatientService.updatePlanPatient(patientPlanId,followType,handleSugges,formRecordVo,formStatus);
    }

    /**
     * 修改计划患者状态
     * @param planPatientId  计划患者主键
     * @param status 状态
     * @return
     */
    @GetMapping("updateStatus")
    public DataOutResponse updateStatus(@RequestParam("planPatientId") Long planPatientId,@RequestParam("status")Integer status){
        return planPatientService.updateStatus(planPatientId,status);
    }



    private LyOauth2Util.UserJwt getUser(HttpServletRequest httpServletRequest){
        LyOauth2Util lyOauth2Util = new LyOauth2Util();
        LyOauth2Util.UserJwt userJwt= lyOauth2Util.getUserJwtFromHeader(httpServletRequest);
        return userJwt;
    }
}
