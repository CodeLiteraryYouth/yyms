package com.leanin.controller;

import com.leanin.domain.dao.RemarkDao;
import com.leanin.domain.response.DataOutResponse;
import com.leanin.service.PlanRemarkService;
import com.leanin.utils.LyOauth2Util;
import com.leanin.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 计划评价
 * @author CPJ.
 * @date 2019/6/5.
 * @time 16:24.
 */
@RestController
@RequestMapping("/planRemark")
public class PlanRemarkController extends BaseController {


    @Autowired
    PlanRemarkService planRemarkService;

    /**
     * 添加计划评论
     * @param remarkDao
     * @return
     */
    @PostMapping("/addRemark")
    public DataOutResponse addRemark(@RequestBody RemarkDao remarkDao){
        LyOauth2Util.UserJwt user = getUser(request);
        remarkDao.setRemarkerId(user.getId());
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
    @GetMapping("/findByParam")
    public DataOutResponse findByParam(String planNum,String formNum,Integer planType,String questionId,Integer page,Integer pageSize){
        return planRemarkService.findByParam(planNum,formNum,planType,questionId,page,pageSize);
    }

    private LyOauth2Util.UserJwt getUser(HttpServletRequest httpServletRequest){
        LyOauth2Util lyOauth2Util = new LyOauth2Util();
        LyOauth2Util.UserJwt userJwt= lyOauth2Util.getUserJwtFromHeader(httpServletRequest);
        return userJwt;
    }

}
