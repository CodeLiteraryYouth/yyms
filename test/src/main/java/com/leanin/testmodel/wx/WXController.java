package com.leanin.testmodel.wx;

import com.alibaba.fastjson.JSON;
import com.leanin.domain.response.ReturnFomart;
import com.leanin.utils.HttpClient;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.text.ParseException;
import java.util.Map;

public class WXController {

    private static RestTemplate restTemplate;

    private String appId = "wx1f020aa42d92b635";
    private String appSecret = "21be49689399a4e8eb437c371f42fbab";
    private String redirectUrl = "www.baidu.com";

    @Test
    public void test(){
        try {
            String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx1f020aa42d92b635&redirect_uri=www.baidu.com&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect";
            HttpClient httpClient = new HttpClient(url);
            httpClient.setHttps(true);
            httpClient.get();

            String content = httpClient.getContent();
            Map map = JSON.parseObject(content, Map.class);
            System.out.println(map);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
