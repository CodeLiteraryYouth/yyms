package com.leanin.controller;

import com.leanin.domain.LeaninCallLogInfoDao;
import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.response.ReturnFomart;
import com.leanin.dto.CallLogInfoDto;
import com.leanin.dto.query.QueryCallLogInfoDto;
import com.leanin.service.CallLogInfoService;
import com.leanin.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName 通话记录
 * @Description 通话记录
 * @Author 刘壮
 * @Date 2019/4/10 10:34
 * @ModifyDate 2019/4/10 10:34
 * @Version 1.0
 */
@RestController
@RequestMapping("callLogInfo")
public class CallLogInfoController extends BaseController {
    @Autowired
    private CallLogInfoService callLogInfoService;

    /**
     * 分页查询
     * @param request
     * @param queryCallLogInfoDto
     * @return
     */
    @RequestMapping(value = "listPage")
    public DataOutResponse findList(HttpServletRequest request,
                                    @ModelAttribute QueryCallLogInfoDto queryCallLogInfoDto
                                        ){
            try {
               return  callLogInfoService.findList(queryCallLogInfoDto);
            }catch (Exception e){
                return ReturnFomart.retParam(404,e.getMessage());
            }
        }

    /**
     * 查看详情
     * @param callLogInfoId
     * @return
     */
    @RequestMapping(value = "detail")
    public DataOutResponse detail(@RequestParam("callLogInfoId") Long callLogInfoId){
            try {
                return  callLogInfoService.detail(callLogInfoId);
            }catch (Exception e){
                return ReturnFomart.retParam(404,e.getMessage());
            }
        }

    /**
     * 修改
     * @param leaninCallLogInfoDao
     * @return
     */
    @RequestMapping(value = "update")
    public DataOutResponse update(@ModelAttribute LeaninCallLogInfoDao leaninCallLogInfoDao
                                      ){
            try {
                return  callLogInfoService.update(leaninCallLogInfoDao);
            }catch (Exception e){
                return ReturnFomart.retParam(404,e.getMessage());
            }
        }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @RequestMapping(value = "delete")
    public DataOutResponse delete (@RequestParam("ids") String ids){
        try {
            return  callLogInfoService.delete(ids);
        }catch (Exception e){
            return ReturnFomart.retParam(404,e.getMessage());
        }
    }
}