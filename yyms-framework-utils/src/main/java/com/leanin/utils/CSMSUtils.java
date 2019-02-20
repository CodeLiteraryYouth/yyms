package com.leanin.utils;

import com.alibaba.fastjson.JSON;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * 自定义短信工具类
 * 多个手机号码用 ， 隔开 手机号码数量最好不要超过2万个
 */
public class CSMSUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(CSMSUtils.class);

    private static String requestUrl = "http://api.feige.ee/SmsService/Send";


    //mobiles 多个手机号码用 ， 隔开
    public static Map sendMessage(String content, String mobiles) {
        Map map = null;
        try {
            List<NameValuePair> formparams = new ArrayList<NameValuePair>();
            formparams.add(new BasicNameValuePair("Account", "18556531536"));
            formparams.add(new BasicNameValuePair("Pwd", "723c422c49c7c3a6d1c0f7953"));
            formparams.add(new BasicNameValuePair("Content", content));
            formparams.add(new BasicNameValuePair("Mobile", mobiles));
            formparams.add(new BasicNameValuePair("SignId", "86057"));
            map = Post(formparams);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return map;
    }

    public static Map Post(List<NameValuePair> formparams) throws UnsupportedEncodingException {
        Map<String, Object> map = new HashMap<>();

        CloseableHttpAsyncClient httpClient = HttpAsyncClients.createDefault();

        httpClient.start();

        HttpPost requestPost = new HttpPost(requestUrl);

        requestPost.setEntity(new UrlEncodedFormEntity(formparams, "utf-8"));

        try {
            HttpResponse httpResponse = httpClient.execute(requestPost, new FutureCallback<HttpResponse>() {

                @Override
                public void failed(Exception arg0) {
                    LOGGER.error("短信发送失败 ：{}", arg0.getMessage());
                    System.out.println("Exception: " + arg0.getMessage());
                }

                @Override
                public void completed(HttpResponse arg0) {
                    System.out.println("Response: " + arg0.getStatusLine());
                    LOGGER.info("短信发送状态：{}", arg0.getStatusLine());
                    try {
                        InputStream stram = arg0.getEntity().getContent();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(stram));
                        System.out.println(reader.readLine());
                        //返回值说明   SendId 短信回执编号  InvalidCount 无效号码  SuccessCount 成功数量
                        //BlackCount 黑名单号码条数  Code 短信回执状态码，判断成功失败的标志（成功为0，其他请参照 API 错误代码
                        //Message 短信回执状态描述（成功为ok，其它请参考 API 错误代码）
                        LOGGER.info("短信发送后返回的：{}", reader.readLine().toString());
                    } catch (UnsupportedOperationException e) {
                        // e.printStackTrace();
                    } catch (IOException e) {
                        //  e.printStackTrace();
                    }
                }

                @Override
                public void cancelled() {
                    // TODO Auto-generated method stub
                }
            }).get();

            BufferedReader reader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
            Map resultMap = JSON.parseObject(reader.readLine().toString(), Map.class);
            Integer code = (Integer) resultMap.get("Code");
            if (code == 0){
                map.put("msg", "true");
            }else{
                map.put("msg", "false");
            }
        } catch (Exception e) {
            map.put("msg", "false");
        }
        return map;
    }

}
