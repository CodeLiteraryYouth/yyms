package com.leanin.socket.message;

import com.corundumstudio.socketio.SocketIOServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @ProjectName: yyms
 * @Package: com.leanin.socket.message
 * @ClassName: ServerRunner
 * @Author: zd
 * @Description: socket线程启动类
 * @Date: 2019/5/16 15:24
 * @Version: 1.0
 */
@Component
public class ServerRunner implements CommandLineRunner {

    private final SocketIOServer server;

    @Autowired
    public ServerRunner(SocketIOServer server) {
        this.server = server;
    }

    @Override
    public void run(String... args) throws Exception {
        server.start();
    }
}

