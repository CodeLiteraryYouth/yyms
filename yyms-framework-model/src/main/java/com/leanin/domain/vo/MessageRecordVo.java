package com.leanin.domain.vo;

import lombok.Data;

import java.util.List;

/**
 * @author CPJ.
 * @date 2019/6/2.
 * @time 11:01.
 */
@Data
public class MessageRecordVo extends MessageRecord {

    private String senderName;  //发送人姓名

    private List<WardInfoVo> wardInfoVos; //发送人科室
}
