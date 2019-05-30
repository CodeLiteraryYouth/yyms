package com.leanin.socket.message;

import com.alibaba.fastjson.JSON;
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
import com.leanin.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

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
        String clientType=client.getHandshakeData().getSingleUrlParam("clientType");
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
            cInfo.setClientType(clientType);
            client.sendEvent("connect",cInfo);
            clientInfoRepository.addClientInfo(cInfo);
        } else {
            clientInfo.setConnected((short)1);
            clientInfo.setMostsignbits(client.getSessionId().getMostSignificantBits());
            clientInfo.setLeastsignbits(client.getSessionId().getLeastSignificantBits());
            clientInfo.setLastconnecteddate(nowTime);
            clientInfo.setClientType(clientType);
            client.sendEvent("connect",clientInfo);
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
            log.info(clientInfo.getClientid()+"断开连接了");
        }
    }

    //消息接收入口，当接收到消息后，查找发送目标客户端，并且向该客户端发送消息，且给自己发送消息
    @OnEvent(value = "messageevent")
    @Transactional(rollbackFor = Exception.class)
    public void onEvent(SocketIOClient client, String messageInfo) {
        log.info("客户端发送的消息为:"+ messageInfo);
        MessageInfo data= JsonUtil.json2Obj(messageInfo,MessageInfo.class);
        String targetClientId = data.getTargetClientId();
        ClientInfo clientInfo = clientInfoRepository.findClientByClientId(targetClientId);
        log.info("发送的目标用户信息为:"+JSON.toJSONString(clientInfo));
        if (clientInfo != null ) {
            UUID uuid = new UUID(clientInfo.getMostsignbits(), clientInfo.getLeastsignbits());
            MessageInfo sendData = new MessageInfo();
            sendData.setSourceClientId(data.getSourceClientId());
            sendData.setTargetClientId(data.getTargetClientId());
            sendData.setMsgType(data.getMsgType());
            sendData.setMsgContent(data.getMsgContent());
            sendData.setMsgDate(System.currentTimeMillis());
            sendData.setRead(data.getRead());
            log.info("发送的数据为:"+JSON.toJSONString(sendData));
            client.sendEvent("messageevent", sendData);
            log.info("server的类信息为:"+server);
            server.getClient(uuid).sendEvent("messageevent", sendData);
            //将消息记录存储入库
            messageInfoRepository.addMessageInfo(sendData);
        }
    }

    /**
     * 医生的聊天列表
     * @param client
     */
    @OnEvent(value = "userList")
    public void onList(SocketIOClient client,Map paramMap) {
        log.info("当前连接人为:"+JSON.toJSONString(paramMap));
        String date=DateUtils.formatDate(new Date(),"yyyy-MM-dd");
        List<String> patientId = messageInfoRepository.findPatientIdList(paramMap.get("clientId").toString(), date);
        DataOutResponse data = socketClient.findHosList(StringUtils.join(patientId, ","));
        ClientInfo clientInfo = clientInfoRepository.findClientByClientId(paramMap.get("clientId").toString());
        if (clientInfo != null ) {
            UUID uuid = new UUID(clientInfo.getMostsignbits(), clientInfo.getLeastsignbits());
            log.info("医生的用户列表为:" + data.getData());
            //推送给客户端病人列表
            client.sendEvent("userList", data.getData());
            log.info("server的类信息为:"+server+"uuid为:"+uuid);
            server.getClient(uuid).sendEvent("userList", data.getData());
        }
    }

    /**
     * 消息记录
     * @param client
     * @param paramMap
     */
    @OnEvent(value = "messageRecord")
    public void onRecord(SocketIOClient client,Map paramMap) {
        log.info("查询记录的条件为:"+JSON.toJSONString(paramMap));
        List<String> sourceId=Arrays.asList(paramMap.get("sourceId").toString().split(","));
        List<Map> result=messageInfoRepository.findMessageList(sourceId,sourceId,Long.parseLong(paramMap.get("startDate").toString()),
                Long.parseLong(paramMap.get("endDate").toString()));
        log.info("返回的消息记录列表为:"+JSON.toJSONString(result));
        client.sendEvent("messageRecord",result);
    }

    /**
     * 推送病人基本信息
     * @param client
     * @param paramMap
     */
    @OnEvent(value = "patientInfo")
    public void onPatient(SocketIOClient client,Map paramMap) {
        Long patientId=Long.parseLong(paramMap.get("patientId").toString());
        Integer patientSource=Integer.parseInt(paramMap.get("patientSource").toString());
        log.info("病人的patientID为:"+patientId+"病人来源:"+patientSource);
        DataOutResponse data=socketClient.findHisPlanPatientById(patientId,patientSource);
        log.info("病人基本信息为:"+JSON.toJSONString(data));
        if (200==data.getStatus()) {
            client.sendEvent("patientInfo", data.getData());
        }
    }

    /**
     * 查询当前医生未读消息条数
     * @param client
     * @param paramMap
     */
    @OnEvent(value = "msgCount")
    public void onCount(SocketIOClient client,Map paramMap) {
        log.info("当前连接人为:"+JSON.toJSONString(paramMap));
        int msgCount=messageInfoRepository.findMsgCount(paramMap.get("clientId").toString());
        log.info("当前医生未读条数为:"+msgCount);
        client.sendEvent("msgCount",msgCount);
    }

    /**
     * 修改消息状态
     * @param client
     * @param paramMap
     */
    @OnEvent(value = "updateMsgStatus")
    @Transactional(rollbackFor = Exception.class)
    public void onStatus(SocketIOClient client,Map paramMap) {
        log.info("消息ID为:"+JSON.toJSONString(paramMap));
        List<String> idList=Arrays.asList(paramMap.get("clientId").toString(),",");
        messageInfoRepository.updateMsgStatus(idList);
    }
}

