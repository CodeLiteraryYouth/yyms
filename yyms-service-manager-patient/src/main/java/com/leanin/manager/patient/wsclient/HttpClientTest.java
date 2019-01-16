package com.leanin.manager.patient.wsclient;

import com.alibaba.fastjson.JSON;
//import org.apache.cxf.endpoint.Client;
//import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import com.leanin.domain.ao.InHosPatInfoAo;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.IOException;
import java.util.*;

public class HttpClientTest {
//    /**
//     * post请求传输map数据
//     * @param url
//     * @param map
//     * @param encoding
//     * @return
//     * @throws ClientProtocolException
//     * @throws IOException
//     */
//    public static String sendPostDataByMap(String url, Map<String, String> map, String encoding) throws ClientProtocolException, IOException {
//        String result = "";
//
//        // 创建httpclient对象
//        CloseableHttpClient httpClient = HttpClients.createDefault();
//        // 创建post方式请求对象
//        HttpPost httpPost = new HttpPost(url);
//
//        // 装填参数
//        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
//        if (map != null) {
//            for (Map.Entry<String, String> entry : map.entrySet()) {
//                nameValuePairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
//            }
//        }
//
//        // 设置参数到请求对象中
//        httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, encoding));
//
//        // 设置header信息
//        // 指定报文头【Content-type】、【User-Agent】
//        httpPost.setHeader("Content-type", "application/x-www-form-urlencoded");
////        httpPost.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
//
//        // 执行请求操作，并拿到结果（同步阻塞）
//        CloseableHttpResponse response = httpClient.execute(httpPost);
//        // 获取结果实体
//        // 判断网络连接状态码是否正常(0--200都数正常)
//        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
//            result = EntityUtils.toString(response.getEntity(), "utf-8");
//        }
//        // 释放链接
//        response.close();
//
//        return result;
//    }
//
//    /**
//     * post请求传输json数据
//     *
//     * @param url
//     * @param json
//     * @param encoding
//     * @return
//     * @throws ClientProtocolException
//     * @throws IOException
//     */
//    public static String sendPostDataByJson(String url, String json, String encoding) throws ClientProtocolException, IOException {
//        String result = "";
//
//        // 创建httpclient对象
//        CloseableHttpClient httpClient = HttpClients.createDefault();
//
//        // 创建post方式请求对象
//        HttpPost httpPost = new HttpPost(url);
//
//        // 设置参数到请求对象中
//        StringEntity stringEntity = new StringEntity(json, ContentType.APPLICATION_JSON);
//        stringEntity.setContentEncoding("utf-8");
//        httpPost.setEntity(stringEntity);
//
//        // 执行请求操作，并拿到结果（同步阻塞）
//        CloseableHttpResponse response = httpClient.execute(httpPost);
//
//        // 获取结果实体
//        // 判断网络连接状态码是否正常(0--200都数正常)
//        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
//            result = EntityUtils.toString(response.getEntity(), "utf-8");
//        }
//        // 释放链接
//        response.close();
//
//        return result;
//    }
//
//    /**
//     * get请求传输数据
//     *
//     * @param url
//     * @param encoding
//     * @return
//     * @throws ClientProtocolException
//     * @throws IOException
//     */
//    public String sendGetData(String url, String encoding) throws ClientProtocolException, IOException {
//        String result = "";
//
//        // 创建httpclient对象
//        CloseableHttpClient httpClient = HttpClients.createDefault();
//
//        // 创建get方式请求对象
//        HttpGet httpGet = new HttpGet(url);
//        httpGet.addHeader("Content-type", "application/json");
//        // 通过请求对象获取响应对象
//        CloseableHttpResponse response = httpClient.execute(httpGet);
//
//        // 获取结果实体
//        // 判断网络连接状态码是否正常(0--200都数正常)
//        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
//            result = EntityUtils.toString(response.getEntity(), "utf-8");
//        }
//        // 释放链接
//        response.close();
//
//        return result;
//    }
//
//    @Test
//    public void testSendPostDataByMap() throws ClientProtocolException, IOException {
//
//        String url = "http://localhost:8080/httpService/sendPostDataByMap";
//        Map<String, String> map = new HashMap<String, String>();
//        map.put("name", "wyj");
//        map.put("city", "南京");
//        String body = sendPostDataByMap(url, map, "utf-8");
//        System.out.println("响应结果：" + body);
//    }
//
//    @Test
//    public void testSendPostDataByJson() throws ClientProtocolException, IOException {
//        String url = "http://localhost:8080/httpService/sendPostDataByJson";
//        Map<String, String> map = new HashMap<String, String>();
//        map.put("name", "wyj");
//        map.put("city", "南京");
//        String body = sendPostDataByJson(url, JSON.toJSONString(map), "utf-8");
//        System.out.println("响应结果：" + body);
//    }
//
//    @Test
//    public void testSendGetData() throws ClientProtocolException, IOException {
//        String url = "http://localhost:8080/httpService/sendGetData?name=wyj&city=南京";
//        String body = sendGetData(url, "utf-8");
//        System.out.println("响应结果：" + body);
//    }
//    @Test
//    public void test1(){
//        // 创建动态客户端
//        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
//        Client client = dcf.createClient("http://192.168.0.131:8082/soap/test?wsdl");
//        // 需要密码的情况需要加上用户名和密码
//        // client.getOutInterceptors().add(new ClientLoginInterceptor(USER_NAME,PASS_WORD));
//        Map paramMap=new HashMap();
//        paramMap.put("currentPage",1);
//        paramMap.put("pageSize",20);
//        paramMap.put("inOut","1");
//        String string = JSON.toJSONString(paramMap);
//        try {
//            Object[] testWS = client.invoke("testWS", string);
//            Map map = JSON.parseObject(testWS[0].toString(), Map.class);
//            List<Map> list = (List<Map>) map.get("list");
////            Map data = (Map) testWS[0];
////            Integer totalCount = (Integer) data.get("totalCount");
////            List<Map> list1 = (List<Map>) data.get("list");
//            int size = list.size();
//            System.out.println(size+"数据"+list);
////            Object[] invoke = client.invoke("testString", "wbservicews");
////            System.out.println(invoke[0].toString());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
    @Test
    public void test(){
        Map paramMap=new HashMap();
        paramMap.put("dept","儿科");
        // 创建动态客户端
        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
        Client client = dcf.createClient("http://192.168.0.131:8082/soap/test?wsdl");
        // 需要密码的情况需要加上用户名和密码
        // client.getOutInterceptors().add(new ClientLoginInterceptor(USER_NAME,PASS_WORD));
        String jsonString = JSON.toJSONString(paramMap);
        //获取webService返回结果
        Map dataMap=null;
        try {
            Object[] objects = client.invoke("findInHosPatInfoAoListByParam", jsonString);
            dataMap = JSON.parseObject(objects[0].toString(), Map.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<InHosPatInfoAo> list = (List<InHosPatInfoAo>) dataMap.get("list");
        System.out.println(list.size());
    }
}
