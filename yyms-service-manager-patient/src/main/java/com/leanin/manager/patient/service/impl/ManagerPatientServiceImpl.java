package com.leanin.manager.patient.service.impl;

import com.alibaba.fastjson.JSON;
import com.leanin.domain.analysis.DeptAnalysis;
import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.response.ReturnFomart;
import com.leanin.manager.patient.service.ManagerPatientService;
import com.leanin.webserviceclient.WebServiceClient;
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
//        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
//        Client client = dcf.createClient("http://221.12.12.58:8082/soap/test?wsdl");
//        Client client = dcf.createClient("http://127.0.0.1:8082/soap/test?wsdl");
        Client client = WebServiceClient.createClient();
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
//        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
//        Client client = dcf.createClient("http://221.12.12.58:8082/soap/test?wsdl");
//        Client client = dcf.createClient("http://127.0.0.1:8082/soap/test?wsdl");
        Client client = WebServiceClient.createClient();
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
//        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
//        Client client = dcf.createClient("http://221.12.12.58:8082/soap/test?wsdl");
//        Client client = dcf.createClient("http://127.0.0.1:8082/soap/test?wsdl");
        Client client = WebServiceClient.createClient();
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
//        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
//        Client client = dcf.createClient("http://221.12.12.58:8082/soap/test?wsdl");
//        Client client = dcf.createClient("http://127.0.0.1:8082/soap/test?wsdl");
        Client client = WebServiceClient.createClient();
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
//        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
//        Client client = dcf.createClient("http://221.12.12.58:8082/soap/test?wsdl");
//        Client client = dcf.createClient("http://127.0.0.1:8082/soap/test?wsdl");
        Client client = WebServiceClient.createClient();
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
//        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
//        Client client = dcf.createClient("http://221.12.12.58:8082/soap/test?wsdl");
//        Client client = dcf.createClient("http://127.0.0.1:8082/soap/test?wsdl");
        Client client = WebServiceClient.createClient();
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
            return listMap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    @Override
    public Map findInHosPatientById(String patientId) {
        // 创建动态客户端
//        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
//        Client client = dcf.createClient("http://221.12.12.58:8082/soap/test?wsdl");
//        Client client = dcf.createClient("http://127.0.0.1:8082/soap/test?wsdl");
        Client client = WebServiceClient.createClient();
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
//        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
//        Client client = dcf.createClient("http://221.12.12.58:8082/soap/test?wsdl");
//        Client client = dcf.createClient("http://127.0.0.1:8082/soap/test?wsdl");
        Client client = WebServiceClient.createClient();
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

    @Override
    public DataOutResponse findRegList(Map paramMap) {
        // 创建动态客户端
//        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
//        Client client = dcf.createClient("http://221.12.12.58:8082/soap/test?wsdl");
//        Client client = dcf.createClient("http://127.0.0.1:8082/soap/test?wsdl");
        Client client = WebServiceClient.createClient();
        List<Map> listMap=null;
        String jsonString = JSON.toJSONString(paramMap);
        try {
            Object[] objects = client.invoke("findRegList", jsonString);
            listMap = JSON.parseArray(objects[0].toString(), Map.class);
            return ReturnFomart.retParam(200,listMap);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public DataOutResponse updateRegNum(Map paramMap) {
        // 创建动态客户端
//        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
//        Client client = dcf.createClient("http://221.12.12.58:8082/soap/test?wsdl");
////        Client client = dcf.createClient("http://127.0.0.1:8082/soap/test?wsdl");
//
//        Map dataMap=new HashMap();
//        String jsonString = JSON.toJSONString(paramMap);
//        try {
//            Object[] objects = client.invoke("updateRegNum", jsonString);
//            dataMap = JSON.parseObject(objects[0].toString(), Map.class);
//            if (Integer.parseInt(dataMap.get("data").toString())>0) {
//                return ReturnFomart.retParam(200,dataMap);
//            }
//            return ReturnFomart.retParam(0,dataMap);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
        return ReturnFomart.retParam(200,paramMap);
    }

    @Override
    public DataOutResponse updateOrderInfo(Map paramMap) {
        return ReturnFomart.retParam(200,paramMap);
    }

    @Override
    public DataOutResponse cancelOrder(Map paramMap) {
        return ReturnFomart.retParam(200,paramMap);
    }

    @Override
    public DataOutResponse findDoctorDept() {
        // 创建动态客户端
//        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
//        Client client = dcf.createClient("http://221.12.12.58:8082/soap/test?wsdl");
        Client client = WebServiceClient.createClient();
        List<Map> listMap=null;
        try {
            Object[] objects = client.invoke("findDoctorDept", "");
            listMap = JSON.parseArray(objects[0].toString(), Map.class);
            return ReturnFomart.retParam(200,listMap);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public DataOutResponse findByIdCard(String idCard,String patientName) {
        // 创建动态客户端
//        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
//        Client client = dcf.createClient("http://221.12.12.58:8082/soap/test?wsdl");
//        List<Map> listMap=null;
        Client client = WebServiceClient.createClient();
        Map<String,String> param =new HashMap<>();
        param.put("idCard",idCard);
        param.put("patientName",patientName);
        try {
            Object[] objects = client.invoke("findByIdCard", JSON.toJSONString(param));
            if (objects[0] == null){
                return ReturnFomart.retParam(1,"信息不存在");
            }
//            Map map = JSON.parseObject(objects[0].toString(), Map.class);
            return ReturnFomart.retParam(200,objects[0].toString());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<DeptAnalysis> findInOutCount(String dept, String startDate, String endDate,Integer inOut) {
        Client client = WebServiceClient.createClient();
        Map param =new HashMap<>();
        param.put("dept",dept);
        param.put("startDate",startDate);
        param.put("endDate",endDate);
        param.put("inOut",inOut);
        try {
            Object[] objects = client.invoke("findInOutCount", JSON.toJSONString(param));
            if (objects[0] == null){
                return null;
            }
            List<DeptAnalysis> result = JSON.parseArray(objects[0].toString(), DeptAnalysis.class);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<DeptAnalysis> findInOutCountByYear(String dept, String year, Integer inOut) {
        Client client = WebServiceClient.createClient();
        Map param =new HashMap<>();
        param.put("dept",dept);
        param.put("year",year);
        param.put("inOut",inOut);
        try {
            Object[] objects = client.invoke("findInOutCountByYear", JSON.toJSONString(param));
            if (objects[0] == null){
                return null;
            }
            List<DeptAnalysis> result = JSON.parseArray(objects[0].toString(), DeptAnalysis.class);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
