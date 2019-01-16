package com.leanin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * 启动类
 * @author Administrator
 *
 */
@SpringBootApplication
@EnableFeignClients //开始feignClient
@EnableDiscoveryClient
@EnableEurekaClient
@EnableHystrix
@ComponentScan("com.leanin.domain")	//扫描model包
@MapperScan("com.leanin.mapper")	//扫描mapper包
@ComponentScan("com.leanin.utils")//扫描工具类
@ComponentScan("com.leanin.api")//扫描api
@ComponentScan("com.leanin")
class FollowApplication {

	public static void main(String[] args) {
		SpringApplication.run(FollowApplication.class, args);
	}
	
}
