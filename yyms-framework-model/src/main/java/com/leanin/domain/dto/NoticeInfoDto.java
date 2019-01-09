package com.leanin.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class NoticeInfoDto {
	
    private Long noticeId;	//公告标题主键

    private String noticeTitle;	//公告标题名称

    private String wardName;	//公告发布的科室

    private String areaCode;	//院区编码

    private String noticeText;	//公告发布的内容

}