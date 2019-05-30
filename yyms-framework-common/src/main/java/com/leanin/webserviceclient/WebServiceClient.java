package com.leanin.webserviceclient;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;

/**
 * 单列模式创建webService客户端连接对象
 * @author CPJ.
 * @date 2019/5/20.
 * @time 10:34.
 */
public class WebServiceClient {

    private static volatile Client client = null;

    private WebServiceClient() {
    }

    public static Client createClient() {
        if (client == null) {
            synchronized (Client.class) {
                JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
                client = dcf.createClient("http://221.12.12.58:8082/soap/test?wsdl");
                // 需要密码的情况需要加上用户名和密码
                // client.getOutInterceptors().add(new ClientLoginInterceptor(USER_NAME,PASS_WORD));
            }
        }
        return client;
    }

}
