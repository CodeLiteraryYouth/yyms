package com.leanin.manager.patient.service.impl;

import com.alibaba.fastjson.JSON;
import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.response.ReturnFomart;
import com.leanin.manager.patient.service.ManagerPatientService;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

//import com.github.pagehelper.Page;
//import com.github.pagehelper.PageHelper;

@Service
public class ManagerPatientServiceImpl implements ManagerPatientService {




    @Override
    public DataOutResponse findListByParam(Map paramMap) {
        // 创建动态客户端
        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
        Client client = dcf.createClient("http://192.168.0.131:8082/soap/test?wsdl");
        // 需要密码的情况需要加上用户名和密码
        // client.getOutInterceptors().add(new ClientLoginInterceptor(USER_NAME,PASS_WORD));
        String jsonString = JSON.toJSONString(paramMap);
        //获取webService返回结果
        Map dataMap=null;
        try {
            Object[] objects = client.invoke("findListByParam", jsonString);
            dataMap = JSON.parseObject(objects[0].toString(), Map.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ReturnFomart.retParam(200,dataMap);
    }

    @Override
    public DataOutResponse findOutHosPatientByParam(Map paramMap) {
        // 创建动态客户端
        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
        Client client = dcf.createClient("http://192.168.0.131:8082/soap/test?wsdl");
        // 需要密码的情况需要加上用户名和密码
        // client.getOutInterceptors().add(new ClientLoginInterceptor(USER_NAME,PASS_WORD));
        String jsonString = JSON.toJSONString(paramMap);
        //获取webService返回结果
        Map dataMap=null;
        try {
            Object[] objects = client.invoke("findOutHosPatientByParam", jsonString);
            dataMap = JSON.parseObject(objects[0].toString(), Map.class);
        } catch (Exception e) {
            e.printStackTrace();
            return ReturnFomart.retParam(96,"操作失败，数据为空");

        }

        return ReturnFomart.retParam(200,dataMap);
    }

    //给随访提供接口，出住院病人信息
    @Override
    public List<Map> findOutHosPatientByParamToSF(Map paramMap) {
        // 创建动态客户端
        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
        Client client = dcf.createClient("http://192.168.0.131:8082/soap/test?wsdl");
        // 需要密码的情况需要加上用户名和密码
        // client.getOutInterceptors().add(new ClientLoginInterceptor(USER_NAME,PASS_WORD));
        String jsonString = JSON.toJSONString(paramMap);
        //获取webService返回结果
        List<Map> dataMap=null;
        try {
            Object[] objects = client.invoke("findInHosPatInfoAoListByParam", jsonString);
            dataMap = JSON.parseArray(objects[0].toString(), Map.class);
            return dataMap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //给随访提供接口，门诊病人信息
    @Override
    public List<Map> findInHosPatientByParamToSF(Map paramMap) {
        // 创建动态客户端
        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
        Client client = dcf.createClient("http://192.168.0.131:8082/soap/test?wsdl");
        // 需要密码的情况需要加上用户名和密码
        // client.getOutInterceptors().add(new ClientLoginInterceptor(USER_NAME,PASS_WORD));
        String jsonString = JSON.toJSONString(paramMap);
        //获取webService返回结果
        List<Map> dataMap=null;
        try {
            Object[] objects = client.invoke("findOutHosPatInfoAoListByParam", jsonString);
            dataMap = JSON.parseArray(objects[0].toString(), Map.class);
//            List<Map> list = (List<Map>) dataMap.get("list");
            return dataMap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
