package com.leanin.socket.mapper;

import com.leanin.socket.domain.MessageInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Map;

/**
 * @ProjectName: yyms
 * @Package: com.leanin.socket.mapper
 * @ClassName: MessageInfoRepository
 * @Author: zd
 * @Description: 存储消息内容的Mapper层
 * @Date: 2019/5/16 15:14
 * @Version: 1.0
 */
@Mapper
public interface MessageInfoRepository {

    /**
     * 添加消息记录
     * @param messageInfo
     */
    void addMessageInfo(MessageInfo messageInfo);
    /**
     * 根据接收者ID查询发送者ID列表
     * @param clientId
     * @return
     */
    List<String> findPatientIdList(@Param("clientId") String clientId,@Param("date") String date);

    /**
     * 查询当前医生未读消息总数
     * @param clientId
     * @return
     */
    int findMsgCount(@Param("clientId") String clientId);

    /**
     * 根据发送者ID和接收者ID查询消息列表
     * @param sourceId
     * @param targetId
     * @return
     */
    List<Map> findMessageList(@Param("sourceId") List<String> sourceId,@Param("targetId") List<String> targetId,
                              @Param("startDate") long startDate,@Param("endDate") long endDate);

    /**
     * 修改消息阅读状态
     * @param id
     */
    void updateMsgStatus(@Param("idList") List<String> id);
}
