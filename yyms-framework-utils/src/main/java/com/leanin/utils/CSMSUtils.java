package com.leanin.utils;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 自定义短信工具类
 * 多个手机号码用 ， 隔开 手机号码数量最好不要超过2万个
 */
public class CSMSUtils {

    private static final Logger LOGGER= LoggerFactory.getLogger(CSMSUtils.class);

    private static String requestUrl="http://api.feige.ee/SmsService/Send";


    //mobiles 多个手机号码用 ， 隔开
    public static void sendMessage(String content,String mobiles){
        try {
            List<NameValuePair> formparams = new ArrayList<NameValuePair>();
            formparams.add(new BasicNameValuePair("Account", "18556531536"));
            formparams.add(new BasicNameValuePair("Pwd", "723c422c49c7c3a6d1c0f7953"));
            formparams.add(new BasicNameValuePair("Content", content));
            formparams.add(new BasicNameValuePair("Mobile", mobiles));
            formparams.add(new BasicNameValuePair("SignId", "86057"));
            Post(formparams);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void Post(List<NameValuePair> formparams) throws Exception {
        CloseableHttpAsyncClient httpClient = HttpAsyncClients.createDefault();

        httpClient.start();

        HttpPost requestPost = new HttpPost(requestUrl);

        requestPost.setEntity(new UrlEncodedFormEntity(formparams, "utf-8"));

        httpClient.execute(requestPost, new FutureCallback<HttpResponse>() {

            public void failed(Exception arg0) {
                LOGGER.error("短信发送失败 ：{}",arg0.getMessage());
                System.out.println("Exception: " + arg0.getMessage());
            }

            public void completed(HttpResponse arg0) {
                System.out.println("Response: " + arg0.getStatusLine());
                LOGGER.info("短信发送状态：{}",arg0.getStatusLine());
                try {

                    InputStream stram = arg0.getEntity().getContent();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(stram));
                    System.out.println(reader.readLine());
                    LOGGER.info("短信发送后返回的：{}",reader.readLine().toString());
                } catch (UnsupportedOperationException e) {

//                    e.printStackTrace();
                } catch (IOException e) {

//                    e.printStackTrace();
                }


            }

            public void cancelled() {
                // TODO Auto-generated method stub

            }
        }).get();
    }

}
