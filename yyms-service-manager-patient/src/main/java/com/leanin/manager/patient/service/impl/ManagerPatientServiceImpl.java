package com.leanin.manager.patient.service.impl;

import com.alibaba.fastjson.JSON;
import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.response.ReturnFomart;
import com.leanin.manager.patient.service.ManagerPatientService;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class ManagerPatientServiceImpl implements ManagerPatientService {




    @Override
    public DataOutResponse findListByParam(Map paramMap) {
        // 创建动态客户端
        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
        Client client = dcf.createClient("http://221.12.12.58:8082/soap/test?wsdl");
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
        Client client = dcf.createClient("http://221.12.12.58:8082/soap/test?wsdl");
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
    public Map findOutHosPatientByParamToSF(Map paramMap) {
        // 创建动态客户端
        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
        Client client = dcf.createClient("http://221.12.12.58:8082/soap/test?wsdl");
        // 需要密码的情况需要加上用户名和密码
        // client.getOutInterceptors().add(new ClientLoginInterceptor(USER_NAME,PASS_WORD));
        String jsonString = JSON.toJSONString(paramMap);
        //获取webService返回结果
        List<Map> listMap=null;
        Map dataMap =new HashMap();
        try {
            Object[] objects = client.invoke("findOutHosPatInfoAoListByParam", jsonString);
            listMap = JSON.parseArray(objects[0].toString(), Map.class);
            dataMap.put("totalCount",listMap.size());
            dataMap.put("list",listMap);
            return dataMap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //给随访提供接口，门诊病人信息
    @Override
    public Map findInHosPatientByParamToSF(Map paramMap) {
        // 创建动态客户端
        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
        Client client = dcf.createClient("http://221.12.12.58:8082/soap/test?wsdl");
        // 需要密码的情况需要加上用户名和密码
        // client.getOutInterceptors().add(new ClientLoginInterceptor(USER_NAME,PASS_WORD));
        String jsonString = JSON.toJSONString(paramMap);
        //获取webService返回结果
        List<Map> listMap=null;
        Map dataMap = new HashMap();
        try {
            Object[] objects = client.invoke("findInHosPatInfoAoListByParam", jsonString);
            listMap = JSON.parseArray(objects[0].toString(), Map.class);
            int size = dataMap.size();
            dataMap.put("totalCount",size);
            dataMap.put("list",listMap);
            return dataMap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //给随访提供接口，根据病人id查询 出住院记录
    @Override
    public List<Map> findInHosRecordById(Map paramMap) {
        // 创建动态客户端
        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
        Client client = dcf.createClient("http://221.12.12.58:8082/soap/test?wsdl");
        // 需要密码的情况需要加上用户名和密码
        // client.getOutInterceptors().add(new ClientLoginInterceptor(USER_NAME,PASS_WORD));
        //获取webService返回结果
        List<Map> listMap=null;
//        Map dataMap = new HashMap();
        String jsonString = JSON.toJSONString(paramMap);
        try {
            Object[] objects = client.invoke("findInHosCordById", jsonString);
            listMap = JSON.parseArray(objects[0].toString(), Map.class);
//            int size = dataMap.size();
//            dataMap.put("totalCount",size);
//            dataMap.put("list",listMap);
            System.out.println("呜呜呜呜呜呜呜呜无："+listMap);
            return listMap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //给随访提供接口，根据病人id查询 门诊记录
    @Override
    public List<Map> findOutHosRecordById(String patientId) {
        // 创建动态客户端
        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
        Client client = dcf.createClient("http://221.12.12.58:8082/soap/test?wsdl");
        // 需要密码的情况需要加上用户名和密码
        // client.getOutInterceptors().add(new ClientLoginInterceptor(USER_NAME,PASS_WORD));
        //获取webService返回结果
        List<Map> listMap=null;
//        Map dataMap = new HashMap();
        try {
            Object[] objects = client.invoke("findOutHosCordById", patientId);
            listMap = JSON.parseArray(objects[0].toString(), Map.class);
//            int size = dataMap.size();
//            dataMap.put("totalCount",size);
//            dataMap.put("list",listMap);
            System.out.println("凄凄切切群群："+listMap);
            return listMap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    @Override
    public Map findInHosPatientById(String patientId) {
        // 创建动态客户端
        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
        Client client = dcf.createClient("http://221.12.12.58:8082/soap/test?wsdl");

        Map dataMap=new HashMap();
        try {
            Object[] objects = client.invoke("findInHosPatientInfo", patientId);
            dataMap = JSON.parseObject(objects[0].toString(), Map.class);
            return dataMap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Map findOutHosPatientById(String patientId) {
        // 创建动态客户端
        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
        Client client = dcf.createClient("http://221.12.12.58:8082/soap/test?wsdl");

        Map dataMap=new HashMap();
        try {
            Object[] objects = client.invoke("findOutHosPatientInfo", patientId);
            dataMap = JSON.parseObject(objects[0].toString(), Map.class);
            return dataMap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
