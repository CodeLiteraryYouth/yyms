package com.leanin.mq;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
@SpringBootApplication
@EntityScan("com.leanin.domain")
@ComponentScan("com.leanin.api")//扫描api
@ComponentScan("com.leanin.mq")//扫描本包
@ComponentScan("com.leanin.utils")//扫描工具类
public class RabbitMQApplication {
    public static void main(String[] args) {
        SpringApplication.run(RabbitMQApplication.class,args);
    }
}
