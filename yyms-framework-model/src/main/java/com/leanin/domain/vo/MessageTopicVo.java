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
public class MessageTopicVo {
	
    private String msgTopicId;	//短信主题单号

    private String msgTopicTitle;	//短信主题

    private Integer patientType;	//患者来源

    private String diseaseName;     //疾病类型

    private String msgTopicHead;	//短信头部

    private String msgContent;      //短信内容

    private Date msgSendDate;          //发送时间

    private Long msgTopicCreater;	//短信主题创建人

    private String createrName;     //创建人名称

    private String msgTopicCreaterWard;  //短信主题创建人所属科室

    private String areaCode;	//院区编码

    private Integer msgTopicState;      //短信主题状态  1 在用  -1 删除

    private Date msgStartDate;          //短信主题筛选患者开始时间

    private Date msgEndDate;            //短信主题筛选患者结束时间

    private Integer unsentCount;        //未发送人数
}