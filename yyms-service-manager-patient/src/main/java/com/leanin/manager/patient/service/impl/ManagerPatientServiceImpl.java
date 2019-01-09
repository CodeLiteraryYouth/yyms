package com.leanin.manager.patient.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.response.ReturnFomart;
import com.leanin.manager.patient.dao.ManagerPatientMapper;
import com.leanin.manager.patient.service.ManagerPatientService;
import org.apache.http.client.utils.HttpClientUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ManagerPatientServiceImpl implements ManagerPatientService {

    @Autowired
    ManagerPatientMapper managerPatientMapper;


    @Override
    public DataOutResponse findListByParam(Map paramMap) {
        //设置当前页  默认显示第一页
        Integer currentPage = (Integer) paramMap.get("currentPage");
        if (currentPage <= 0) {
            currentPage = 1;
        }
        //每页显示数据大小 默认每页显示10条数据
        Integer pageSize = (Integer) paramMap.get("pageSize");
        if (pageSize<=0){
            pageSize=10;
        }
        //分页查询
        PageHelper.startPage(currentPage,pageSize);
        Page<Map> page = (Page<Map>) managerPatientMapper.findListByParam(paramMap);
        long totalPage = page.getTotal();
        List<Map> list = page.getResult();
        Map data=new HashMap();
        data.put("totalCount",totalPage);
        data.put("list",list);



        return ReturnFomart.retParam(200,data);
    }

    @Override
    public DataOutResponse findOutHosPatientByParam(Map paramMap) {
        //设置当前页  默认显示第一页
        Integer currentPage = (Integer) paramMap.get("currentPage");
        if (currentPage <= 0) {
            currentPage = 1;
        }
        //每页显示数据大小 默认每页显示10条数据
        Integer pageSize = (Integer) paramMap.get("pageSize");
        if (pageSize<=0){
            pageSize=10;
        }
        PageHelper.startPage(currentPage,pageSize);
        Page<Map> page= (Page<Map>) managerPatientMapper.findOutHosPatientByParam(paramMap);
        Map data=new HashMap();
        data.put("totalCount",page.getTotal());
        data.put("list",page.getResult());

        return ReturnFomart.retParam(200,data);
    }
}
