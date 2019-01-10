package com.leanin.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * 短信提醒库
 * @author Administrator
 *
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MsgInfoVo {
	
    private String msgId;	//短信库单号

    private Long followFormId;	//分类ID

    private String msgName;	//提醒语名称

    private String msgCreater;	//提醒语创建者

    private Date msgCreateTime;	//创建时间

    private String msgCreateWard;	//创建者病区

    private Integer msgStatus;	//提醒语状态

    private String areaCoe;	//院区编码

    private String msgText;	//提醒语正文

}