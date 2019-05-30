package com.leanin.socket.client;

import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.response.ReturnFomart;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
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
@Slf4j
public class SocketClientFallBack implements FallbackFactory<SocketClient> {

    @Override
    public SocketClient create(Throwable throwable) {
        log.info("调用follow异常:"+throwable.getMessage());
        SocketClient socketClient = new SocketClient() {
            @Override
            public DataOutResponse findHosList(String patientId) {
                return ReturnFomart.retParam(1000, patientId);
            }

            @Override
            public DataOutResponse findHisPlanPatientById(Long patientId, Integer patientSource) {
                return ReturnFomart.retParam(1000, patientId);
            }
        };
        return socketClient;
    }
}
