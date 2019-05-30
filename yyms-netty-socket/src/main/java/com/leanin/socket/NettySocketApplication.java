package com.leanin.socket;

import com.corundumstudio.socketio.AuthorizationListener;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.HandshakeData;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.SpringAnnotationScanner;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * @ProjectName: yyms
 * @Package: com.leanin.socket
 * @ClassName: NettySocketApplication
 * @Author: zd
 * @Description: 启动类
 * @Date: 2019/5/19 23:00
 * @Version: 1.0
 */
@EnableFeignClients //开始feignClient
@EnableDiscoveryClient
@EnableEurekaClient
@EnableHystrix
@ComponentScan("com.leanin.domain")	//扫描model包
@ComponentScan("com.leanin.utils")//扫描工具类
@ComponentScan("com.leanin.socket")
@ComponentScan("com.leanin.api")//扫描api
@SpringBootApplication
public class NettySocketApplication {

    @Value("${wss.server.host}")
    private String host;

    @Value("${wss.server.port}")
    private Integer port;

    @Bean
    public SocketIOServer socketIOServer()
    {
        Configuration config = new Configuration();
        config.setHostname(host);
        config.setPort(port);
        //设置socket传输包的最大值
        config.setMaxFramePayloadLength(65536*50);

        //该处可以用来进行身份验证
        config.setAuthorizationListener(new AuthorizationListener() {
            @Override
            public boolean isAuthorized(HandshakeData data) {
                //http://localhost:8081?username=test&password=test
                //例如果使用上面的链接进行connect，可以使用如下代码获取用户密码信息，本文不做身份验证
//				String username = data.getSingleUrlParam("username");
//				String password = data.getSingleUrlParam("password");
                return true;
            }
        });
        //初始化SocketServer
        final SocketIOServer server = new SocketIOServer(config);
        return server;
    }

    //客户端负载均衡
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate(new OkHttp3ClientHttpRequestFactory());
    }

    @Bean
    public SpringAnnotationScanner springAnnotationScanner(SocketIOServer socketServer) {
        return new SpringAnnotationScanner(socketServer);
    }

    public static void main(String[] args) {
        SpringApplication.run(NettySocketApplication.class, args);
    }

}
