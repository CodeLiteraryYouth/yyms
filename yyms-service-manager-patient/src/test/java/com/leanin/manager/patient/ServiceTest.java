package com.leanin.manager.patient;

import com.alibaba.fastjson.JSON;
import com.leanin.domain.response.DataOutResponse;
import com.leanin.manager.patient.service.ManagerPatientService;
import com.leanin.utils.CSMSUtils;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.message.BasicNameValuePair;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;
import java.util.*;
import java.util.concurrent.ExecutionException;

//import org.apache.cxf.endpoint.Client;
//import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ServiceTest {

    @Autowired
    ManagerPatientService managerPatientService;

    @Test
    public void test01() {
        Date startDate = new Date(116, 1, 4, 0, 0, 0);
//        JSON.toJSONString(startDate)
//        System.out.println(JSON.toJSONString(startDate));
        Map map = new HashMap();
        map.put("currentPage", 1);
        map.put("pageSize", 10);
        map.put("inOut", 1);
        map.put("startDate", startDate);
//
        DataOutResponse listByParam = managerPatientService.findListByParam(map);
        System.out.println(listByParam);
    }

    @Test
    public void testws() {
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

    @Test
    public void test() {
        Integer currentPage=0;
//        while (true) {

            currentPage++;
            Map paramMap = new HashMap();
            paramMap.put("dept", "儿科");
            paramMap.put("currentPage", currentPage);
            paramMap.put("pageSize", 100);
            // 创建动态客户端
            JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
            Client client = dcf.createClient("http://192.168.0.131:8082/soap/test?wsdl");
            // 需要密码的情况需要加上用户名和密码
            // client.getOutInterceptors().add(new ClientLoginInterceptor(USER_NAME,PASS_WORD));
            String jsonString = JSON.toJSONString(paramMap);
            //获取webService返回结果
            Map dataMap = null;
            try {
                Object[] objects = client.invoke("findInHosPatInfoAoListByParam", jsonString);
                dataMap = JSON.parseObject(objects[0].toString(), Map.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
            List<Map> list = (List<Map>) dataMap.get("list");
            for (Map inHosPatInfoAo : list) {

                System.out.println(inHosPatInfoAo);
            }
            System.out.println("当前页是第几页："+currentPage+" 当前页的数据条数为： "+list.size()+"数据集合："+list);
//            if (list.size()<=0){
//                break;
//            }
//        }
    }

    @Test
    public void test1() throws UnsupportedEncodingException, ExecutionException, InterruptedException {
//        System.out.println("Hello World!");
        String requestUrl="http://api.feige.ee/SmsService/Send";
        List<NameValuePair> formparams=null;
        try {
            formparams = new ArrayList<NameValuePair>();
            formparams.add(new BasicNameValuePair("Account", "18556531536"));
            formparams.add(new BasicNameValuePair("Pwd", "723c422c49c7c3a6d1c0f7953"));
            formparams.add(new BasicNameValuePair("Content", "这是一条测试短信，随便玩玩"));
            formparams.add(new BasicNameValuePair("Mobile", "18556531536"));
            formparams.add(new BasicNameValuePair("SignId", "86057"));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        CloseableHttpAsyncClient httpClient = HttpAsyncClients.createDefault();

        httpClient.start();

        HttpPost requestPost = new HttpPost(requestUrl);

        requestPost.setEntity(new UrlEncodedFormEntity(formparams, "utf-8"));

        httpClient.execute(requestPost, new FutureCallback<HttpResponse>() {

            public void failed(Exception arg0) {

                System.out.println("Exception: " + arg0.getMessage());
            }

            public void completed(HttpResponse arg0) {
                System.out.println("Response: " + arg0.getStatusLine());
                try {

                    InputStream stram = arg0.getEntity().getContent();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(stram));
                    System.out.println(reader.readLine());

                } catch (UnsupportedOperationException e) {

                    e.printStackTrace();
                } catch (IOException e) {

                    e.printStackTrace();
                }


            }

            public void cancelled() {
                // TODO Auto-generated method stub

            }
        }).get();


//        System.out.println("Done");
    }

    @Test
    public void testSendMessage(){
        CSMSUtils.sendMessage("随便发着玩的短信，不信你试试","18556531536,17858852678");
    }


    @Test
    public void testinfo(){
        Map paramMap = new HashMap();
        paramMap.put("inOut", 2);// 1在院  2出院
        List<String> patsWardCode=new ArrayList<>();
        patsWardCode.add("儿科(病区)");
        patsWardCode.add("妇科(病区)");
        paramMap.put("patsWardCode", patsWardCode);// 患者随访科室编码 可能是集合
//        List<String> diseaseCode=new ArrayList<>();
//        diseaseCode.add()
//        paramMap.put("diseaseCode", diseaseCode);//疾病编码
        paramMap.put("planSex", 2);//病人性别 1男 2女
//        paramMap.put("beginDate",planResult.getPlanBeginTime());//开始区间
        paramMap.put("endDate",new Date());//结束区间

//        String planAgeInterval = planResult.getPlanAgeInterval();//年龄区间
//        if (planAgeInterval != null) {//年龄区间
//            String[] split = planAgeInterval.split(",");
            paramMap.put("startAge", 0);
            paramMap.put("endAge", 60);
//        }
        paramMap.put("planExisPhone",1);//有无联系方式 1有 2无


        // 创建动态客户端
        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
        Client client = dcf.createClient("http://192.168.0.131:8082/soap/test?wsdl");
        // 需要密码的情况需要加上用户名和密码
        // client.getOutInterceptors().add(new ClientLoginInterceptor(USER_NAME,PASS_WORD));
        String jsonString = JSON.toJSONString(paramMap);
        //获取webService返回结果
        Map dataMap = null;
        try {
            Object[] objects = client.invoke("findInHosPatInfoAoListByParam", jsonString);
            dataMap = JSON.parseObject(objects[0].toString(), Map.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<Map> list = (List<Map>) dataMap.get("list");
        System.out.println(list);
    }

    @Test
    public void testFindById(){
        // 创建动态客户端
        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
        Client client = dcf.createClient("http://192.168.0.131:8082/soap/test?wsdl");
        // 需要密码的情况需要加上用户名和密码
        // client.getOutInterceptors().add(new ClientLoginInterceptor(USER_NAME,PASS_WORD));
        //获取webService返回结果
        List<Map> listMap=null;
//        Map dataMap = new HashMap();
        try {
            Object[] objects = client.invoke("findInHosCordById", "61665");
            listMap = JSON.parseArray(objects[0].toString(), Map.class);
//            int size = dataMap.size();
//            dataMap.put("totalCount",size);
//            dataMap.put("list",listMap);
//            return listMap;
            System.out.println("结果："+listMap);
        } catch (Exception e) {
            e.printStackTrace();
//            return null;
        }
    }

    @Test
    public void sms(){
        CSMSUtils.sendMessage("测试短信","18556531536");
    }

}
