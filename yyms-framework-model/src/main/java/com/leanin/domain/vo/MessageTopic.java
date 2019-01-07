package com.leanin.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * 短信主题单号
 * @author Administrator
 *
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MessageTopic {
	
    private String msgTopicId;	//短信单号

    private Long formTypeId;	//分类ID

    private String msgTopicTitle;	//短信主题

    private Integer patientType;	//病人类型

    private String msgTopicHead;	//短信头部

    private Long msgId;	//短信表单ID

    private Integer msgSendType;	//短信发送方式

    private Date msgSendDate;	//短信发送日期和时间

    private Integer msgSendStatus;	//短信发送状态（-1：发送失败 0：发送成功）

    private String msgTopicCreater;	//短信主题创建人

    private String msgTopicCreateWard;	//短信主题创建者科室
    
    private String areaCode;	//院区编码

}