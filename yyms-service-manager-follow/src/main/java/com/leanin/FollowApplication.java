package com.leanin;

//


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
//import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * 启动类
 * @author Administrator
 *
 */
//@EnableScheduling//开启任务调度
@EnableFeignClients //开始feignClient
@EnableDiscoveryClient
@EnableEurekaClient
//@EnableTransactionManagement
@EnableHystrix
@ComponentScan("com.leanin.domain")	//扫描model包
@MapperScan("com.leanin.mapper")	//扫描mapper包
@EntityScan("com.leanin.domain")	//扫描
@ComponentScan("com.leanin.utils")//扫描工具类
@ComponentScan("com.leanin")
@ComponentScan("com.leanin.api")//扫描api
@SpringBootApplication
public class FollowApplication {

	public static void main(String[] args) {
		SpringApplication.run(FollowApplication.class, args);
	}

	//客户端负载均衡
	@Bean
	@LoadBalanced
	public RestTemplate restTemplate() {
		return new RestTemplate(new OkHttp3ClientHttpRequestFactory());
	}


}
