package com.leanin.socket.domain;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @ProjectName: yyms
 * @Package: com.leanin.socket.domain
 * @ClassName: MessageInfo
 * @Author: zd
 * @Description: 消息记录存储
 * @Date: 2019/5/16 14:35
 * @Version: 1.0
 */
@Data
@Entity
@Table(name="leanin_message_info")
public class MessageInfo {

    //主键ID
    @Id
    @Column(name="id",unique = true, nullable = false)
    private long id;
    //源客户端id
    @Column(name="source_client_id")
    private String sourceClientId;
    //目标客户端id
    @Column(name="target_client_id")
    private String targetClientId;
    //消息类型
    @Column(name="msg_type")
    private String msgType;
    //消息内容
    @Column(name="msg_text")
    private String msgContent;
    //消息时间戳
    @Column(name="msg_date")
    private long msgDate;

    //0:未读 1:已读
    @Column(name="is_read")
    private int read;

}
