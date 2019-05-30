package com.leanin.socket.mapper;

import com.leanin.socket.domain.ClientInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @ProjectName: yyms
 * @Package: com.leanin.socket.mapper
 * @ClassName: ClientInfoRepository
 * @Author: zd
 * @Description: ${description}
 * @Date: 2019/5/16 14:48
 * @Version: 1.0
 */
@Mapper
public interface ClientInfoRepository {

    /**
     * 根据clientId查询客户端连接信息
     * @param clientId
     * @return
     */
    ClientInfo findClientByClientId(@Param("clientId") String clientId);

    /**
     * 添加socket连接人信息
     * @param client
     */
    void addClientInfo(ClientInfo client);

    /**
     * 修改socket连接人信息
     * @param client
     */
    void updateClientInfo(ClientInfo client);

    /**
     * 查询医生在线连接列表
     * @return
     */
    List<ClientInfo> findClientList();
}
