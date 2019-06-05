package com.leanin.controller;

import com.leanin.domain.dao.RemarkDao;
import com.leanin.domain.response.DataOutResponse;
import com.leanin.service.PlanRemarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 计划评价
 * @author CPJ.
 * @date 2019/6/5.
 * @time 16:24.
 */
@RestController
@RequestMapping("/planRemark")
public class PlanRemarkController {


    @Autowired
    PlanRemarkService planRemarkService;

    /**
     * 添加计划评论
     * @param remarkDao
     * @return
     */
    @PostMapping("/addRemark")
    public DataOutResponse addRemark(@RequestBody RemarkDao remarkDao){
        return planRemarkService.addRemark(remarkDao);
//        return null;
    }

    /**
     * 计划评论查询
     * @param planNum  计划号
     * @param formNum 表单号
     * @param planType 计划类型  1 随访 2 宣教  3 满意度
     * @param questionId 题目id
     * @param page 当前页 默认 第一页
     * @param pageSize 每页展示条数 默认10条
     * @return
     */
    @RequestMapping("/findByParam")
    public DataOutResponse findByParam(String planNum,String formNum,Integer planType,String questionId,Integer page,Integer pageSize){
        return planRemarkService.findByParam(planNum,formNum,planType,questionId,page,pageSize);
    }


}
