package com.leanin.ucenter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.persistence.Entity;




@EnableFeignClients //开始feignClient
@EnableDiscoveryClient
@EnableHystrix
@EntityScan("com.leanin.domain")
@ComponentScan("com.leanin.utils") //扫描工具类
@ComponentScan("com.leanin.api") //扫描工具类
@ComponentScan("com.leanin.ucenter")
@ComponentScan("com.leanin")
@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
public class UCenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(UCenterApplication.class,args);
    }

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate(new OkHttp3ClientHttpRequestFactory());
    }
}
