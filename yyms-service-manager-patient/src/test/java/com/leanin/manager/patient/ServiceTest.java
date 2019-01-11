package com.leanin.manager.patient;

import com.alibaba.fastjson.JSON;
import com.leanin.domain.response.DataOutResponse;
import com.leanin.manager.patient.ManagerPatientApplication;
import com.leanin.manager.patient.service.ManagerPatientService;
//import org.apache.cxf.endpoint.Client;
//import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ServiceTest {

    @Autowired
    ManagerPatientService managerPatientService;

    @Test
    public void test01(){
        Date startDate=new Date(116,1,4,0,0,0);
//        JSON.toJSONString(startDate)
//        System.out.println(JSON.toJSONString(startDate));
        Map map=new HashMap();
        map.put("currentPage",1);
        map.put("pageSize",10);
        map.put("inOut",1);
        map.put("startDate",startDate);
//
        DataOutResponse listByParam = managerPatientService.findListByParam(map);
        System.out.println(listByParam);
    }

    @Test
    public void testws(){
        // 创建动态客户端
//        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
//        Client client = dcf.createClient("http://192.168.0.131:8082/soap/test?wsdl");
        // 需要密码的情况需要加上用户名和密码
        // client.getOutInterceptors().add(new ClientLoginInterceptor(USER_NAME,PASS_WORD));
//        Map paramMap=new Hashtable();
//        paramMap.put("currentPage",1);
//        paramMap.put("pageSize",1);
//        paramMap.put("inOut","1");
//        try {
//            Object[] testWS = client.invoke("testWS", paramMap);
//            System.out.println(testWS.toString());
//            Object[] invoke = client.invoke("testString", "wbservicews");
//            System.out.println(invoke.toString());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
}
