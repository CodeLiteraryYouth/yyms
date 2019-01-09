package com.leanin.manager.patient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableFeignClients //开始feignClient
@EnableDiscoveryClient //
@EntityScan("com.leanin.domain")
@ComponentScan("com.leanin.api")//扫描api
@ComponentScan("com.leanin.manager.patient")//扫描本包
@ComponentScan("com.leanin")//扫描common包
@ComponentScan("com.leanin.utils")//扫描工具类
public class ManagerPatientApplication {

    public static void main(String[] args) {
        SpringApplication.run(ManagerPatientApplication.class,args);
    }

    @Bean
    @LoadBalanced//开始客户端负载均衡
    public RestTemplate restTemplate(){
        return new RestTemplate(new OkHttp3ClientHttpRequestFactory());
    }
}
