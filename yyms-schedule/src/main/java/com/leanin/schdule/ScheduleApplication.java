package com.leanin.schdule;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;

@EnableEurekaClient
@SpringBootApplication
@ComponentScan("com.leanin.api")	//扫描实体类
@ComponentScan("com.leanin.schdule")	//扫描本包
@ComponentScan("com.leanin")	//扫描model包
@EntityScan("com.leanin.domain")
@MapperScan("com.leanin.schdule.mapper")	//扫描mapper包
public class ScheduleApplication {
    public static void main(String[] args) {
        SpringApplication.run(ScheduleApplication.class, args);
    }
}
