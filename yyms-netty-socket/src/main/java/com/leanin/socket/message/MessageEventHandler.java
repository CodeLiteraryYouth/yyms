package com.leanin.socket.message;

import com.alibaba.fastjson.JSON;
import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import com.corundumstudio.socketio.annotation.OnEvent;
import com.leanin.domain.response.DataOutResponse;
import com.leanin.socket.client.SocketClient;
import com.leanin.socket.domain.ClientInfo;
import com.leanin.socket.domain.MessageInfo;
import com.leanin.socket.mapper.ClientInfoRepository;
import com.leanin.socket.mapper.MessageInfoRepository;
import com.leanin.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @ProjectName: yyms
 * @Package: com.leanin.socket.message
 * @ClassName: MessageEventHandler
 * @Author: zd
 * @Description: socketIO的事件监听
 * @Date: 2019/5/16 15:18
 * @Version: 1.0
 */
@Component
@Slf4j
public class MessageEventHandler {

    private final SocketIOServer server;

    @Autowired
    private ClientInfoRepository clientInfoRepository;

    @Autowired
    private MessageInfoRepository messageInfoRepository;

    @Autowired
    private SocketClient socketClient;

    @Autowired
    public MessageEventHandler(SocketIOServer server) {
        this.server = server;
    }

    //添加connect事件，当客户端发起连接时调用，本文中将clientid与sessionid存入数据库
    //方便后面发送消息时查找到对应的目标client,
    @OnConnect
    @Transactional(rollbackFor = Exception.class)
    public void onConnect(SocketIOClient client) {
        String clientId = client.getHandshakeData().getSingleUrlParam("clientid");
        log.info("客户端的clientId为:"+clientId);
        ClientInfo clientInfo = clientInfoRepository.findClientByClientId(clientId);
        Date nowTime = new Date();
        if (clientInfo == null) {
            //保存连接信息进入数据库
            ClientInfo cInfo=new ClientInfo();
            cInfo.setClientid(clientId);
            cInfo.setConnected((short)1);
            cInfo.setMostsignbits(client.getSessionId().getMostSignificantBits());
            cInfo.setLeastsignbits(client.getSessionId().getLeastSignificantBits());
            cInfo.setLastconnecteddate(nowTime);
            clientInfoRepository.addClientInfo(cInfo);
        } else {
            clientInfo.setConnected((short)1);
            clientInfo.setMostsignbits(client.getSessionId().getMostSignificantBits());
            clientInfo.setLeastsignbits(client.getSessionId().getLeastSignificantBits());
            clientInfo.setLastconnecteddate(nowTime);
            clientInfoRepository.updateClientInfo(clientInfo);
        }
    }

    //添加@OnDisconnect事件，客户端断开连接时调用，刷新客户端信息
    @OnDisconnect
    @Transactional(rollbackFor = Exception.class)
    public void onDisconnect(SocketIOClient client) {
        String clientId = client.getHandshakeData().getSingleUrlParam("clientid");
        ClientInfo clientInfo = clientInfoRepository.findClientByClientId(clientId);
        if (clientInfo != null) {
            clientInfo.setConnected((short)0);
            clientInfo.setMostsignbits(null);
            clientInfo.setLeastsignbits(null);
            clientInfoRepository.updateClientInfo(clientInfo);
        }
    }

    //消息接收入口，当接收到消息后，查找发送目标客户端，并且向该客户端发送消息，且给自己发送消息
    @OnEvent(value = "messageevent")
    @Transactional(rollbackFor = Exception.class)
    public void onEvent(SocketIOClient client, AckRequest request, MessageInfo data) {
        log.info("客户端发送的消息为:"+ JSON.toJSONString(data));
        String targetClientId = data.getTargetClientId();
        ClientInfo clientInfo = clientInfoRepository.findClientByClientId(targetClientId);
        log.info("发送的目标用户信息为:"+JSON.toJSONString(clientInfo));
        if (clientInfo != null && clientInfo.getConnected() != 0) {
            UUID uuid = new UUID(clientInfo.getMostsignbits(), clientInfo.getLeastsignbits());
            MessageInfo sendData = new MessageInfo();
            sendData.setSourceClientId(data.getSourceClientId());
            sendData.setTargetClientId(data.getTargetClientId());
            sendData.setMsgType(data.getMsgType());
            sendData.setMsgContent(data.getMsgContent());
            sendData.setMsgDate(System.currentTimeMillis());
            client.sendEvent("messageevent", sendData);
            server.getClient(uuid).sendEvent("messageevent", sendData);
            //将消息记录存储入库
            messageInfoRepository.addMessageInfo(sendData);
        }
    }

    /**
     * 病人的聊天列表
     * @param client
     */
    @OnEvent(value = "userList")
    public void onList(SocketIOClient client, Map paramMap) {
        String date=DateUtils.format(new Date(),"yyyy-MM-dd");
        List<String> patientId=messageInfoRepository.findPatientIdList(paramMap.get("targetId").toString(),date);
        DataOutResponse data=socketClient.findHosList(StringUtils.join(patientId,","));
        log.info("查询的微信病人列表信息为:"+JSON.toJSONString(data));
        //推送给客户端病人列表
        client.sendEvent("userList",data.getData());
    }

    /**
     * 消息记录
     * @param client
     * @param paramMap
     */
    @OnEvent(value = "messageRecord")
    public void onRecord(SocketIOClient client,Map paramMap) {
        log.info("查询记录的条件为:"+JSON.toJSONString(paramMap));
        List<MessageInfo> result=messageInfoRepository.findMessageList(paramMap.get("sourceId").toString(),paramMap.get("targetId").toString(),
                                                               Long.parseLong(paramMap.get("startDate").toString()),Long.parseLong(paramMap.get("endDate").toString()));
        client.sendEvent("messageRecord",result);
    }
}

