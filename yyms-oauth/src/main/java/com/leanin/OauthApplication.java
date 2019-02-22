package com.leanin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * 认证中心启动类
 * @author Administrator
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableEurekaClient
@EntityScan("com.leanin.domain")    //扫描实体类的包
@MapperScan("com.leanin.oauth.mapper")	//扫描mapper包
@ComponentScan("com.leanin.utils") //扫描工具类
@ComponentScan("com.leanin.api") //扫描工具类
@ComponentScan("com.leanin.oauth") //
public class OauthApplication {
    public static void main(String[] args) {
        SpringApplication.run(OauthApplication.class, args);
    }
}