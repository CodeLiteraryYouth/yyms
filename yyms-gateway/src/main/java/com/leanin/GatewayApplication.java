package com.leanin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * 网关启动中心
 * @author Administrator
 */
//@EnableZuulProxy
////@EnableDiscoveryClient
////@EnableEurekaClient
////@SpringBootApplication
////@EnableOAuth2Sso
@SpringBootApplication
@EnableZuulProxy//此工程是一个zuul网关
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class,args);
    }
}
