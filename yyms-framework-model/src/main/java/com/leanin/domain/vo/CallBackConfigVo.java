package com.leanin.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * 投诉表扬处理表
 * @author Administrator
 *
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CallBackConfigVo {
	
    private String configNum;	//投诉表扬处理单号

    private Integer configStatus;	//（1：投诉 2：表扬）

    private Integer configType;		//（1：医疗技术 2：服务态度 3：不及时）

    private String wardCode;	//操作人员科室编码

    private String dealWithName;	//处理人（多人以逗号拼接）

    private String delaWithPhone;	//处理人联系电话

    private String configCreater;	//创建人

    private Date configTime;	//创建时间

    private Integer configState;	//（-1：已注销 0：正在使用）	

    private String areaCode;	//院区编码

}