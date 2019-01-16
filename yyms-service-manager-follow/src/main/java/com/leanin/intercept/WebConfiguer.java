package com.leanin.intercept;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Title:
 * @author: chenmingyue
 * @date: 2018/3/16 12:01
 * Description:配置URLInterceptor拦截器，以及拦截路径
 */
@Configuration
public class WebConfiguer extends WebMvcConfigurerAdapter{
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new URLInterceptor()).addPathPatterns("/*/*");
        super.addInterceptors(registry);
    }
}
