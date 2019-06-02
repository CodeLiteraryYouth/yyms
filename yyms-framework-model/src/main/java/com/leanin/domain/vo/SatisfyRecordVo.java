package com.leanin.domain.vo;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 患者档案 满意度记录展示
 * @author CPJ.
 * @date 2019/5/31.
 * @time 10:34.
 */
@Data
public class SatisfyRecordVo {

    private Date sendTime;  //满意度发送时间

    private List<WardInfoVo> wardInfoVos;   //发送人科室集合

    private String senderName;      //发送人

    private String handleSuggess;   //处理意见

    private String formId;          //表单id

    private String formName;        //表单名称

    private String formRedcordId;   //表单记录主键

    private String state;            //满意度完成  -1:收案 1：未完成 2：已完成；3:已过期; 4 无法接听 5 号码错误 6 拒绝接听 7 无人接听 8 家属接听 9 患者不合作 10 无联系电话 11 其他
}
