package com.leanin.service.impl;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.leanin.domain.CallBack;
import com.leanin.domain.CallBackDeal;
import com.leanin.domain.dto.CallBackDto;
import com.leanin.domain.excel.BookPatientExcel;
import com.leanin.domain.excel.ComplainExcel;
import com.leanin.domain.excel.PraiseExcel;
import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.response.ReturnFomart;
import com.leanin.mapper.CallBackMapper;
import com.leanin.utils.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.leanin.service.CallBackService;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
@Slf4j
public class CallBackServiceImpl implements CallBackService {

    @Autowired
    private CallBackMapper callBackMapper;


    @Override
    public DataOutResponse findBackList(int page, int pageSize, String beginDate, String endDate,
                                        Integer dealStatus, String accuseWard, String backNum, int status) {
        log.info("起始时间为:"+beginDate+"结束时间为:"+endDate+"处理状态:"+dealStatus+"科室:"+accuseWard+"单号:"+backNum);
        PageHelper.startPage(page, pageSize);
        List<CallBack> backList=callBackMapper.findBackList(beginDate, endDate, dealStatus, accuseWard, backNum,status);
        PageInfo pageInfo=new PageInfo<>(backList);
        return ReturnFomart.retParam(200, pageInfo);
    }

    @Override
    public DataOutResponse findBackById(String backNum,int status) {
        log.info("单号为:"+backNum);
        CallBack callBack=callBackMapper.findBackById(backNum,status);
        if (callBack == null){
            return ReturnFomart.retParam(1,"信息不存在");
        }
        log.info("查询的投诉表扬信息为:"+JSON.toJSONString(callBack));
        return ReturnFomart.retParam(200, callBack);
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public DataOutResponse addCallBack(CallBack callBack) {
        log.info("添加的投诉表扬信息为:"+JSON.toJSONString(callBack));
//        String uuid = UUIDUtils.getUUID();
//        callBack.setCallBackNum(uuid);
        CallBack callBackDto=callBackMapper.findBackById(callBack.getCallBackNum(),callBack.getStatus());
        if(callBackDto != null) {
            return ReturnFomart.retParam(4000, callBackDto);
        }
        callBack.setPushDate(new Date());
        callBack.setCallBackDate(new Date());
        callBackMapper.addCallBack(callBack);
        //同时保存一份单号进入处理表单意见
        CallBackDeal callBackDeal=new CallBackDeal();
        callBackDeal.setCallBackNum(callBack.getCallBackNum());
        callBackDeal.setDealStatus(0);
        callBackMapper.addCallBackDeal(callBackDeal);
        return ReturnFomart.retParam(200, callBack);
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public DataOutResponse updateCallBackDeal(CallBackDeal callBackDeal) {
        log.info("修改的处理人信息为:"+JSON.toJSONString(callBackDeal));
        CallBackDeal record = callBackMapper.findBackDetail(callBackDeal.getCallBackNum());
        if (record == null){
            return ReturnFomart.retParam(300, "数据不存在");
        }
        callBackDeal.setDealNum(record.getDealNum());
        callBackMapper.updateCallBackDeal(callBackDeal);
        return ReturnFomart.retParam(200, callBackDeal);
    }

    @Override
    public DataOutResponse findBackDealByBackNum(String backNum) {
        CallBackDeal record = callBackMapper.findBackDetail(backNum);
        return ReturnFomart.retParam(200, record);
    }

    @Override
    public void exportCallBackExcel(String beginDate, String endDate, Integer dealStatus, String accuseWard, String backNum, int status
                                    , HttpServletRequest request, HttpServletResponse response) {
        String fileName = null;
        ServletOutputStream out = null;
        ExcelWriter writer =null;
        try {
            if (status == 1){//投诉
                fileName = new String("投诉列表".getBytes(), "ISO-8859-1");
            }else{//表扬
                fileName = new String("表扬列表".getBytes(), "ISO-8859-1");
            }
            out = response.getOutputStream();
            writer = new ExcelWriter(out, ExcelTypeEnum.XLS, true);
        } catch (IOException e) {
            log.info( e.getMessage());
            e.printStackTrace();
        }
        if (status == 1){//投诉
            log.info("投诉Excel表格导出");
            List<ComplainExcel> list = callBackMapper.exportComplainExcel(beginDate,endDate,dealStatus,accuseWard,backNum,status);
            Sheet sheet1 = new Sheet(1, 0, ComplainExcel.class);
            sheet1.setSheetName("第一个sheet");
            writer.write(list, sheet1);
        }else if (status == 2){//表扬
            log.info("表扬Excel表格导出");
            List<PraiseExcel> list = callBackMapper.exportPraiseExcel(beginDate,endDate,dealStatus,accuseWard,backNum,status);
            Sheet sheet1 = new Sheet(1, 0, PraiseExcel.class);
            sheet1.setSheetName("第一个sheet");
            writer.write(list, sheet1);
        }else {
            log.info("类型不存在");
            return;
        }
        try {
            writer.finish();
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            response.setHeader("Content-disposition", "attachment;filename="+fileName+".xls");
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
