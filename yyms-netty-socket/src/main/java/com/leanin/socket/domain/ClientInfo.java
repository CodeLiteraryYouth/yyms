package com.leanin.socket.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @ProjectName: yyms
 * @Package: com.leanin.socket.domain
 * @ClassName: ClientInfo
 * @Author: zd
 * @Description: 存储socket客户端连接信息
 * @Date: 2019/5/16 14:25
 * @Version: 1.0
 */
@Data
@Entity
@Table(name="leanin_client_connect")
public class ClientInfo {

    @Id
    @Column(name = "client_id", unique = true, nullable = false)
    private String clientid;    //客户源ID

    @Column(name="connected")
    private Short connected;    //连接次数

    @Column(name="mostsign_bits")
    private Long mostsignbits;

    @Column(name="leastsign_bits")
    private Long leastsignbits;

    @Column(name="last_connected_date")
    private Date lastconnecteddate;

}
