package com.leanin.domain.vo;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author CPJ.
 * @date 2019/5/31.
 * @time 13:37.
 */
@Data
public class MsgRecordVo {

    private Date sendTime;      //发送时间

    private List<WardInfoVo> wardInfoVos;   //发送人科室

    private String senderName;  //发送人姓名

    private String phone;       //接受人号码

    private String msgTopicName;     //主题名字

    private String msgContent;  //短信内容

}
