package com.leanin.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 公告内容
 * @author Administrator
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class NoticeInfoVo {
	
    private Long noticeId;	//公告标题主键

    private String noticeTitle;	//公告标题名称

    private String wardName;	//公告发布的科室

    private String areaCode;	//院区编码

    private String noticeText;	//公告发布的内容

    private  Integer status;    //0:启用 1：停用

}