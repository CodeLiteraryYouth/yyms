package com.leanin.testmodel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableDiscoveryClient //一个EurekaClient从EurekaServer发现服务
@EntityScan("com.leanin.domain") //扫描实体类
@ComponentScan(basePackages = {"com.leanin.api"}) //扫描接口
@ComponentScan(basePackages = {"com.leanin"}) //扫描common包下的类
@ComponentScan(basePackages = {"com.leanin.testmodel"}) //扫描本包下的类
public class TestApplication {
    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class,args);
    }

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate(new OkHttp3ClientHttpRequestFactory());
    }
}
