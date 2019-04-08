package com.leanin.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @ClassName WebMvcConfig
 * @Description 上传录音文件映射tomcat路径
 * @Author 刘壮
 * @Date 2019/4/8 15:00
 * @ModifyDate 2019/4/8 15:00
 * @Version 1.0
 */

@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

     //配置server虚拟路径，handler为前台访问的目录，locations为files相对应的本地路径
        String filePath="";
        String os = System.getProperty("os.name");
        if (os.startsWith("Windows")) {
            filePath="D:\\upload\\";
        }else if (os.startsWith("Linux")) {
            filePath="/home/tp/wav/";
    }

    registry.addResourceHandler("/upload/**").addResourceLocations(filePath);

        }
}
