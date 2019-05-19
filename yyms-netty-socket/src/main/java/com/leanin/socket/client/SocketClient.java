package com.leanin.socket.client;

import com.leanin.domain.response.DataOutResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @ProjectName: yyms
 * @Package: com.leanin.socket.client
 * @ClassName: SocketClient
 * @Author: zd
 * @Description: socket调用病人信息服务
 * @Date: 2019/5/17 9:27
 * @Version: 1.0
 */
@FeignClient(value = "yyms-service-manager-follow",fallback = SocketClientFallBack.class)//制定远程调用的服务名
public interface SocketClient {

    /**
     * 根据patientId查询微信病人列表
     * @param patientId
     * @return
     */
    @GetMapping("/wx/findHosList")
    public DataOutResponse findHosList(@RequestParam(required = false) String patientId);

}
