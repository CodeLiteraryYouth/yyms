package com.leanin.socket.client;

import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.response.ReturnFomart;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @ProjectName: yyms
 * @Package: com.leanin.socket.client
 * @ClassName: SocketClientFallBack
 * @Author: zd
 * @Description: socket的熔断器机制
 * @Date: 2019/5/17 9:28
 * @Version: 1.0
 */
@Component
public class SocketClientFallBack implements SocketClient {


    @Override
    public DataOutResponse findHosList(String patientId) {
        return ReturnFomart.retParam(1000,patientId);
    }
}
