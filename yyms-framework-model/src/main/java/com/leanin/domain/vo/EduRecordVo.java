package com.leanin.domain.vo;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 宣教记录展示  患者档案
 * @author CPJ.
 * @date 2019/5/25.
 * @time 10:59.
 */
@Data
public class EduRecordVo {

    private Date sendTime;  //发送时间

    private List<WardInfoVo> wardInfoVos;   //发送人科室

    private String senderName;      //发送人姓名

    private String eduFormName;     //宣教表单名称

    private String eduFormNum;      //宣教表单号

    private String formStatus;     //表单状态  1 未完成  2 已完成


}
